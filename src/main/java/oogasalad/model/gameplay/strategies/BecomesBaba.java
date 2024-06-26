package oogasalad.model.gameplay.strategies;


import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;

/**
 * This class is a concrete implementation of the AbstractBecomesBehaviors class. It represents the
 * behavior of a block when it becomes Baba.
 *
 * @author Philip Lee.
 */
public class BecomesBaba extends AbstractBecomesBehavior {

  private static final String BABA_VISUAL_BLOCK = "BabaVisualBlock";

  /**
   * Constructor for BecomesBaba.
   *
   * @param block    block to control.
   * @param updater  updater to update block.
   * @param iterator iterator to control.
   */
  @Override
  public void execute(AbstractBlock block, BlockUpdater updater, CellIterator iterator) {
    if (onlyEmptyVisualBlock(block, iterator)) {
      updater.updateBlock(block, BABA_VISUAL_BLOCK);
    }
  }

}
