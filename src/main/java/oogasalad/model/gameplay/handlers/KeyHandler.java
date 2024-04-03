package oogasalad.model.gameplay.handlers;

import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.strategies.Winnable;
import oogasalad.model.gameplay.strategies.Stoppable;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.Grid;
import java.util.List;

public class KeyHandler {

  private final Grid grid;

  public KeyHandler(Grid grid) {
    this.grid = grid;
    grid.checkForRules();
  }


  public void handleKeyPress(KeyCode code) {
    grid.checkBehaviors();
    List<int[]> controllableBlockPositions = grid.findControllableBlock();
    if(controllableBlockPositions.get(0) != null){
      for(int[] element : controllableBlockPositions){
        moveBlock(element[0], element[1], element[2], code, grid);
      }
    }
  }


  private void moveBlock(int i, int j, int k,  KeyCode code, Grid gameGrid) {
    int deltaI = 0;
    int deltaJ = 0;
    switch (code) {
      case UP -> deltaI = -1;
      case DOWN -> deltaI = 1;
      case LEFT -> deltaJ = -1;
      case RIGHT -> deltaJ = 1;
      default -> {
          return;
      }
    }

    // TEMPORARY: Exits the block when a winning condition is met
    // USED FOR DEMO PURPOSES
    int nextI = i + deltaI;
    int nextJ = j + deltaJ;

    if (!isValidMove(nextI, nextJ, k, gameGrid)) {
      return; // Don't move if the next position is invalid.
    }

    AbstractBlock nextBlock = gameGrid.getBlock(nextI, nextJ, k);
    if (nextBlock != null && nextBlock.hasBehavior(Winnable.class)) {
      System.out.println("Game over, you won!");
      System.exit(0);
    }

    int[] movement = calculateMovement(i, j, k, deltaI, deltaJ, gameGrid);
    if (movement != null) {
      performMovement(i, j, k, deltaI, deltaJ, movement[2], gameGrid);
    }
  }

  private int[] calculateMovement(int i, int j, int k, int deltaI, int deltaJ, Grid gameGrid) {
    int length = 1;

    while (true) {
      int nextI = i + length * deltaI; //gets next cell
      int nextJ = j + length * deltaJ; // gets next cell
      if (isValidMove(nextI, nextJ, k, gameGrid) && !"EmptyVisualBlock".equals(gameGrid.getBlock(nextI, nextJ,k).getBlockName())) {
        length++;
      }
      else{
        break;
      }
    }

    int endI = i + length * deltaI;
    int endJ = j + length * deltaJ;
    if (!isValidMove(endI, endJ, k, gameGrid) || !"EmptyVisualBlock".equals(gameGrid.getGrid()[endI][endJ].get(k).getBlockName())) {
      return null; // No space to move the chain
    }

    return new int[]{deltaI, deltaJ, length};
  }

  private void performMovement(int i, int j, int k, int deltaI, int deltaJ, int length, Grid gameGrid) {
    // Move all blocks
    for (int m = length - 1; m > 0; m--) {
      int currentI = i + m * deltaI;
      int currentJ = j + m * deltaJ;
      int nextI = currentI + deltaI;
      int nextJ = currentJ + deltaJ;
      gameGrid.moveBlock(currentI, currentJ, k, nextI, nextJ, k);
    }

    // Move controllable block last
    gameGrid.moveBlock(i, j, k, i+deltaI, j+deltaJ, k);
    gameGrid.setBlock(i, j, k, "EmptyVisualBlock");
  }

  private boolean isValidMove(int i, int j, int k, Grid gameGrid) {
    return i >= 0 && i < gameGrid.getGrid().length && j >= 0 && j < gameGrid.getGrid()[i].length && !gameGrid.getBlock(i, j,k).hasBehavior(Stoppable.class);
  }

}
