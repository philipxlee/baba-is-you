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

  private static boolean isContainsNonEmptyVisualBlock(int j, AbstractBlock targetBlock,
      List<AbstractBlock>[] gameGrid) {
    return gameGrid[j]
        .stream()
        .anyMatch(block ->
            !block.isTextBlock() &&
                !block.getBlockName().equals("EmptyVisualBlock") &&
                !block.equals(targetBlock));
  }

  private static boolean isContainsTextBlock(int j, List<AbstractBlock>[] gameGrid) {
    return gameGrid[j]
        .stream()
        .anyMatch(AbstractBlock::isTextBlock);
  }

  /**
   * Update the block in the grid to a wall block if it does not contain any text or non-empty.
   *
   * @param grid    The grid containing the block to act upon.
   * @param updater The object responsible for updating the block in the grid.
   * @param i       The x-coordinate of the block to act upon.
   * @param j       The y-coordinate of the block to act upon.
   * @param k       The z-coordinate of the block to act upon.
   */
  @Override
  public void execute(Grid grid, BlockUpdater updater, int i, int j, int k) {
    List<AbstractBlock>[][] gameGrid = grid.getGrid();
    AbstractBlock targetBlock = gameGrid[i][j].get(k);
    boolean containsTextBlock = isContainsTextBlock(j, gameGrid[i]);
    boolean containsNonEmptyVisualBlock = isContainsNonEmptyVisualBlock(j, targetBlock,
        gameGrid[i]);
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
