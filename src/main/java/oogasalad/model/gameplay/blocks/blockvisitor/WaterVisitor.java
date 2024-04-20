package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.LavaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WaterVisualBlock;
import oogasalad.model.gameplay.strategies.becomes.BecomesWater;

/**
 * Visitor pattern for the water behavior.
 */
public class WaterVisitor implements BlockVisitor {

  /**
   * Adds the water behavior to the baba block.
   *
   * @param baba the baba block
   */
  @Override
  public void visit(BabaVisualBlock baba) {
    baba.addBehavior(new BecomesWater());
  }

  /**
   * Adds the water behavior to the wall block.
   *
   * @param water the water block.
   */
  @Override
  public void visit(WallVisualBlock water) {
    water.addBehavior(new BecomesWater());
  }

  /**
   * Adds the water behavior to the rock block.
   *
   * @param rock the rock block.
   */
  @Override
  public void visit(RockVisualBlock rock) {
    rock.addBehavior(new BecomesWater());
  }

  /**
   * Adds the water behavior to the flag block.
   *
   * @param flag the flag block.
   */
  @Override
  public void visit(FlagVisualBlock flag) {
    flag.addBehavior(new BecomesWater());
  }

  /**
   * Adds the water behavior to the empty block.
   *
   * @param empty the empty block.
   */
  @Override
  public void visit(EmptyVisualBlock empty) {
    empty.addBehavior(new BecomesWater());
  }

  /**
   * Adds the water behavior to the lava block.
   *
   * @param lava the lava block.
   */
  @Override
  public void visit(LavaVisualBlock lava) {
    lava.addBehavior(new BecomesWater());
  }

  /**
   * Adds the water behavior to the water block.
   *
   * @param water the water block.
   */
  @Override
  public void visit(WaterVisualBlock water) {
    water.addBehavior(new BecomesWater());
  }

}
