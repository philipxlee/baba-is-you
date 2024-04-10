package oogasalad.model.gameplay.strategies.becomes;

import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * Abstract class that provides common behaviors for 'becomes' strategies.
 * This class encapsulates methods to check the presence of non-empty visual
 * blocks and text blocks within a grid cell.
 */
public abstract class AbstractBecomesBehavior implements Strategy {

  private static final String EMPTY_VISUAL_BLOCK = "EmptyVisualBlock";

  /**
   * Executes the specific 'becomes' behavior on the target block within the grid.
   *
   * @param grid The grid containing the block to act upon.
   * @param updater The utility to update the block within the grid.
   * @param i The x-coordinate of the block to act upon.
   * @param j The y-coordinate of the block to act upon.
   * @param k The z-coordinate of the block to act upon.
   */
  @Override
  public abstract void execute(Grid grid, BlockUpdater updater, int i, int j, int k);

  /**
   * Checks if the specified list of blocks contains a non-empty visual block other than the target block.
   *
   * @param blocks The list of blocks to check.
   * @param targetBlock The block to exclude in the check.
   * @return true if there is a non-empty visual block present; false otherwise.
   */
  public boolean containsNonEmptyVisualBlock(List<AbstractBlock> blocks, AbstractBlock targetBlock) {
    return blocks.stream().anyMatch(block ->
        !block.isTextBlock() &&
            !block.getBlockName().equals(EMPTY_VISUAL_BLOCK) &&
            !block.equals(targetBlock));
  }

  /**
   * Checks if the specified list of blocks contains a text block.
   *
   * @param blocks The list of blocks to check.
   * @return true if a text block is present; false otherwise.
   */
  public boolean containsTextBlock(List<AbstractBlock> blocks) {
    return blocks.stream().anyMatch(AbstractBlock::isTextBlock);
  }

}
