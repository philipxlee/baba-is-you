package oogasalad.model.gameplay.strategies.becomes;


import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;

/**
 * This class is a concrete implementation of the AbstractBecomesBehaviors class. It represents the
 * behavior of a block when it becomes a rock.
 */
public class BecomesRock extends AbstractBecomesBehavior {

  private static final String ROCK_VISUAL_BLOCK = "RockVisualBlock";

  /**
   * Constructor for BecomesRock.
   *
   * @param block block to control.
   * @param updater updater to update block.
   * @param iterator iterator to control.
   */
  @Override
  public void execute(AbstractBlock block, BlockUpdater updater, CellIterator iterator) {
    if (onlyEmptyVisualBlock(block, iterator)) {
      updater.updateBlock(block, ROCK_VISUAL_BLOCK);
    }
  }
}
