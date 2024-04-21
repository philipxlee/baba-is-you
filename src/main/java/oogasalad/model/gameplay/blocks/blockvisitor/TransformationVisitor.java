package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.*;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * This class is a concrete implementation of the BlockVisitor interface. It represents the behavior
 * of a block when it is visited by a transformation visitor.
 */
public class TransformationVisitor implements BlockVisitor {

  private static final String PREFIX = "Becomes";
  private static final String STRATEGY_PACKAGE = "oogasalad.model.gameplay.strategies.becomes.";

  private final String targetType;

  /**
   * Constructor for TransformationVisitor.
   *
   * @param targetType target type to set.
   */
  public TransformationVisitor(String targetType) {
    this.targetType = PREFIX + targetType;
  }

  /**
   * Transforms the block to the target type.
   *
   * @param baba block to transform.
   */
  @Override
  public void visit(BabaVisualBlock baba) {
    transform(baba);
  }

  /**
   * Transforms the block to the target type.
   *
   * @param wall block to transform.
   */
  @Override
  public void visit(WallVisualBlock wall) {
    transform(wall);
  }

  /**
   * Transforms the block to the target type.
   *
   * @param rock block to transform.
   */
  @Override
  public void visit(RockVisualBlock rock) {
    transform(rock);
  }

  /**
   * Transforms the block to the target type.
   *
   * @param flag block to transform.
   */
  @Override
  public void visit(FlagVisualBlock flag) {
    transform(flag);
  }

  /**
   * Transforms the block to the target type.
   *
   * @param empty block to transform.
   */
  @Override
  public void visit(EmptyVisualBlock empty) {
    transform(empty);
  }

  /**
   * Transforms the block to the target type.
   *
   * @param lava block to transform.
   */
  @Override
  public void visit(LavaVisualBlock lava) {
    transform(lava);
  }

  /**
   * Transforms the block to the target type.
   *
   * @param water block to transform.
   */
  @Override
  public void visit(WaterVisualBlock water) {
    transform(water);
  }

  /**
   * Transforms the block to the target type.
   *
   * @param block block to transform.
   */
  private void transform(AbstractVisualBlock block) {
    try {
      Class<?> clazz = Class.forName(STRATEGY_PACKAGE + targetType);
      Strategy behavior = (Strategy) clazz.getDeclaredConstructor().newInstance();
      block.addBehavior(behavior);
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
             NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
      System.err.println("Transformation failed: " + e.getMessage());
    }
  }
}
