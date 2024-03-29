package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.strategies.Winnable;

/**
 * Visitor pattern for the winnable behavior.
 */
public class WinVisitor implements BlockVisitor {

  /**
   * Adds the winnable behavior to the baba block
   *
   * @param baba the baba block
   */
  @Override
  public void visit(BabaVisualBlock baba) {
    baba.addBehavior(new Winnable());
  }

  /**
   * Adds the winnable behavior to the wall block
   *
   * @param wall the wall block
   */
  @Override
  public void visit(WallVisualBlock wall) {
    wall.addBehavior(new Winnable());
  }

  /**
   * Adds the winnable behavior to the flag block
   *
   * @param flag the flag block
   */
  @Override
  public void visit(FlagVisualBlock flag) {
    flag.addBehavior(new Winnable());
  }

  /**
   * Adds the winnable behavior to the rock block
   *
   * @param rock the rock block
   */
  @Override
  public void visit(RockVisualBlock rock) {
    rock.addBehavior(new Winnable());
  }
}
