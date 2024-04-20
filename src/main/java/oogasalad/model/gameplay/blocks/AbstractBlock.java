package oogasalad.model.gameplay.blocks;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * Serves as the base class for all block types within the game, defining common properties and
 * behaviors. Subclasses should override methods as necessary to provide specific functionality.
 */
public abstract class AbstractBlock {

  public static final List<String> grammarType = new ArrayList<>();

  /**
   * Indicates whether this block is a text block. By default, a block is not a text block.
   * Subclasses representing text blocks should override this method.
   *
   * @return false by default, indicating this is not a text block.
   */
  public boolean isTextBlock() {
    return false;
  }

  /**
   * Determines if the block matches a specified descriptor, typically based on its class name.
   * Subclasses can rely on this implementation or override it for custom matching logic.
   *
   * @param descriptor The descriptor to match against the block.
   * @return true if the descriptor matches the block's class name, false otherwise.
   */
  public boolean matches(String descriptor) {
    return getBlockName().equals(descriptor);
  }

  /**
   * Checks if the block exhibits a specific behavior. This base implementation always returns
   * false. Blocks with behaviors should override this method to confirm the presence of a
   * behavior.
   *
   * @param behaviorType The behavior class to check against this block.
   * @return false by default, indicating no behavior of the specified type.
   */
  public boolean hasBehavior(Class<? extends Strategy> behaviorType) {
    return false;
  }

  /**
   * Retrieves the name of the block, typically derived from the class name.
   *
   * @return The simple class name of this block instance.
   */
  public String getBlockName() {
    return this.getClass().getSimpleName();
  }

  /**
   * Accepts a visitor for performing operations on the block. By default, this method does nothing.
   * Visual blocks or other specific block types should override to accept visitors properly.
   *
   * @param visitor The BlockVisitor instance to process this block.
   */
  public void accept(BlockVisitor visitor) {
    // Default implementation is intentionally left blank.
  }

  /**
   * Resets the behaviors of the block, if any. By default, there are no behaviors to reset.
   * Specific block types with behaviors should override this method.
   */
  public void resetAllBehaviors() {
    // Default implementation is intentionally left blank.
  }

  /**
   * Executes the block's behaviors. By default, there are no behaviors to execute. Blocks with
   * executable behaviors should override this method.
   */
  public void executeBehaviors(AbstractBlock block, BlockUpdater updater, CellIterator iterator) {
    // Default implementation is intentionally left blank.
  }

  /**
   * Gets the grammatical category of the block, useful for parsing or rule validation. By default,
   * it returns "default". Override in subclasses to provide specific grammar.
   *
   * @return The grammatical category of the block as a string, DEFAULT_GRAMMAR for the base class.
   */
  public List<String> getBlockGrammar() {
    return grammarType;
  }

  /**
   * Gets the row of the block.
   *
   * @return The row of the block.
   */
  public int getRow() {
    return 0;
  }

  /**
   * Sets the row of the block.
   *
   * @param row The row to set.
   */
  public void setRow(int row) {
    // Default implementation is intentionally left blank.
  }

  /**
   * Gets the column of the block.
   *
   * @return The column of the block.
   */
  public int getCol() {
    return 0;
  }

  /**
   * Sets the column of the block.
   *
   * @param col The column to set.
   */
  public void setCol(int col) {
    // Default implementation is intentionally left blank.
  }

  /**
   * Retrieves the value of a specific attribute.
   *
   * @param attribute The name of the attribute to query.
   * @return The boolean value of the attribute; returns false if the attribute is not found.
   */
  public boolean getAttribute(String attribute) {
    return false;
  }

}
