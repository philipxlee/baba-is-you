package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.strategies.Winnable;

public class FlagVisualBlock extends AbstractVisualBlock {

  public FlagVisualBlock(String name) {
    super(name);
  }

  @Override
  public void accept(BlockVisitor visitor) {
    visitor.visit(this);
  }

}
