package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;

public interface Strategy {

  void execute(AbstractVisualBlock block);

}
