package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.LavaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;

/**
 * Interface for a visitor pattern for the different types of blocks.
 */
public interface BlockVisitor {

  void visit(BabaVisualBlock baba);

  void visit(WallVisualBlock wall);

  void visit(RockVisualBlock rock);

  void visit(FlagVisualBlock flag);

  void visit(EmptyVisualBlock empty);

  void visit(LavaVisualBlock lava);

}
