package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;

public interface BlockVisitor {
  void visit(BabaVisualBlock baba);
  void visit(WallVisualBlock wall);
  void visit(RockVisualBlock rock);
  void visit(FlagVisualBlock flag);
}
