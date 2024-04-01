package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;

/**
 * Strategy for blocks that can be won by the player.
 */
public class Winnable implements Strategy {

  /**
   * Executes the winnable strategy.
   *
   * @param block the block that is being won.
   */
  @Override
  public void execute(AbstractVisualBlock block) {

  }

  @Override
  public boolean interactWith(AbstractVisualBlock targetBlock, Strategy initiatingBlockStrategy) {
    return false;
  }

}
