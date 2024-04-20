package oogasalad.model.gameplay.blocks.visualblocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;
import oogasalad.model.gameplay.strategies.Strategy;
import oogasalad.shared.util.PropertiesLoader;

/**
 * Represents an abstract visual block within the game, providing visual-specific functionality and
 * behavior management.
 */
public abstract class AbstractVisualBlock extends AbstractBlock {

  private static final Map<String, String> attributeTranslationMap = new HashMap<>();

  private static final String BLOCK_PROPERTIES = "blockbehaviors/behaviors.properties";
  private static final String VISUAL_BLOCK = "VisualBlock";
  private static final String TEXT_BLOCK = "TextBlock";
  private static final String EMPTY_STRING = "";
  private final String name;
  private final List<Strategy> behaviors;
  private final Map<String, Boolean> attributes;
  private final Properties properties;
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
    this.properties = PropertiesLoader.loadProperties(BLOCK_PROPERTIES);
    this.behaviors = new ArrayList<>(); // for BecomesX behaviors
    this.attributes = new HashMap<>(); // for Controllable, Pushable, etc.
    this.row = row;
    this.col = col;
    initializeAttributes();

    attributeTranslationMap.put("You", "Controllable");
    attributeTranslationMap.put("Win", "Winnable");
    attributeTranslationMap.put("Drown", "Drownable");
    attributeTranslationMap.put("Melt", "Meltable");
    attributeTranslationMap.put("Push", "Pushable");
    attributeTranslationMap.put("Hot", "Hotable");
    attributeTranslationMap.put("Sink", "Sinkable");
    attributeTranslationMap.put("Stop", "Stoppable");
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

  public List<Strategy>  getBehaviors(){
    return behaviors;
  }

  /**
   * Allows external modification of an attribute's boolean value.
   *
   * @param attribute The name of the attribute to modify.
   * @param value The new boolean value for the attribute.
   */
  public void modifyAttribute(String attribute, boolean value) {
    String attributeKey = attributeTranslationMap.get(attribute);
    if (attributes.containsKey(attributeKey)) {
      attributes.put(attribute, value);
    } else {
      throw new IllegalArgumentException("Attribute not recognized: " + attribute);
    }
  }

  private void initializeAttributes() {
    String attributesList = properties.getProperty("attributes");
    for (String attribute : attributesList.split(",")) {
      System.out.printf("Attribute: %s%n", attribute);
      attributes.put(attribute.trim(), false);
    }
  }
}