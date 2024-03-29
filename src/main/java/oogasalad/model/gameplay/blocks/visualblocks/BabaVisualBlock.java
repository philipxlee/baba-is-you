package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.strategies.Controllable;
import oogasalad.model.gameplay.strategies.Pushable;

public class BabaVisualBlock extends AbstractVisualBlock {

  public BabaVisualBlock(String name) {
    super(name);
  }

  @Override
  public void accept(BlockVisitor visitor) {
    visitor.visit(this);
  }

}
