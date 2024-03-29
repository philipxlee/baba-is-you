package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

public class EmptyVisualBlock extends AbstractVisualBlock {
  public EmptyVisualBlock(String name, String imagePath) {
    super(name, imagePath);

  }

  @Override
  public void accept(BlockVisitor visitor) {
    // Do nothing
  }
}
