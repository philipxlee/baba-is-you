package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.Block;

public interface Strategies {
  void applyRule(String behavior);
  void interactWith(Block block, Block otherBlock);
}
