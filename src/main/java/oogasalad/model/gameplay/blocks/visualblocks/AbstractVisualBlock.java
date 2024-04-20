package oogasalad.model.gameplay.blocks.visualblocks;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * Represents an abstract visual block within the game, providing visual-specific functionality and
 * behavior management.
 */
public abstract class AbstractVisualBlock extends AbstractBlock {


  private static final String VISUAL_BLOCK = "VisualBlock";
  private static final String TEXT_BLOCK = "TextBlock";
  private static final String EMPTY_STRING = "";
  private final String name;
  private final List<Strategy> behaviors;
  private int row;
  private int col;

  /**
   * Constructs a new visual block with the given name.
   *
   * @param name The name of the visual block.
   */
  public AbstractVisualBlock(String name, int row, int col) {
    super();
    this.name = name;
    this.behaviors = new ArrayList<>();
    this.row = row;
    this.col = col;
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
    String normalizedBlockName = this.name.replace(VISUAL_BLOCK, EMPTY_STRING);
    return normalizedBlockName.equalsIgnoreCase(descriptor.replace(TEXT_BLOCK, EMPTY_STRING));
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

  /**
   * Executes all behaviors associated with this block, based on the provided direction and grid
   * context. This method is typically called by the grid to update the block's state, e.g. turning
   * an empty block into a wall.
   */
  @Override
  public void executeBehaviors(AbstractBlock block, BlockUpdater updater, CellIterator iterator) {
    for (Strategy behavior : behaviors) {
      behavior.execute(block, updater, iterator);
    }
  }

  /**
   * Gets the row of the block.
   *
   * @return the row of the block
   */
  @Override
  public int getRow() {
    return row;
  }

  /**
   * Sets the row of the block.
   *
   * @param row the row to set
   */
  @Override
  public void setRow(int row) {
    this.row = row;
  }

  /**
   * Gets the column of the block.
   *
   * @return the column of the block
   */
  @Override
  public int getCol() {
    return col;
  }

  /**
   * Sets the column of the block.
   *
   * @param col the column to set
   */
  @Override
  public void setCol(int col) {
    this.col = col;
  }

}