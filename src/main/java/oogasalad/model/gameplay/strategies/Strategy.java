package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.VisualBlocks.AbstractVisualBlock;

public interface Strategy {

  void execute(AbstractVisualBlock block);

}
