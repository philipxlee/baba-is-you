package oogasalad.model.gameplay.handlers;

import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.strategies.Controllable;

public class KeyHandler {

  private final Grid grid;
  private final BlockFactory blockFactory;

  public KeyHandler(Grid grid) {
    this.grid = grid;
    this.blockFactory = new BlockFactory();
    grid.checkForRules();
  }

  public void handleKeyPress(KeyCode code) {
    AbstractBlock[][] gameGrid = grid.getGrid();
    int[] controllableBlockPosition = findControllableBlock(gameGrid);
    if (controllableBlockPosition != null) {
      moveBlock(controllableBlockPosition[0], controllableBlockPosition[1], code, gameGrid);
    }
  }

  private int[] findControllableBlock(AbstractBlock[][] gameGrid) {
    for (int i = 0; i < gameGrid.length; i++) {
      for (int j = 0; j < gameGrid[i].length; j++) {
        AbstractBlock block = gameGrid[i][j];
        if (block != null && block.hasBehavior(Controllable.class)) {
          return new int[]{i, j};
        }
      }
    }
    return null;
  }

  private void moveBlock(int i, int j, KeyCode code, AbstractBlock[][] gameGrid) {
    int deltaI = 0;
    int deltaJ = 0;
    switch (code) {
      case UP:
        deltaI = -1;
        break;
      case DOWN:
        deltaI = 1;
        break;
      case LEFT:
        deltaJ = -1;
        break;
      case RIGHT:
        deltaJ = 1;
        break;
      default:
        return;
    }

    int[] movement = calculateMovement(i, j, deltaI, deltaJ, gameGrid);
    if (movement != null) {
      performMovement(i, j, deltaI, deltaJ, movement[2], gameGrid);
    }
  }

  private int[] calculateMovement(int i, int j, int deltaI, int deltaJ, AbstractBlock[][] gameGrid) {
    int length = 1;

    while (true) {
      int nextI = i + length * deltaI;
      int nextJ = j + length * deltaJ;
      if (isValidMove(nextI, nextJ, gameGrid) && !"EmptyVisualBlock".equals(gameGrid[nextI][nextJ].getBlockName())) {
        length++;
      }
      else{
        break;
      }

    }

    int endI = i + length * deltaI;
    int endJ = j + length * deltaJ;
    if (!isValidMove(endI, endJ, gameGrid) || !"EmptyVisualBlock".equals(gameGrid[endI][endJ].getBlockName())) {
      return null; // No space to move the chain
    }

    return new int[]{deltaI, deltaJ, length};
  }

  private void performMovement(int i, int j, int deltaI, int deltaJ, int length, AbstractBlock[][] gameGrid) {
    // Move all blocks
    for (int k = length - 1; k > 0; k--) {
      int currentI = i + k * deltaI;
      int currentJ = j + k * deltaJ;
      int nextI = currentI + deltaI;
      int nextJ = currentJ + deltaJ;
      gameGrid[nextI][nextJ] = gameGrid[currentI][currentJ];
    }

    // Move controllable block last
    gameGrid[i + deltaI][j + deltaJ] = gameGrid[i][j];
    gameGrid[i][j] = blockFactory.createBlock("EmptyVisualBlock");
  }

  private boolean isValidMove(int i, int j, AbstractBlock[][] gameGrid) {
    return i >= 0 && i < gameGrid.length && j >= 0 && j < gameGrid[i].length;
  }

}
