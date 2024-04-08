package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.Grid;

/**
 * This class is a strategy that is used to control the behavior of a block. It is used to implement
 * the Strategy design pattern.
 */
public class Controllable implements Strategy {

  /**
   * This method is used to execute the behavior of the block.
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
  public String toString(){
    return "Controllable";
  }
  @Override
  public boolean interactWith(AbstractVisualBlock targetBlock, Strategy initiatingBlockStrategy) {
    return false;
  }
}
