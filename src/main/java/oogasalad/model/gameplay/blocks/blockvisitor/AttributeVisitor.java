package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.LavaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WaterVisualBlock;

/**
 * This class is a concrete implementation of the BlockVisitor interface. It represents the behavior
 * of a block when it is visited by an attribute visitor.
 *
 * @author Philip Lee.
 */
public class AttributeVisitor implements BlockVisitor {

  private final String attribute;

  /**
   * Constructor for AttributeVisitor.
   *
   * @param attribute attribute to set.
   */
  public AttributeVisitor(String attribute) {
    this.attribute = attribute;
  }

  /**
   * Sets the attribute of the block.
   *
   * @param baba block to set attribute.
   */
  @Override
  public void visit(BabaVisualBlock baba) {
    setAttribute(baba);
  }

  /**
   * Sets the attribute of the block.
   *
   * @param wall block to set attribute.
   */
  @Override
  public void visit(WallVisualBlock wall) {
    setAttribute(wall);
  }

  /**
   * Sets the attribute of the block.
   *
   * @param rock block to set attribute.
   */
  @Override
  public void visit(RockVisualBlock rock) {
    setAttribute(rock);
  }

  /**
   * Sets the attribute of the block.
   *
   * @param flag block to set attribute.
   */
  @Override
  public void visit(FlagVisualBlock flag) {
    setAttribute(flag);
  }

  /**
   * Sets the attribute of the block.
   *
   * @param empty block to set attribute.
   */
  @Override
  public void visit(EmptyVisualBlock empty) {
    setAttribute(empty);
  }

  /**
   * Sets the attribute of the block.
   *
   * @param lava block to set attribute.
   */
  @Override
  public void visit(LavaVisualBlock lava) {
    setAttribute(lava);
  }

  /**
   * Sets the attribute of the block.
   *
   * @param water block to set attribute.
   */
  @Override
  public void visit(WaterVisualBlock water) {
    setAttribute(water);
  }

  /**
   * Sets the attribute of the block.
   *
   * @param block block to set attribute.
   */
  private void setAttribute(AbstractVisualBlock block) {
    block.modifyAttribute(attribute, true);
  }
}
