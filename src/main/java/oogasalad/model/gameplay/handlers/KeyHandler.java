package oogasalad.model.gameplay.handlers;

import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.strategies.Controllable;

import java.util.ArrayList;
import java.util.List;

public class KeyHandler {

  private final Grid grid;
  private final BlockFactory blockFactory;

  public KeyHandler(Grid grid) {
    this.grid = grid;
    this.blockFactory = new BlockFactory();
    grid.checkForRules();
  }


  public void handleKeyPress(KeyCode code) {
    List<AbstractBlock>[][] gameGrid = grid.getGrid();
    List<int[]> controllableBlockPositions = findControllableBlock(gameGrid);
    if(controllableBlockPositions.get(0) != null){
      for(int[] element : controllableBlockPositions){
        moveBlock(element[0], element[1], element[2], code, gameGrid);
      }
    }
  }

  private List<int[]> findControllableBlock(List<AbstractBlock>[][] gameGrid) {
    List<int[]> AllControllableBlocks = new ArrayList<>();
    for(int i = 0; i < gameGrid.length; i++){
      for(int j = 0; j < gameGrid[i].length; j++){
        for(int k= 0; k < gameGrid[i][j].size(); k++){
          AbstractBlock block = gameGrid[i][j].get(k);
          if(block != null && block.hasBehavior(Controllable.class)){
            int [] a = {i, j, k};
            AllControllableBlocks.add(a);
          }
        }
      }
    }
    return AllControllableBlocks;
  }

  private void moveBlock(int i, int j, int k,  KeyCode code, List<AbstractBlock>[][] gameGrid) {
    int deltaI = 0;
    int deltaJ = 0;
    switch (code) {
      case UP -> deltaI = -1;
      case DOWN -> deltaI = 1;
      case LEFT -> deltaJ = -1;
      case RIGHT -> deltaJ = 1;
      default -> deltaI = 0;
    }

    int[] movement = calculateMovement(i, j, k, deltaI, deltaJ, gameGrid);
    if (movement != null) {
      performMovement(i, j, k, deltaI, deltaJ, movement[2], gameGrid);
    }
  }

  private int[] calculateMovement(int i, int j, int k, int deltaI, int deltaJ, List<AbstractBlock>[][] gameGrid) {
    int length = 1;

    while (true) {
      int nextI = i + length * deltaI;
      int nextJ = j + length * deltaJ;
      if (isValidMove(nextI, nextJ, gameGrid) && !"EmptyVisualBlock".equals(gameGrid[nextI][nextJ].get(k).getBlockName())) {
        length++;
      }
      else{
        break;
      }
    }

    int endI = i + length * deltaI;
    int endJ = j + length * deltaJ;
    if (!isValidMove(endI, endJ, gameGrid) || !"EmptyVisualBlock".equals(gameGrid[endI][endJ].get(k).getBlockName())) {
      return null; // No space to move the chain
    }

    return new int[]{deltaI, deltaJ, length};
  }

  private void performMovement(int i, int j, int k, int deltaI, int deltaJ, int length, List<AbstractBlock>[][] gameGrid) {
    // Move all blocks
    for (int m = length - 1; m > 0; m--) {
      int currentI = i + m * deltaI;
      int currentJ = j + m * deltaJ;
      int nextI = currentI + deltaI;
      int nextJ = currentJ + deltaJ;
      gameGrid[nextI][nextJ] = gameGrid[currentI][currentJ];
    }

    // Move controllable block last
    gameGrid[i + deltaI][j + deltaJ] = gameGrid[i][j];
    gameGrid[i][j].set(k, blockFactory.createBlock("EmptyVisualBlock")) ;
  }

  private boolean isValidMove(int i, int j, List<AbstractBlock>[][] gameGrid) {
    return i >= 0 && i < gameGrid.length && j >= 0 && j < gameGrid[i].length;
  }

}
