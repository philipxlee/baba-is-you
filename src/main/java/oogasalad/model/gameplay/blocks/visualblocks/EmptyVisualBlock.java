package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

public class EmptyVisualBlock extends AbstractVisualBlock {
  public EmptyVisualBlock(String name) {
    super(name);
  }

  @Override
  public void accept(BlockVisitor visitor) {
    // Do nothing
  }
}
