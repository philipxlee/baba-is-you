package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.strategies.Pushable;

public class RockVisualBlock extends AbstractVisualBlock {

  public RockVisualBlock(String name, String imagePath) {
    super(name, imagePath);
  }

  @Override
  public void accept(BlockVisitor visitor) {
    visitor.visit(this);
  }

}
