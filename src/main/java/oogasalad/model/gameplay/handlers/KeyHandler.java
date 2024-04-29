package oogasalad.model.gameplay.handlers;

import oogasalad.controller.gameplay.GameStateController;
import oogasalad.model.gameplay.grid.Grid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * KeyHandler class defines and handles the action associated with the valid keypress.
 *
 * @author Oluwagbotemi Joseph Ogunbadewa.
 */
public abstract class KeyHandler {

  private static final Logger logger = LogManager.getLogger(KeyHandler.class);

  private final Grid grid;
  private final GameStateController gameStateController;
  private static final String KILL = "Kill";
  private static final String KILLABLE = "Killable";
  private static final String CONTROLLABLE = "Controllable";

  private static final String WINNABLE = "Winnable";
  private static final String STOPPABLE = "Stoppable";

  /**
   * Constructs a KeyHandler object with the given grid and game state controller.
   *
   * @param grid                The grid instance associated with the game.
   * @param gameStateController The game state controller for managing game state transitions.
   */

  public KeyHandler(Grid grid, GameStateController gameStateController) {
    this.grid = grid;
    this.gameStateController = gameStateController;
    grid.checkForRules();
//    logger.info("Created KeyHandler class");
  }

  /**
   * Executes the action associated with the keyboard input.
   */
  public abstract void execute();


  /**
   * Handles the key press event by moving controllable blocks.
   *
   * @param deltaI The change in row position caused by the key press.
   * @param deltaJ The change in column position caused by the key press.
   */
  protected void handleKeyPress(int deltaI, int deltaJ) {
    grid.checkBehaviors();
    List<int[]> controllableBlockPositions = grid.findAllPresentBlock(CONTROLLABLE);
    if (!controllableBlockPositions.isEmpty()) {
      controllableBlockPositions.stream()
          .forEach(element -> moveBlock(element[0], element[1], element[2], deltaI, deltaJ));
    } else {
      gameStateController.displayGameOver(false);
    }
    informGridOfUpdates();
  }


  /**
   * Moves the controllable block at the specified position in the grid by the given delta in row
   * and column indices. Checks if the move is valid, determines if the next cell contains a winning
   * block, calculates the length of the block chain to be moved, and performs the movement
   * accordingly. Finally, it updates the grid by checking for rules, behaviors, and notifying
   * observers.
   *
   * @param i      The row index of the starting cell.
   * @param j      The column index of the starting cell.
   * @param k      The index of the controllable block in the starting cell.
   * @param deltaI The change in row position caused by the key press.
   * @param deltaJ The change in column position caused by the key press.
   */
  private void moveBlock(int i, int j, int k, int deltaI, int deltaJ) {
    int nextI = i + deltaI;
    int nextJ = j + deltaJ;
    if (isValidMove(nextI, nextJ, k)) {
      nextIsWinningBlock(nextI, nextJ);
      calculateLength(i, j, k, deltaI, deltaJ)
              .ifPresent(length -> performMovement(i, j, k, deltaI, deltaJ, length));
      informGridOfUpdates();
    }
  }


  /**
   * Determines the length of the block chain to be moved in a particular direction.
   *
   * @param i      The row index of the starting block.
   * @param j      The column index of the starting block.
   * @param k      The index of the controllable block in the cell.
   * @param deltaI The change in row position caused by the key press.
   * @param deltaJ The change in column position caused by the key press.
   * @return An Optional containing the length of the block chain if movement is possible, otherwise
   * empty.
   */
  private Optional<Integer> calculateLength(int i, int j, int k, int deltaI, int deltaJ) {
    int length = 1;
    length = getLength(i, j, k, deltaI, deltaJ, length);
    int endI = i + length * deltaI;
    int endJ = j + length * deltaJ;
    if (!isValidMove(endI, endJ, k) || !grid.isMovableToMargin(endI, endJ, k, i, j, k)
            || grid.cellHasAttribute(endI, endJ, STOPPABLE) || grid.cellHasAttribute(endI, endJ, CONTROLLABLE)) {
      return Optional.empty(); // No space to move the chain
    }
    return Optional.of(length);
  }

