package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.LavaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WaterVisualBlock;
import oogasalad.model.gameplay.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.strategies.Strategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is a concrete implementation of the BlockVisitor interface. It represents the behavior
 * of a block when it is visited by a transformation visitor.
 *
 * @author Philip Lee.
 */
public class TransformationVisitor implements BlockVisitor {

  private static final Logger logger = LogManager.getLogger(TransformationVisitor.class);
  private static final String PREFIX = "Becomes";
  private static final String STRATEGY_PACKAGE = "oogasalad.model.gameplay.strategies.";

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
  private void transform(AbstractVisualBlock block) throws InvalidBlockName {
    try {
      Class<?> clazz = Class.forName(STRATEGY_PACKAGE + targetType);
      Strategy behavior = (Strategy) clazz.getDeclaredConstructor().newInstance();
      block.addBehavior(behavior);
    } catch (ReflectiveOperationException e) {
      logger.error("Transformation failed: " + e.getMessage());
    }
  }
}
