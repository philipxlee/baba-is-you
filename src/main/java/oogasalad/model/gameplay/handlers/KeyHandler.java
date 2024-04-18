package oogasalad.model.gameplay.handlers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import oogasalad.controller.gameplay.GameStateController;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.grid.GridHelper;

public abstract class KeyHandler {

  private final Grid grid;
  private GridHelper gridHelper;
  private final GameStateController gameStateController;

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
    List<int[]> controllableBlockPositions = grid.findControllableBlock();
    if (!controllableBlockPositions.isEmpty()) {
      controllableBlockPositions.stream()
          .forEach(element -> moveBlock(element[0], element[1], element[2], deltaI, deltaJ));
    } else {
      gameStateController.displayGameOver(false);
    }
    grid.checkForRules();
    grid.checkBehaviors();
    grid.notifyObserver();
  }


  /**
   * Moves the controllable block at the specified position in the grid by the given delta in row and column indices.
   * Checks if the move is valid, determines if the next cell contains a winning block, calculates the length of the block chain to be moved,
   * and performs the movement accordingly. Finally, it updates the grid by checking for rules, behaviors, and notifying observers.
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
    if (!isValidMove(nextI, nextJ, k)) {
      return;
    }
    nextIsWinningBlock(nextI, nextJ);
    calculateLength(i, j, k, deltaI,
        deltaJ) //calculates length to move and calls perfrom movement on the length
        .ifPresent(length -> performMovement(i, j, k, deltaI, deltaJ, length));

    grid.checkForRules();
    grid.checkBehaviors();
    grid.notifyObserver();
  }


  /**
   * Determines the length of the block chain to be moved in a particular direction.
   *
   * @param i      The row index of the starting block.
   * @param j      The column index of the starting block.
   * @param k      The index of the controllable block in the cell.
   * @param deltaI The change in row position caused by the key press.
   * @param deltaJ The change in column position caused by the key press.
   * @return An Optional containing the length of the block chain if movement is possible, otherwise empty.
   */
  private Optional<Integer> calculateLength(int i, int j, int k, int deltaI, int deltaJ) {
    int length = 1;
    while (true) {
      int nextI = i + length * deltaI; //gets next cell
      int nextJ = j + length * deltaJ; // gets next cell
      if (isValidMove(nextI, nextJ, k) && grid.cellHasPushable(nextI, nextJ)
          && !grid.cellHasStoppable(nextI, nextJ)) {
        length++;
      } else {
        break;
      }
    }
    int endI = i + length * deltaI;
    int endJ = j + length * deltaJ;
    if (!isValidMove(endI, endJ, k) || !grid.isMovableToMargin(endI, endJ, k, i, j, k)
        || grid.cellHasStoppable(endI, endJ) || grid.cellHasControllable(endI,
        endJ)) { //last condition makes sure you cant stack controllables
      return Optional.empty(); // No space to move the chain
    }
    return Optional.of(length);
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
    for (int m = length - 1; m > 0; m--) {
      int currentI = i + m * deltaI;
      int currentJ = j + m * deltaJ;
      int nextI = currentI + deltaI;
      int nextJ = currentJ + deltaJ;
      //move all the pushable stuffs into the next cell
      List<Integer> indicesToMove = grid.allPushableBlocksIndex(currentI, currentJ);
      if (!indicesToMove.isEmpty()) {
        int minIndex = Collections.min(indicesToMove);
        for (int w = 0; w < indicesToMove.size(); w++) {
          grid.moveBlock(currentI, currentJ, minIndex, nextI, nextJ);
        }
      }
    }
    // Move controllable block last
    grid.moveBlock(i, j, k, i + deltaI, j + deltaJ);
    grid.checkForRules();
    grid.checkBehaviors();
    grid.notifyObserver();
  }


  /**
   * Checks if the next cell contains a winning block and displays game over if so.
   *
   * @param nextI The row index of the next cell.
   * @param nextJ The column index of the next cell.
   */
  private void nextIsWinningBlock(int nextI, int nextJ) {
    if (grid.cellHasWinning(nextI, nextJ)) {
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

}
