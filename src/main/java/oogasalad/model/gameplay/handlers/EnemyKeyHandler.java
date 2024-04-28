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

    }

    @Override
    public void execute() {

        if( grid.findEnemyBlock().size() >= 20){
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
            if (grid.cellHasWinning(randomRow, randomCol) || grid.cellHasTextBlock(randomRow, randomCol) || grid.cellHasWater(randomRow, randomCol) || grid.cellHasLava(randomRow, randomCol) || grid.cellHasEnemy(randomRow, randomCol)) {
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
        List<int[]> allEnemyBlocks = grid.findEnemyBlock();

        if (allEnemyBlocks.isEmpty()) {
            return;
        }
        if(grid.findControllableBlock().isEmpty()){
            gameStateController.displayGameOver(false);
            return;
        }
        if(!allEnemyBlocks.isEmpty() && !grid.findControllableBlock().isEmpty()){
            for(int [] enemyBlock : allEnemyBlocks){
                int[] enemy = {enemyBlock[0], enemyBlock[1]};
                Optional<int[]> babaOptional = nearestBabaCoordinate(enemy);
                if(babaOptional.isEmpty()){ //we removed all the BABA's
                    gameStateController.displayGameOver(false);
                    break;
                }
                int [] babaLocation = babaOptional.get();
                int [] baba = {babaLocation[0], babaLocation[1]};
                if(Arrays.equals(enemy, baba)){ //they are in the same place
                    grid.removeBaba(babaLocation[0], babaLocation[1]);
                    continue;
                }else{
                    Optional<List<int[]>> shortestPathOptional = findShortestPath(enemy, baba);
                    if (shortestPathOptional.isPresent()) {
                        List<int[]> shortestPath = shortestPathOptional.get();

                        int[] nextPosition = shortestPath.get(1);
                        grid.moveEnemy(enemyBlock[0], enemyBlock[1], enemyBlock[2], nextPosition[0], nextPosition[1]);
                    }
                }
            }
        }
        grid.notifyObserver();

    }




    private int [] enemyCoordinate(){
        return grid.enemyPosition();
    }

    private Optional<int[]> nearestBabaCoordinate(int[] enemyCamp) {
        List<int[]> allControllableBlock = grid.findControllableBlock();

        if (allControllableBlock.isEmpty()) {
            return Optional.empty();
        }

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

        return Optional.of(allControllableBlock.get(0));
    }


    private int calculateDistance(int[] coordinate1, int[] coordinate2) {
        return Math.abs(coordinate1[0] - coordinate2[0]) + Math.abs(coordinate1[1] - coordinate2[1]);
    }


    private Optional<List<int[]>> findShortestPath(int[] start, int[] target){
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
        LinkedList<int[]> path = new LinkedList<>();
        int[] current = target;

        // Add the target as the starting point of the path
        path.addFirst(target);

        while (!Arrays.equals(current, start)) {
            boolean stepFound = false;
            for (int[] direction : directions) {
                int newRow = current[0] + direction[0];
                int newCol = current[1] + direction[1];
                if (newRow >= 0 && newRow < distances.length && newCol >= 0 && newCol < distances[0].length) {
                    if (distances[newRow][newCol] == distances[current[0]][current[1]] - 1) {
                        current = new int[]{newRow, newCol};
                        path.addFirst(current);
                        stepFound = true;
                        break;  // Break as soon as a valid step is found
                    }
                }
            }
            if (!stepFound) {
                break;
            }
        }

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
