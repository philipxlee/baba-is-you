package oogasalad.model.gameplay.handlers;

import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.shared.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.strategies.Controllable;

public class KeyHandler {

  private Grid grid;
  private BlockFactory blockFactory;

  public KeyHandler(Grid grid) {
    this.grid = grid;
    this.blockFactory = new BlockFactory();
  }

  public void handleKeyPress(KeyCode code) throws InvalidBlockName {
    AbstractBlock[][] gameGrid = grid.getGrid();


    // Iterate over the grid to find the controllable block
    for (int i = 0; i < gameGrid.length; i++) {
      for (int j = 0; j < gameGrid[i].length; j++) {
        AbstractBlock block = gameGrid[i][j];
        if (block.hasBehavior(Controllable.class)) {
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

          // Find out how many blocks need to be pushed and if there is space
          int length = 1;
          while (true) {
            int nextI = i + length * deltaI;
            int nextJ = j + length * deltaJ;
            if (isValidMove(nextI, nextJ, gameGrid) && !"EmptyVisualBlock".equals(
                gameGrid[nextI][nextJ].getBlockName())) {
              length++;
            } else {
              break;
            }
          }

          // Check if there is space for the last block to move
          int endI = i + length * deltaI;
          int endJ = j + length * deltaJ;
          if (!isValidMove(endI, endJ, gameGrid) || !"EmptyVisualBlock".equals(
              gameGrid[endI][endJ].getBlockName())) {
            return;  // No space to move the chain
          }

          // Move all blocks
          for (int k = length - 1; k > 0; k--) {
            int currentI = i + k * deltaI;
            int currentJ = j + k * deltaJ;
            int nextI = currentI + deltaI;
            int nextJ = currentJ + deltaJ;
            gameGrid[nextI][nextJ] = gameGrid[currentI][currentJ];
          }

          // Move Baba last
          gameGrid[i + deltaI][j + deltaJ] = gameGrid[i][j];
          gameGrid[i][j] = blockFactory.createBlock("EmptyVisualBlock");
          return;
        }
      }

    }
  }


  private boolean isValidMove(int x, int y, AbstractBlock[][] gameGrid) {
    return x >= 0 && x < gameGrid.length && y >= 0 && y < gameGrid[0].length;
  }
}
