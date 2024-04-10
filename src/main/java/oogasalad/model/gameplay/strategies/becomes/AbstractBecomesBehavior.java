package oogasalad.model.gameplay.strategies.becomes;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * Abstract class that provides common behaviors for 'becomes' strategies.
 * This class encapsulates methods to check the presence of non-empty visual
 * blocks and text blocks within a grid cell.
 */
public abstract class AbstractBecomesBehavior implements Strategy {

  private static final String EMPTY_VISUAL_BLOCK = "EmptyVisualBlock";

  /**
   * Executes the behavior of the block.
   *
   * @param block block to control.
   * @param updater updater to update block.
   * @param iterator iterator to control.
   */
  @Override
  public abstract void execute(AbstractBlock block, BlockUpdater updater, CellIterator iterator);

  /**
   * Checks if the grid cell contains a non-empty visual block.
   *
   * @param iterator iterator to control.
   * @param targetBlock block to ignore.
   * @return true if the cell contains a non-empty visual block, false otherwise.
   */
  public boolean containsNonEmptyVisualBlock(CellIterator iterator, AbstractBlock targetBlock) {
    while (iterator.hasNext()) {
      AbstractBlock currentBlock = iterator.next();
      if (!currentBlock.isTextBlock() &&
          !currentBlock.getBlockName().equals(EMPTY_VISUAL_BLOCK) &&
          !currentBlock.equals(targetBlock)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the grid cell contains a text block.
   *
   * @param iterator iterator to control.
   * @return true if the cell contains a text block, false otherwise.
   */
  public boolean containsTextBlock(CellIterator iterator) {
    while (iterator.hasNext()) {
      if (iterator.next().isTextBlock()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the grid cell contains a non-empty visual block.
   *
   * @param block block to ignore.
   * @param iterator iterator to control.
   * @return true if the cell contains a non-empty visual block, false otherwise.
   */
  public boolean onlyEmptyVisualBlock(AbstractBlock block, CellIterator iterator) {
    iterator.reset();
    while (iterator.hasNext()) {
      AbstractBlock currentBlock = iterator.next();
      if (!currentBlock.getBlockName().equals(EMPTY_VISUAL_BLOCK) &&
          !currentBlock.equals(block)) {
        return false;
      }
    }
    return true;
  }

}
