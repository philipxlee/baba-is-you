package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;

/**
 * Strategy interface that defines the execute method for a strategy. A strategy, or behavior, is a
 * class that defines a set of behaviors that can be applied to a block It is used to implement the
 * Strategy design pattern. For example, a block can have a pushable strategy, a stoppable strategy,
 * a controllable strategy, etc.
 */
public interface Strategy {

  void execute(AbstractVisualBlock block);

}
