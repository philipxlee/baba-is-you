package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;

/**
 * This class is a concrete implementation of the AbstractBecomesBehaviors class. It represents the
 * behavior of a block when it becomes a flag.
 */
public class BecomesFlag extends AbstractBecomesBehavior {

  private static final String FLAG_VISUAL_BLOCK = "FlagVisualBlock";

  /**
   * Constructor for BecomesFlag.
   *
   * @param block    block to control.
   * @param updater  updater to update block.
   * @param iterator iterator to control.
   */
  @Override
  public void execute(AbstractBlock block, BlockUpdater updater, CellIterator iterator) {
    if (onlyEmptyVisualBlock(block, iterator)) {
      updater.updateBlock(block, FLAG_VISUAL_BLOCK);
    }
  }
}
