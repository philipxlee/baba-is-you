package oogasalad.model.gameplay.blocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * Represents a generic block within the game. This class provides the base functionality and
 * interface for all blocks.
 */
public abstract class AbstractBlock {

  /**
   * Determines if this block is a text block.
   *
   * @return true if this is a text block, false otherwise.
   */
  public boolean isTextBlock() {
    return false;
  }

  /**
   * Checks if this block matches a given descriptor.
   *
   * @param descriptor The descriptor to match against the block.
   * @return true if the block matches the descriptor, false otherwise.
   */
  public boolean matches(String descriptor) {
    return this.getClass().getSimpleName().equals(descriptor);
  }

  /**
   * Checks if this block has a specific behavior.
   *
   * @param behaviorType The class of the behavior to check for.
   * @return true if the block has the behavior, false otherwise.
   */
  public boolean hasBehavior(Class<? extends Strategy> behaviorType) {
    return false;
  }

  /**
   * Gets the name of the block.
   *
   * @return The name of the block.
   */
  public String getBlockName() {
    return this.getClass().getSimpleName();
  }

  /**
   * Accepts a visitor that can perform operations on this block.
   *
   * @param visitor The visitor to accept.
   */
  public void accept(BlockVisitor visitor) {
    // Default implementation does nothing since only visual blocks accept visitors.
  }

  /**
   * Resets all behaviors assigned to this block.
   */
  public void resetAllBehaviors() {
    // Default implementation does nothing since only visual blocks have behaviors to reset.
  }

  /**
   * Executes all behaviors associated with this block, based on the provided direction and grid context.
   *
   */
  public void executeBehaviors() {
    // Default implementation does nothing since only visual blocks have behaviors to execute.
  }

}