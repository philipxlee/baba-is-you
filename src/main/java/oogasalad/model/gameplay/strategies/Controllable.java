package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;

/**
 * This class is a strategy that is used to control the behavior of a block. It is used to implement
 * the Strategy design pattern.
 */
public class Controllable implements Strategy {

  /**
   * This method is used to execute the behavior of the block.
   *
   * @param block the block that is being controlled.
   */
  @Override
  public void execute(AbstractVisualBlock block) {

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
