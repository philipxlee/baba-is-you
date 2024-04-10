package oogasalad.model.gameplay.strategies.becomes;

import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.Grid;

/**
 * This class is a concrete implementation of the AbstractBecomesBehaviors class. It represents the
 * behavior of a block when it becomes a rock.
 */
public class BecomesRock extends AbstractBecomesBehavior {

  private static final String ROCK_VISUAL_BLOCK = "RockVisualBlock";

  /**
   * This method updates the block at the given coordinates to be a RockVisualBlock.
   *
   * @param grid The grid containing the block to act upon.
   * @param updater The utility to update the block within the grid.
   * @param i The x-coordinate of the block to act upon.
   * @param j The y-coordinate of the block to act upon.
   * @param k The z-coordinate of the block to act upon.
   */
  @Override
  public void execute(Grid grid, BlockUpdater updater, int i, int j, int k) {
    if (!containsTextBlock(grid.getGrid()[i][j]) &&
        !containsNonEmptyVisualBlock(grid.getGrid()[i][j], grid.getGrid()[i][j].get(k))) {
      updater.updateBlock(i, j, k, ROCK_VISUAL_BLOCK);
    }
  }
}