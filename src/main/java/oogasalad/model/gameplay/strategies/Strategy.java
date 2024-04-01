package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;

/**
 * Interface defining a strategy or behavior that can be applied to a visual block,
 * including interactions with other strategies.
 */
public interface Strategy {

  /**
   * Executes the strategy on the given block, potentially altering its state or position
   * based on the provided direction and grid context.
   *
   * @param block The visual block on which to execute the strategy.
   */
  void execute(AbstractVisualBlock block);

  /**
   * Defines how this strategy interacts with another strategy acting upon the associated block.
   * Can be used for behaviors that depend on the interaction between different types of blocks
   * or strategies, such as a pushable block reacting to a controllable block.
   *
   * @param targetBlock The block that is the target of the interaction.
   * @param initiatingBlockStrategy The strategy that is acting upon the target block.
   * @return A boolean indicating whether the interaction was successful or had an effect.
   */
  boolean interactWith(AbstractVisualBlock targetBlock, Strategy initiatingBlockStrategy);
}
