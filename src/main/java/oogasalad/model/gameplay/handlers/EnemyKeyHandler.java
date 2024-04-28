package oogasalad.model.gameplay.handlers;

import oogasalad.controller.gameplay.GameStateController;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.grid.GridHelper;

import java.util.*;

import static java.util.Optional.of;

public class EnemyKeyHandler extends KeyHandler{
    private final Grid grid;
    private final GameStateController gameStateController;

    private final int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, down, left, right
    public EnemyKeyHandler(Grid grid, GameStateController gameStateController) {
        super(grid, gameStateController);
        this.grid = grid;
        this.gameStateController = gameStateController;
        System.out.println("created enemy hand");

    }

    @Override
    public void execute() {

        if(grid.hasEnemy()){
            return;
        }
        else{
            createEnemy();
        }
    }

    private void createEnemy(){
        int numRows = grid.getGridWidth();
        int numCols = grid.getGridHeight();
        boolean enemyPlaced = false;

        // Keep trying until the enemy is successfully placed
        while (!enemyPlaced) {
            // Generate random coordinates within the grid
            int randomRow = (int) (Math.random() * numRows);
            int randomCol = (int) (Math.random() * numCols);

            // Check if the cell at the random coordinates contains a TextBlock or winning block. dont wanna add them on top of each other
            if (grid.cellHasWinning(randomRow, randomCol) || grid.cellHasTextBlock(randomRow, randomCol)) {
                // If it's a TextBlock, continue to the next iteration to reprocess the position
                continue;
            }

            // Place the enemy at the random coordinates
            grid.placeEnemy(randomRow, randomCol);

            // Mark the enemy as successfully placed
            enemyPlaced = true;
        }
    }


    @Override
    public void moveEnemy() {
        if (!grid.hasEnemy()) {
            System.out.println("Grid does not have Enemy");
            return;
        } else {
            System.out.println("Grid has Enemy");
            int[] enemy = enemyCoordinate();
            System.out.println("Enemy Coordinates: (" + enemy[0] + ", " + enemy[1] + ", " + enemy[2] + ")");
            int[] baba = nearestBabaCoordinate(enemy);
            System.out.println("Nearest Baba Coordinates: (" + baba[0] + ", " + baba[1] + ")");
            int[] realEnemy = {enemy[0], enemy[1]};
            int[] realBaba = {baba[0], baba[1]};
            Optional<List<int[]>> shortestPathOptional = findShortestPath(realEnemy, realBaba);
            if (shortestPathOptional.isPresent()) {
                List<int[]> shortestPath = shortestPathOptional.get();
                System.out.println("Shortest Path:");
                for (int[] position : shortestPath) {
                    System.out.println("(" + position[0] + ", " + position[1] + ")");
                }
                int[] nextPosition = shortestPath.get(1);
                System.out.println("Next Position: (" + nextPosition[0] + ", " + nextPosition[1] + ")");
                grid.moveEnemy(enemy[0], enemy[1], enemy[2], nextPosition[0], nextPosition[1]);
            } else {
                System.out.println("No shortest path found.");
            }
        }
    }

//    public void moveEnemy(){ //would be called from the view after every 3 seconds of  no significant keypress from the player
//        if(!grid.hasEnemy()){
//            System.out.println("Grid does not have Enemy");
//            return;
//        }
//        else{
//            System.out.println("grid has Enemy ");
//            int [] enemy = enemyCoordinate();
//            int [] baba = nearestBabaCoordinate(enemy);
//            int [] realEnemy = {enemy[0], enemy[1]};
//            int [] realBaba = {baba[0], baba[1]};
//            Optional<List<int[]>> shortestPathOptional = findShortestPath(realEnemy, realBaba);
//            if(shortestPathOptional.isPresent()){
//                List<int[]> shortestPath = shortestPathOptional.get();
//                int [] nextPosition = shortestPath.get(1);
//                grid.moveEnemy(enemy[0], enemy[1], enemy[2], nextPosition[0], nextPosition[1]);
//
//            }
//            //could also use try catch with a custom exception
//
//        }
//    }


