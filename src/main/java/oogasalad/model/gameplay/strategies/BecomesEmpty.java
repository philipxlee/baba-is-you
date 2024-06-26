package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;

/**
 * This class is a concrete implementation of the AbstractBecomesBehaviors class. It represents the
 * behavior of a block when it becomes empty.
 *
 * @author Philip Lee.
 */
public class BecomesEmpty extends AbstractBecomesBehavior {

  private static final String EMPTY_VISUAL_BLOCK = "EmptyVisualBlock";

  /**
   * Constructor for BecomesEmpty.
   *
   * @param block    block to control.
   * @param updater  updater to update block.
   * @param iterator iterator to control.
   */
  @Override
  public void execute(AbstractBlock block, BlockUpdater updater, CellIterator iterator) {
    updater.updateBlock(block, EMPTY_VISUAL_BLOCK);
  }

}

