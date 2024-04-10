package oogasalad.model.gameplay.handlers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import oogasalad.controller.gameplay.GameOverController;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.grid.GridHelper;

public abstract class KeyHandler {

  private final Grid grid;
  private GridHelper gridHelper;
  private final GameOverController gameOverController;

  public KeyHandler(Grid grid, GameOverController gameOverController) {
    this.grid = grid;
    this.gameOverController = gameOverController;
    grid.checkForRules();
  }

  public abstract void execute();

  protected void handleKeyPress(int deltaI, int deltaJ) {
    grid.checkBehaviors();
    List<int[]> controllableBlockPositions = grid.findControllableBlock();
    if (!controllableBlockPositions.isEmpty()) {
      controllableBlockPositions.stream()
          .forEach(element -> moveBlock(element[0], element[1], element[2], deltaI, deltaJ));
    } else {
      gameOverController.displayGameOver(false);
    }
    grid.checkForRules();
    grid.checkBehaviors();
    grid.notifyObserver();
  }

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


  private void nextIsWinningBlock(int nextI, int nextJ) {
    if (grid.cellHasWinning(nextI, nextJ)) {
      gameOverController.displayGameOver(true);
    }
  }

  private boolean isValidMove(int i, int j, int k) {
    return grid.isNotOutOfBounds(i, j);
  }

}
