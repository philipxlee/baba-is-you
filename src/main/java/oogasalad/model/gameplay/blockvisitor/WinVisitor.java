package oogasalad.model.gameplay.blockvisitor;

import oogasalad.shared.blocks.visualblocks.BabaVisualBlock;
import oogasalad.shared.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.shared.blocks.visualblocks.FlagVisualBlock;
import oogasalad.shared.blocks.visualblocks.RockVisualBlock;
import oogasalad.shared.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.strategies.Winnable;

/**
 * Visitor pattern for the winnable behavior.
 */
public class WinVisitor implements BlockVisitor {

  /**
   * Adds the winnable behavior to the baba block.
   *
   * @param baba the baba block.
   */
  @Override
  public void visit(BabaVisualBlock baba) {
    baba.addBehavior(new Winnable());
  }

  /**
   * Adds the winnable behavior to the wall block.
   *
   * @param wall the wall block.
   */
  @Override
  public void visit(WallVisualBlock wall) {
    wall.addBehavior(new Winnable());
  }

  /**
   * Adds the winnable behavior to the flag block.
   *
   * @param flag the flag block.
   */
  @Override
  public void visit(FlagVisualBlock flag) {
    flag.addBehavior(new Winnable());
  }

  /**
   * Adds the winnable behavior to the rock block.
   *
   * @param rock the rock block.
   */
  @Override
  public void visit(RockVisualBlock rock) {
    rock.addBehavior(new Winnable());
  }

  /**
   * Adds the winnable behavior to the empty block.
   *
   * @param empty the empty block.
   */
  @Override
  public void visit(EmptyVisualBlock empty) {
    empty.addBehavior(new Winnable());
  }
}
