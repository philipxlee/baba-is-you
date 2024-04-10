package oogasalad.model.gameplay.strategies.becomes;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;

/**
 * This class is a concrete implementation of the AbstractBecomesBehaviors class. It represents the
 * behavior of a block when it becomes a wall.
 */
public class BecomesWall extends AbstractBecomesBehavior {

  private static final String WALL_VISUAL_BLOCK = "WallVisualBlock";

  /**
   * Constructor for BecomesWall.
   *
   * @param block block to control.
   * @param updater updater to update block.
   * @param iterator iterator to control.
   */
  @Override
  public void execute(AbstractBlock block, BlockUpdater updater, CellIterator iterator) {
    if (!containsTextBlock(iterator) && !containsNonEmptyVisualBlock(iterator, block)
        && onlyEmptyVisualBlock(block, iterator)) {
      System.out.println(block.getBlockName() + " becomes wall");
      System.out.println("Baba position: " + block.getRow() + " " + block.getCol());
      updater.updateBlock(block, WALL_VISUAL_BLOCK);
    }
  }
}