    public int [] enemyCoordinate(){
        return grid.enemyPosition();
    }
    public int[] nearestBabaCoordinate(int[] enemyCamp) {
        System.out.println("inside nearestBabaCoordinate");
        List<int[]> allControllableBlock = grid.findControllableBlock();

        Comparator<int[]> distanceComparator = new Comparator<int[]>() {
            @Override
            public int compare(int[] block1, int[] block2) {
                // Calculate the distance of each block to the enemy camp
                int distanceToEnemyCamp1 = calculateDistance(block1, enemyCamp);
                int distanceToEnemyCamp2 = calculateDistance(block2, enemyCamp);

                // Compare the distances
                return Integer.compare(distanceToEnemyCamp1, distanceToEnemyCamp2);
            }
        };

        // Sort the allControllableBlock list using the custom comparator
        allControllableBlock.sort(distanceComparator);

        return allControllableBlock.get(0); //would never be null, so no need of optionals
    }

    private int calculateDistance(int[] coordinate1, int[] coordinate2) {
        return Math.abs(coordinate1[0] - coordinate2[0]) + Math.abs(coordinate1[1] - coordinate2[1]);
    }


    public Optional<List<int[]>> findShortestPath(int[] start, int[] target){
        System.out.println("inside findShortestPath");
        int numRows = grid.getGridWidth();
        int numCols = grid.getGridHeight();
        int[][] distances = new int[numRows][numCols];
        boolean[][] visited = new boolean[numRows][numCols];

        initializeArrays(distances, visited);

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(start);
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            if (row == target[0] && col == target[1]) {
                // Target reached, reconstruct path back to enemy
                List<int[]> path = reconstructPath(start, target, distances);
                return Optional.of(path);
            }

            for (int[] neighbor : getNeighbors(row, col, numRows, numCols, directions, distances)) {
                int newRow = neighbor[0];
                int newCol = neighbor[1];
                if (!visited[newRow][newCol] && grid.isPassable(newRow, newCol)) {
                    visited[newRow][newCol] = true;
                    distances[newRow][newCol] = distances[row][col] + 1;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }

        // No path found
        return Optional.empty();
    }

    //distances: A 2D array to store the shortest distances from the start position to each cell in the grid.
    //visited: A 2D boolean array to mark cells that have been visited during the search process.
    //initalizes the visited and distance array to signify not visited
    private void initializeArrays(int[][] distances, boolean[][] visited) {
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[0].length; j++) {
                distances[i][j] = Integer.MAX_VALUE; //no known shortest distance, infinity
                visited[i][j] = false;
            }
        }
    }

    private List<int[]> reconstructPath(int[] start, int[] target, int[][] distances) {
        System.out.println("inside reconstructPath");
        List<int[]> path = new ArrayList<>();
        int[] position = target;
        while (!Arrays.equals(start, position)) {
            path.add(position);
            for (int[] dir : directions) {
                int newRow = position[0] + dir[0];
                int newCol = position[1] + dir[1];
                if (newRow >= 0 && newRow < grid.getGridWidth() && newCol >= 0 && newCol < grid.getGridHeight() &&
                        distances[newRow][newCol] == distances[position[0]][position[1]] - 1) {
                    position = new int[]{newRow, newCol};
                    break;
                }
            }
        }
        path.add(start);

        Collections.reverse(path); // Reverse the path to start from start to target
        return path;
    }

    private boolean isValidMove(int[][] distances, int row, int col) {
        return grid.isNotOutOfBounds(row, col) && distances[row][col] == Integer.MAX_VALUE;
    }

    private List<int[]> getNeighbors(int row, int col, int numRows, int numCols, int[][] directions, int[][] distances) {
        List<int[]> neighbors = new ArrayList<>();
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValidMove(distances, newRow, newCol)) {
                neighbors.add(new int[]{newRow, newCol});
            }
        }
        return neighbors;
    }

}
