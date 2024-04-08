package oogasalad.model.gameplay.strategies;

import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.Grid;

/**
 * Strategy for when a block becomes a flag.
 */
public class BecomesFlag implements Strategy {

  // Need to fix DRY here
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


  @Override
  public void execute(Grid grid, BlockUpdater updater, int i, int j, int k) {
    List<AbstractBlock>[][] gameGrid = grid.getGrid();
    AbstractBlock targetBlock = gameGrid[i][j].get(k);
    boolean containsTextBlock = isContainsTextBlock(j, gameGrid[i]);
    boolean containsNonEmptyVisualBlock = isContainsNonEmptyVisualBlock(j, targetBlock,
        gameGrid[i]);
    if (!containsTextBlock && !containsNonEmptyVisualBlock) {
      updater.updateBlock(i, j, k, "FlagVisualBlock");
    }
  }

  @Override
  public boolean interactWith(AbstractVisualBlock targetBlock, Strategy initiatingBlockStrategy) {
    // TODO Auto-generated method stub
    return false;
  }
}
