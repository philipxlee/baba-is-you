package oogasalad.model.gameplay.strategies.attributes;

import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * This class is a strategy that is used to control the behavior of a block. It is used to implement
 * the Strategy design pattern.
 */
public class Controllable implements Strategy {

  /**
   * This method is used to execute the behavior of the block.
   *
   * @param grid The grid containing the block to act upon.
   * @param i    The x-coordinate of the block to act upon.
   * @param j    The y-coordinate of the block to act upon.
   * @param k    The z-coordinate of the block to act upon.
   */
  @Override
  public void execute(Grid grid, BlockUpdater updater, int i, int j, int k) {

  }

}
