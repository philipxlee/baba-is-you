package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.LavaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WaterVisualBlock;

/**
 * Interface for a visitor pattern for the different types of blocks.
 *
 * @author Philip Lee.
 */
public interface BlockVisitor {

  /**
   * Visits a BabaVisualBlock.
   *
   * @param baba BabaVisualBlock to visit.
   */
  void visit(BabaVisualBlock baba);

  /**
   * Visits a WallVisualBlock.
   *
   * @param wall WallVisualBlock to visit.
   */
  void visit(WallVisualBlock wall);

  /**
   * Visits a RockVisualBlock.
   *
   * @param rock RockVisualBlock to visit.
   */
  void visit(RockVisualBlock rock);

  /**
   * Visits a FlagVisualBlock.
   *
   * @param flag FlagVisualBlock to visit.
   */
  void visit(FlagVisualBlock flag);

  /**
   * Visits an EmptyVisualBlock.
   *
   * @param empty EmptyVisualBlock to visit.
   */
  void visit(EmptyVisualBlock empty);

  /**
   * Visits a LavaVisualBlock.
   *
   * @param lava LavaVisualBlock to visit.
   */
  void visit(LavaVisualBlock lava);

  /**
   * Visits a WaterVisualBlock.
   *
   * @param water WaterVisualBlock to visit.
   */
  void visit(WaterVisualBlock water);

}
