package oogasalad.model.gameplay.blocks.visualblocks;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * Represents an abstract visual block within the game, providing visual-specific functionality and
 * behavior management.
 */
public abstract class AbstractVisualBlock extends AbstractBlock {

  private final String name;
  private final List<Strategy> behaviors;

  public AbstractVisualBlock(String name) {
    this.name = name;
    this.behaviors = new ArrayList<>();
  }

  /**
   * Accepts a visitor that can perform operations on this visual block.
   *
   * @param visitor The visitor to accept.
   */
  @Override
  public abstract void accept(BlockVisitor visitor);

  /**
   * Checks if this visual block has a specific behavior.
   *
   * @param behaviorType The class of the behavior to check for.
   * @return true if the block has the behavior, false otherwise.
   */
  @Override
  public boolean hasBehavior(Class<? extends Strategy> behaviorType) {
    return behaviors.stream().anyMatch(behaviorType::isInstance);
  }

  /**
   * Compares the block's name with a given descriptor after normalizing both strings.
   *
   * @param descriptor The descriptor to compare against the block's name.
   * @return true if the normalized names match, false otherwise.
   */
  @Override
  public boolean matches(String descriptor) {
    String normalizedBlockName = this.name.replace("VisualBlock", "");
    return normalizedBlockName.equalsIgnoreCase(descriptor.replace("TextBlock", ""));
  }

  /**
   * Adds a behavior to this visual block.
   *
   * @param behavior The behavior to add.
   */
  public void addBehavior(Strategy behavior) {
    this.behaviors.add(behavior);
  }

  /**
   * Resets all behaviors associated with this visual block.
   */
  @Override
  public void resetAllBehaviors() {
    behaviors.clear();
  }

  /**
   * Gets the name of the visual block.
   *
   * @return The name of the visual block.
   */
  @Override
  public String getBlockName() {
    return name;
  }
}