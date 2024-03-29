package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.strategies.Controllable;

/**
 * Visitor pattern for the "you" behavior
 */
public class YouVisitor implements BlockVisitor {

  /**
   * Adds the controllable behavior to the baba block
   *
   * @param baba the baba block
   */
  @Override
  public void visit(BabaVisualBlock baba) {
    baba.addBehavior(new Controllable());
  }

  /**
   * Adds the controllable behavior to the wall block
   *
   * @param wall the wall block
   */
  @Override
  public void visit(WallVisualBlock wall) {
    wall.addBehavior(new Controllable());
  }

  /**
   * Adds the controllable behavior to the flag block
   *
   * @param flag the flag block
   */
  @Override
  public void visit(FlagVisualBlock flag) {
    flag.addBehavior(new Controllable());
  }

  /**
   * Adds the controllable behavior to the rock block
   *
   * @param rock  the rock block
   */
  @Override
  public void visit(RockVisualBlock rock) {
    rock.addBehavior(new Controllable());
  }
}
