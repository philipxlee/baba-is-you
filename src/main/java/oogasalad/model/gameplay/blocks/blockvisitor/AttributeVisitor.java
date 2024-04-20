package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.*;

public class AttributeVisitor implements BlockVisitor {
  private String attribute;

  public AttributeVisitor(String attribute) {
    System.out.println("AttributeVisitor created");
    this.attribute = attribute;
  }

  @Override
  public void visit(BabaVisualBlock baba) {
    setAttribute(baba);
  }

  @Override
  public void visit(WallVisualBlock wall) {
    setAttribute(wall);
  }

  @Override
  public void visit(RockVisualBlock rock) {
    setAttribute(rock);
  }

  @Override
  public void visit(FlagVisualBlock flag) {
    setAttribute(flag);
  }

  @Override
  public void visit(EmptyVisualBlock empty) {
    setAttribute(empty);
  }

  @Override
  public void visit(LavaVisualBlock lava) {
    setAttribute(lava);
  }

  @Override
  public void visit(WaterVisualBlock water) {
    setAttribute(water);
  }

  private void setAttribute(AbstractVisualBlock block) {
    block.modifyAttribute(attribute, true);
  }
}
