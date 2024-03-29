package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.strategies.Stoppable;

public class WallVisualBlock extends AbstractVisualBlock {

  public WallVisualBlock(String name, String imagePath) {
    super(name, imagePath);
  }

  @Override
  public void accept(BlockVisitor visitor) {
    visitor.visit(this);
  }

}
