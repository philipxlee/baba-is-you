package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.Grid;

/**
 * A strategy that makes a block stoppable, meaning it will not move when pushed.
 */
public class Stoppable implements Strategy {

  /**
   * Executes the stoppable strategy.
   *
   * @param grid The grid containing the block to act upon.
   * @param i The x-coordinate of the block to act upon.
   * @param j The y-coordinate of the block to act upon.
   * @param k The z-coordinate of the block to act upon.
   */
  @Override
  public void execute(Grid grid, BlockUpdater updater, int i, int j, int k) {

  }

  @Override
  public boolean interactWith(AbstractVisualBlock targetBlock, Strategy initiatingBlockStrategy) {
    return false;
  }
}
