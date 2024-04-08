package oogasalad.model.gameplay.strategies;

import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.Grid;

/**
 * This class is responsible for updating the block in the grid to a wall block.
 */
public class BecomesWall implements Strategy {

  /**
   * Update the block in the grid to a wall block if it does not contain any text or non-empty.
   *
   * @param grid The grid containing the block to act upon.
   * @param updater
   * @param i The x-coordinate of the block to act upon.
   * @param j The y-coordinate of the block to act upon.
   * @param k The z-coordinate of the block to act upon.
   */
  @Override
  public void execute(Grid grid, BlockUpdater updater, int i, int j, int k) {
    List<AbstractBlock>[][] gameGrid = grid.getGrid();
    boolean containsTextBlock = gameGrid[i][j]
        .stream()
        .anyMatch(AbstractBlock::isTextBlock);

    boolean containsNonEmptyVisualBlock = gameGrid[i][j]
        .stream()
        .anyMatch(block ->
            !block.isTextBlock() &&
                !block.getBlockName().equals("EmptyVisualBlock"));

    if (!containsTextBlock && !containsNonEmptyVisualBlock) {
      updater.updateBlock(i, j, k, "WallVisualBlock");
    }
  }

  @Override
  public boolean interactWith(AbstractVisualBlock targetBlock, Strategy initiatingBlockStrategy) {
    // TODO Auto-generated method stub
    return false;
  }

}
