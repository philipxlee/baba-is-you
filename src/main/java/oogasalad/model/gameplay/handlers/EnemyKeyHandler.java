package oogasalad.model.gameplay.handlers;

import oogasalad.controller.gameplay.GameStateController;
import oogasalad.model.gameplay.grid.Grid;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class to represent the movement of the enemy character.
 *
 * @author Oluwagbotemi Joseph Ogunbadewa
 */
public class EnemyKeyHandler extends KeyHandler {
    private static final Logger logger = LogManager.getLogger(EnemyKeyHandler.class);
    private final Grid grid;
    private final GameStateController gameStateController;
    private static final int MAX_ENEMY_COUNT = 30;
    private final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};



    /**
     * Constructs an EnemyKeyHandler with the specified grid and game state controller.
     *
     * @param grid                The grid on which enemies are placed and moved.
     * @param gameStateController Controller for managing the game state.
     */
    public EnemyKeyHandler(Grid grid, GameStateController gameStateController) {
        super(grid, gameStateController);
        this.grid = grid;
        this.gameStateController = gameStateController;
//        logger.info("Created enemy handler for artificial player");
    }


    /**
     * Executes the action of creating new enemies if the maximum enemy count has not been reached.
     */
    @Override
    public void execute() {
        if (grid.findEnemyBlock().size() < MAX_ENEMY_COUNT) {
            createEnemy();
            logger.info("Created enemy");
        }
    }

    /**
     * Moves all enemy entities towards the nearest controllable block (player) on the grid.
     * If no enemy entities exist or if no controllable block is found, the method returns without performing any action.
     * If an enemy entity overlaps with a controllable block, the block is removed from the grid.
     * Enemy entities are moved one step closer to the nearest controllable block based on the shortest path algorithm.
     * If a valid path to the controllable block exists, the enemy entity is moved accordingly.
     * This method also notifies the grid observer after all enemy entities have been moved.
     */
    public void moveEnemy() {
        List<int[]> allEnemyBlocks = grid.findEnemyBlock();
        if (allEnemyBlocks.isEmpty() || grid.findControllableBlock().isEmpty()) {
            handleNoMovementConditions(allEnemyBlocks);
            return;
        }
        allEnemyBlocks.forEach(this::moveSingleEnemy);
        grid.notifyObserver();
    }


    /**
     * Handles the conditions where no enemy blocks or controllable blocks are found.
     *
     * @param allEnemyBlocks The list of all enemy blocks on the grid.
     */
    private void handleNoMovementConditions(List<int[]> allEnemyBlocks) {
        if (!allEnemyBlocks.isEmpty()) {
            gameStateController.displayGameOver(false);
        }
    }


    /**
     * Moves a single enemy entity towards the nearest controllable block.
     *
     * @param enemyBlock The coordinates of the enemy entity.
     */
    private void moveSingleEnemy(int[] enemyBlock) {
        int[] enemy = {enemyBlock[0], enemyBlock[1]};
        Optional<int[]> controllableOptional = nearestControllableCoordinate(enemy);
        if (controllableOptional.isEmpty()) {
            gameStateController.displayGameOver(false); // All controllable blocks have been removed
            return;
        }
        int[] controllableLocation = controllableOptional.get();
        int[] controllable = {controllableLocation[0], controllableLocation[1]};
        if (Arrays.equals(enemy, controllable)) {
            handleEnemyControllableOverlap(controllableLocation);
        } else {
            moveEnemyTowardsControllable(enemyBlock, controllable);
        }
    }

    /**
     * Moves an enemy entity towards the nearest controllable block.
     *
     * @param enemyBlock The coordinates of the enemy entity.
     * @param controllable       The coordinates of the nearest controllable block.
     */
    private void moveEnemyTowardsControllable(int[] enemyBlock, int[] controllable) {
        Optional<List<int[]>> shortestPathOptional = findShortestPath(enemyBlock, controllable);
        if (shortestPathOptional.isPresent()) {
            List<int[]> shortestPath = shortestPathOptional.get();
            int[] nextPosition = shortestPath.get(1);
            Optional<Integer> optionalIndex = grid.findEnemyIndex(enemyBlock[0], enemyBlock[1]);
            if (optionalIndex.isPresent()) {
                int index = optionalIndex.get();
                grid.moveEnemy(enemyBlock[0], enemyBlock[1], index, nextPosition[0], nextPosition[1]);
            }
        }
    }


    /**
     * Finds the coordinates of the nearest controllable block (player) to a given enemy camp.
     *
     * @param enemyCamp The coordinates of the enemy position.
     * @return Optional containing the coordinates of the nearest controllable block if found, otherwise empty.
     */
    private Optional<int[]> nearestControllableCoordinate(int[] enemyCamp) {
        List<int[]> allControllableBlock = grid.findControllableBlock();
        if (allControllableBlock.isEmpty()) {
            return Optional.empty();
        }
        sortWithCustomCompare(enemyCamp, allControllableBlock);
        return Optional.of(allControllableBlock.get(0));
    }

    /**
     * Sorts a list of coordinates based on their distances to a specified enemy camp using a custom comparator.
     *
     * @param enemyCamp            The coordinates of the enemy camp.
     * @param allControllableBlock The list of controllable block coordinates to be sorted.
     */
    private void sortWithCustomCompare(int[] enemyCamp, List<int[]> allControllableBlock) {
        Comparator<int[]> distanceComparator = new Comparator<int[]>() {
            @Override
            public int compare(int[] block1, int[] block2) {
                int distanceToEnemyCamp1 = calculateDistance(block1, enemyCamp);
                int distanceToEnemyCamp2 = calculateDistance(block2, enemyCamp);
                return Integer.compare(distanceToEnemyCamp1, distanceToEnemyCamp2);
            }
        };
        allControllableBlock.sort(distanceComparator);
    }


    /**
     * Finds the shortest path from a starting point to a target point using breadth-first search (BFS).
     *
     * @param start  The starting point coordinates.
     * @param target The target point coordinates.
     * @return Optional containing the shortest path as a list of coordinates if found, otherwise empty.
     */
    private Optional<List<int[]>> findShortestPath(int[] start, int[] target) {
        int numRows = grid.getGridWidth();
        int numCols = grid.getGridHeight();
        int[][] distances = new int[numRows][numCols];
        boolean[][] visited = new boolean[numRows][numCols];
        initializeArrays(distances, visited);
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(start);
        visited[start[0]][start[1]] = true;
        return determinePath(start, target, queue, distances, numRows, numCols, visited);
    }

    /**
     * Determines the shortest path from the start coordinates to the target coordinates using a breadth-first search algorithm.
     *
     * @param start     The starting coordinates.
     * @param target    The target coordinates.
     * @param queue     The queue for BFS traversal.
     * @param distances The distance array to track distances from start to each cell.
     * @param numRows   The number of rows in the grid.
     * @param numCols   The number of columns in the grid.
     * @param visited   The boolean array to track visited cells.
     * @return An optional containing the shortest path as a list of coordinates if found, or empty if no path exists.
     */
    private Optional<List<int[]>> determinePath(int[] start, int[] target, Queue<int[]> queue, int[][] distances, int numRows, int numCols, boolean[][] visited) {
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            if (row == target[0] && col == target[1]) {
                // Target reached, reconstruct path back to enemy
                List<int[]> path = reconstructPath(start, target, distances);
                return Optional.of(path);
            }
            evaluateNeighbors(row, col, numRows, numCols, distances, visited, queue);
        }
        return Optional.empty();
    }


    /**
     * Evaluates neighboring cells of a given cell during BFS traversal.
     *
     * @param row       The row index of the current cell.
     * @param col       The column index of the current cell.
     * @param numRows   The total number of rows in the grid.
     * @param numCols   The total number of columns in the grid.
     * @param distances The distances array used in BFS.
     * @param visited   The visited array used in BFS.
     * @param queue     The queue used in BFS.
     */
    private void evaluateNeighbors(int row, int col, int numRows, int numCols, int[][] distances, boolean[][] visited, Queue<int[]> queue) {
        getNeighbors(row, col, numRows, numCols, DIRECTIONS, distances)
                .stream()
                .filter(neighbor -> !visited[neighbor[0]][neighbor[1]] && grid.isPassable(neighbor[0], neighbor[1]))
                .forEach(neighbor -> {
                    visited[neighbor[0]][neighbor[1]] = true;
                    distances[neighbor[0]][neighbor[1]] = distances[row][col] + 1;
                    queue.offer(new int[]{neighbor[0], neighbor[1]});
                });
    }


    /**
     * Initializes the distances and visited arrays used in BFS.
     *
     * @param distances The distances array to initialize.
     * @param visited   The visited array to initialize.
     */
    private void initializeArrays(int[][] distances, boolean[][] visited) {
        IntStream.range(0, distances.length)
                .forEach(i ->
                        IntStream.range(0, distances[0].length)
                                .forEach(j -> {
                                    distances[i][j] = Integer.MAX_VALUE;
                                    visited[i][j] = false;
                                })
                );
    }


    /**
     * Reconstructs the shortest path from the target point to the starting point.
     *
     * @param start     The starting point coordinates.
     * @param target    The target point coordinates.
     * @param distances The distances array used in BFS.
     * @return The reconstructed shortest path as a list of coordinates.
     */
    private List<int[]> reconstructPath(int[] start, int[] target, int[][] distances) {
        LinkedList<int[]> path = new LinkedList<>();
        path.addFirst(target);
        breadthSearch(start, distances, target, path);
        return path;
    }

    /**
     * Performs a breadth-first search to reconstruct the shortest path from the target position to the start position.
     *
     * @param start     The starting position of the search.
     * @param distances A 2D array representing the distances from each cell to the target position.
     * @param current   The current position being evaluated during the search.
     * @param path      A linked list representing the reconstructed shortest path.
     */
    private void breadthSearch(int[] start, int[][] distances, int[] current, LinkedList<int[]> path) {
        while (!Arrays.equals(current, start)) {
            boolean stepFound = false;
            for (int[] direction : DIRECTIONS) {
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
    }


    /**
     * Retrieves neighboring cells of a given cell.
     *
     * @param row        The row index of the cell.
     * @param col        The column index of the cell.
     * @param numRows    The total number of rows in the grid.
     * @param numCols    The total number of columns in the grid.
     * @param directions The directions array indicating possible moves.
     * @param distances  The distances array used in BFS.
     * @return A list of neighboring cells' coordinates.
     */
    private List<int[]> getNeighbors(int row, int col, int numRows, int numCols, int[][] directions, int[][] distances) {
        return Arrays.stream(directions)
                .map(dir -> new int[]{row + dir[0], col + dir[1]})
                .filter(neighbor -> isValidMove(distances, neighbor[0], neighbor[1]))
                .collect(Collectors.toList());
    }

    /**
     * Creates a new enemy entity and places it randomly on the grid.
     */
    private void createEnemy() {
        int numRows = grid.getGridWidth();
        int numCols = grid.getGridHeight();
        while (true) {
            // Generate random coordinates within the grid
            int randomRow = (int) (Math.random() * numRows);
            int randomCol = (int) (Math.random() * numCols);
            if (grid.cellIsEmpty(randomRow, randomCol)) {
                grid.placeEnemy(randomRow, randomCol);
                break;
            }
        }
    }

    /**
     * Checks if a move to the specified cell is valid based on the distances array.
     *
     * @param distances The distances array used in BFS.
     * @param row       The row index of the cell to check.
     * @param col       The column index of the cell to check.
     * @return True if the move is valid, false otherwise.
     */
    private boolean isValidMove(int[][] distances, int row, int col) {
        return grid.isNotOutOfBounds(row, col) && distances[row][col] == Integer.MAX_VALUE;
    }

    /**
     * Calculates the Manhattan distance between two sets of coordinates.
     *
     * @param coordinate1 The first set of coordinates.
     * @param coordinate2 The second set of coordinates.
     * @return The Manhattan distance between the two sets of coordinates.
     */
    private int calculateDistance(int[] coordinate1, int[] coordinate2) {
        return Math.abs(coordinate1[0] - coordinate2[0]) + Math.abs(coordinate1[1] - coordinate2[1]);
    }

    /**
     * Handles the situation where an enemy entity overlaps with a controllable block.
     *
     * @param controllableLocation The coordinates of the controllable block.
     */
    private void handleEnemyControllableOverlap(int[] controllableLocation) {
        grid.removeControllable(controllableLocation[0], controllableLocation[1]);
    }

}
