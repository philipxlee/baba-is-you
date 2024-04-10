package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.Grid;

/**
 * Interface defining a strategy or behavior that can be applied to a visual block, including
 * interactions with other strategies.
 */
public interface Strategy {

  /**
   * Executes the strategy on the given block, potentially altering its state or position based on
   * the provided direction and grid context.
   *
   * @param grid The grid containing the block to act upon.
   * @param i    The x-coordinate of the block to act upon.
   * @param j    The y-coordinate of the block to act upon.
   * @param k    The z-coordinate of the block to act upon.
   */
  void execute(Grid grid, BlockUpdater updater, int i, int j, int k);
}
