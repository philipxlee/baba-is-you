package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.LavaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.strategies.becomes.BecomesBaba;
import oogasalad.model.gameplay.strategies.becomes.BecomesLava;

/**
 * Visitor pattern for the lava behavior.
 */
public class LavaVisitor implements BlockVisitor {

  /**
   * Adds the lava behavior to the baba block.
   *
   * @param baba the baba block
   */
  @Override
  public void visit(BabaVisualBlock baba) {
    baba.addBehavior(new BecomesLava());
  }

  /**
   * Adds the lava behavior to the wall block.
   *
   * @param wall the wall block.
   */
  @Override
  public void visit(WallVisualBlock wall) {
    wall.addBehavior(new BecomesLava());
  }


  /**
   * Adds the lava behavior to the rock block.
   *
   * @param rock the rock block.
   */
  @Override
  public void visit(RockVisualBlock rock) {
    rock.addBehavior(new BecomesLava());
  }

  /**
   * Adds the lava behavior to the flag block.
   *
   * @param flag the flag block.
   */
  @Override
  public void visit(FlagVisualBlock flag) {
    flag.addBehavior(new BecomesLava());
  }

  /**
   * Adds the lava behavior to the empty block.
   *
   * @param empty the empty block.
   */
  @Override
  public void visit(EmptyVisualBlock empty) {
    empty.addBehavior(new BecomesLava());
  }

  /**
   * Adds the lava behavior to the lava block.
   *
   * @param lava the lava block.
   */
  @Override
  public void visit(LavaVisualBlock lava) {
    lava.addBehavior(new BecomesLava());
  }
}