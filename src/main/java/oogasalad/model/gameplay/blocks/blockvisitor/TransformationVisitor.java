package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.*;
import oogasalad.model.gameplay.strategies.Strategy;

public class TransformationVisitor implements BlockVisitor {
  private String targetType;

  public TransformationVisitor(String targetType) {
    this.targetType = "Becomes" + targetType;
    System.out.println("TransformationVisitor created");
  }

  private void transform(AbstractVisualBlock block) {
    try {
      Class<?> clazz = Class.forName("oogasalad.model.gameplay.strategies.becomes." + targetType);
      Strategy behavior = (Strategy) clazz.getDeclaredConstructor().newInstance();
      block.addBehavior(behavior);
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
             NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
      System.err.println("Transformation failed: " + e.getMessage());
    }
  }

  @Override
  public void visit(BabaVisualBlock baba) {
    transform(baba);
  }

  @Override
  public void visit(WallVisualBlock wall) {
    transform(wall);
  }

  @Override
  public void visit(RockVisualBlock rock) {
    transform(rock);
  }

  @Override
  public void visit(FlagVisualBlock flag) {
    transform(flag);
  }

  @Override
  public void visit(EmptyVisualBlock empty) {
    transform(empty);
  }

  @Override
  public void visit(LavaVisualBlock lava) {
    transform(lava);
  }

  @Override
  public void visit(WaterVisualBlock water) {
    transform(water);
  }
}
