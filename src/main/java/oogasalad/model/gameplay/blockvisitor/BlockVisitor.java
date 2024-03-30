package oogasalad.model.gameplay.blockvisitor;

import oogasalad.shared.blocks.visualblocks.BabaVisualBlock;
import oogasalad.shared.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.shared.blocks.visualblocks.FlagVisualBlock;
import oogasalad.shared.blocks.visualblocks.RockVisualBlock;
import oogasalad.shared.blocks.visualblocks.WallVisualBlock;

/**
 * Interface for a visitor pattern for the different types of blocks.
 */
public interface BlockVisitor {

  void visit(BabaVisualBlock baba);

  void visit(WallVisualBlock wall);

  void visit(RockVisualBlock rock);

  void visit(FlagVisualBlock flag);

  void visit(EmptyVisualBlock empty);

}
