package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;

/**
 * Abstract class that provides common behaviors for 'becomes' strategies. This class encapsulates
 * methods to check the presence of non-empty visual blocks and text blocks within a grid cell.
 *
 * @author Philip Lee.
 */
public abstract class AbstractBecomesBehavior implements Strategy {

  private static final String EMPTY_VISUAL_BLOCK = "EmptyVisualBlock";

  /**
   * Executes the behavior of the block.
   *
   * @param block    block to control.
   * @param updater  updater to update block.
   * @param iterator iterator to control.
   */
  @Override
  public abstract void execute(AbstractBlock block, BlockUpdater updater, CellIterator iterator);

  /**
   * Checks if the grid cell contains a non-empty visual block.
   *
   * @param block    block to ignore.
   * @param iterator iterator to control.
   * @return true if the cell contains a non-empty visual block, false otherwise.
   */
  public boolean onlyEmptyVisualBlock(AbstractBlock block, CellIterator iterator) {
    while (iterator.hasNext()) {
      AbstractBlock currentBlock = iterator.next();
      if (!currentBlock.isTextBlock() && !currentBlock.equals(block)) {
        return false;
      }
    }
    return true;
  }

}
