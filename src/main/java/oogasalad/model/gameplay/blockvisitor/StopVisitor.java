package oogasalad.model.gameplay.blockvisitor;

import oogasalad.shared.blocks.visualblocks.BabaVisualBlock;
import oogasalad.shared.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.shared.blocks.visualblocks.FlagVisualBlock;
import oogasalad.shared.blocks.visualblocks.RockVisualBlock;
import oogasalad.shared.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.strategies.Stoppable;

/**
 * Visitor pattern for the stoppable behavior.
 */
public class StopVisitor implements BlockVisitor {

  /**
   * Adds the stoppable behavior to the baba block.
   *
   * @param baba the baba block.
   */
  @Override
  public void visit(BabaVisualBlock baba) {
    baba.addBehavior(new Stoppable());
  }

  /**
   * Adds the stoppable behavior to the wall block.
   *
   * @param wall the wall block.
   */
  @Override
  public void visit(WallVisualBlock wall) {
    wall.addBehavior(new Stoppable());
  }

  /**
   * Adds the stoppable behavior to the flag block.
   *
   * @param flag the flag block.
   */
  @Override
  public void visit(FlagVisualBlock flag) {
    flag.addBehavior(new Stoppable());
  }

  /**
   * Adds the stoppable behavior to the rock block.
   *
   * @param rock the rock block.
   */
  @Override
  public void visit(RockVisualBlock rock) {
    rock.addBehavior(new Stoppable());
  }

  /**
   * Adds the stoppable behavior to the empty block.
   *
   * @param empty the empty block.
   */
  @Override
  public void visit(EmptyVisualBlock empty) {
    empty.addBehavior(new Stoppable());
  }

}
