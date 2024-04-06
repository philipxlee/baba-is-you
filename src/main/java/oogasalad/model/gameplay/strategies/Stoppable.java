package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;

/**
 * A strategy that makes a block stoppable, meaning it will not move when pushed.
 */
public class Stoppable implements Strategy {

  /**
   * Executes the stoppable strategy.
   *
   * @param block the block that is being stopped.
   */
  @Override
  public void execute(AbstractVisualBlock block) {

  }
  @Override
  public String toString(){
    return "Stoppable";
  }

  @Override
  public boolean interactWith(AbstractVisualBlock targetBlock, Strategy initiatingBlockStrategy) {
    return false;
  }
}