  /**
   * Calculates the length of a sequence of pushable blocks in a given direction.
   *
   * @param i      The starting row index.
   * @param j      The starting column index.
   * @param k      The direction index.
   * @param deltaI The change in row index for each step.
   * @param deltaJ The change in column index for each step.
   * @param length The initial length of the sequence.
   * @return The length of the sequence of pushable blocks in the specified direction.
   */
  private int getLength(int i, int j, int k, int deltaI, int deltaJ, int length) {
    while (true) {
      int nextI = i + length * deltaI;
      int nextJ = j + length * deltaJ;
      if (isValidMove(nextI, nextJ, k) && grid.cellHasPushable(nextI, nextJ) && !grid.cellHasAttribute(nextI, nextJ, STOPPABLE)) {
        length++;
      } else {
        break;
      }
    }
    return length;
  }


  /**
   * Performs the movement of controllable blocks in the specified direction.
   *
   * @param i      The row index of the starting block.
   * @param j      The column index of the starting block.
   * @param k      The index of the controllable block in the cell.
   * @param deltaI The change in row position caused by the key press.
   * @param deltaJ The change in column position caused by the key press.
   * @param length The length of the block chain to be moved.
   */
  private void performMovement(int i, int j, int k, int deltaI, int deltaJ, int length) {
    // Move all blocks
    IntStream.range(1, length)
            .map(m -> length - m)
            .forEach(m -> {
              int currentI = i + m * deltaI;
              int currentJ = j + m * deltaJ;
              int nextI = currentI + deltaI;
              int nextJ = currentJ + deltaJ;
              List<Integer> indicesToMove = grid.allPushableBlocksIndex(currentI, currentJ);
              moveToIndexIfValid(indicesToMove, currentI, currentJ, nextI, nextJ);
            });
    grid.moveBlock(i, j, k, i + deltaI, j + deltaJ);
    informGridOfUpdates();
  }


  /**
   * Moves a block to a specified index if the list of indices to move is not empty.
   *
   * @param indicesToMove The list of indices to which the block should be moved.
   * @param currentI      The current row index of the block.
   * @param currentJ      The current column index of the block.
   * @param nextI         The row index to which the block will be moved.
   * @param nextJ         The column index to which the block will be moved.
   */
  private void moveToIndexIfValid(List<Integer> indicesToMove, int currentI, int currentJ, int nextI, int nextJ) {
    if (!indicesToMove.isEmpty()) {
      int minIndex = Collections.min(indicesToMove);
      for (int w = 0; w < indicesToMove.size(); w++) {
        grid.moveBlock(currentI, currentJ, minIndex, nextI, nextJ);
      }
    }
  }


  /**
   * Checks if the next cell contains a winning block and displays game over if so.
   *
   * @param nextI The row index of the next cell.
   * @param nextJ The column index of the next cell.
   */
  private void nextIsWinningBlock(int nextI, int nextJ) {
    if (grid.cellHasAttribute(nextI, nextJ, WINNABLE)) {
      gameStateController.displayGameOver(true);
    }
  }

  /**
   * Checks if the move to the specified cell is valid.
   *
   * @param i The row index of the cell.
   * @param j The column index of the cell.
   * @param k The index of the controllable block in the cell.
   * @return True if the move is valid, false otherwise.
   */
  private boolean isValidMove(int i, int j, int k) {
    return grid.isNotOutOfBounds(i, j);
  }

  /**
   * Inform the grid of updates, checking its rules, behaviors and notifying the observers.
   */
  private void informGridOfUpdates() {
    grid.checkForRules();
    grid.checkBehaviors();
    grid.notifyObserver();
  }

}
