package oogasalad.model.gameplay.blocks.visualblocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;
import oogasalad.model.gameplay.strategies.Strategy;
import oogasalad.shared.loader.PropertiesLoader;

/**
 * Abstract base class for visual blocks in the game, handling attributes and behaviors specific to visual elements.
 *
 * @author Philip Lee.
 */
public abstract class AbstractVisualBlock extends AbstractBlock {

  private static final String ATTRIBUTE_MAPPINGS = "blockbehaviors/attributemap.properties";
  private static final String BLOCK_PROPERTIES = "blockbehaviors/behaviors.properties";
  private static final String VISUAL_BLOCK = "VisualBlock";
  private static final String TEXT_BLOCK = "TextBlock";
  private static final String ATTRIBUTES = "attributes";
  private static final String EMPTY_STRING = "";
  private static final String REGEX_SPLIT = ",";
  private final List<Strategy> behaviors = new ArrayList<>();
  private final Properties properties;
  private final String name;

  private Map<String, String> attributeTranslationMap = new HashMap<>();
  private Map<String, Boolean> attributes = new HashMap<>();
  private int row;
  private int col;

  /**
   * Constructs a new visual block with specified properties.
   *
   * @param name the identifier name of the block
   * @param row the initial row location of the block
   * @param col the initial column location of the block
   */
  public AbstractVisualBlock(String name, int row, int col) {
    super();
    this.properties = PropertiesLoader.loadProperties(BLOCK_PROPERTIES);
    this.name = name;
    this.row = row;
    this.col = col;
    loadAttributeMappings();
    initializeAttributes();
  }

  /**
   * Accepts a visitor to process this block.
   *
   * @param visitor The BlockVisitor instance to process this block.
   */
  @Override
  public abstract void accept(BlockVisitor visitor);

  /**
   * Determines if the block is a text block.
   *
   * @param descriptor The descriptor to match against the block.
   * @return true if the descriptor matches the block's class name, false
   */
  @Override
  public boolean matches(String descriptor) {
    String normalizedBlockName = name.replace(VISUAL_BLOCK, EMPTY_STRING);
    return normalizedBlockName.equalsIgnoreCase(descriptor.replace(TEXT_BLOCK, EMPTY_STRING));
  }

  /**
   * Resets all behaviors associated with the block and its attributes.
   */
  @Override
  public void resetAllBehaviors() {
    behaviors.clear();
    attributes.replaceAll((k, v) -> false);
  }

  /**
   * Executes the behaviors associated with the block.
   *
   * @param block The block to control.
   * @param updater The updater to update the block.
   * @param iterator The iterator to control.
   */
  @Override
  public void executeBehaviors(AbstractBlock block, BlockUpdater updater, CellIterator iterator) {
    behaviors.forEach(behavior -> behavior.execute(block, updater, iterator));
  }

  /**
   * Checks if the block has a specific behavior.
   *
   * @param attribute The name of the attribute to query.
   * @return true if the block has the specified attribute, false otherwise.
   */
  @Override
  public boolean getAttribute(String attribute) {
    return attributes.getOrDefault(attribute, false);
  }

  /**
   * Retrieves the block's attributes.
   *
   * @return An iterator of the block's attributes.
   */
  @Override
  public Optional<Iterator<Map.Entry<String, Boolean>>> getAttributeIterator() {
    return Optional.of(attributes.entrySet().iterator());
  }

  /**
   * Retrieves the name of the block.
   *
   * @return The simple class name of this block instance.
   */
  @Override
  public String getBlockName() {
    return name;
  }

  /**
   * Retrieves the row of the block.
   *
   * @return The row of the block.
   */
  @Override
  public int getRow() {
    return row;
  }

  /**
   * Sets the row of the block.
   *
   * @param row The row to set.
   */
  @Override
  public void setRow(int row) {
    this.row = row;
  }

  /**
   * Retrieves the column of the block.
   *
   * @return The column of the block.
   */
  @Override
  public int getCol() {
    return col;
  }

  /**
   * Sets the column of the block.
   *
   * @param col The column to set.
   */
  @Override
  public void setCol(int col) {
    this.col = col;
  }

  /**
   * Modifies the block's attribute.
   *
   * @param attribute The attribute to modify.
   * @param value The new value of the attribute.
   */
  public void modifyAttribute(String attribute, boolean value) {
    Optional.ofNullable(attributeTranslationMap.get(attribute))
        .ifPresentOrElse(key -> attributes.put(key, value),
            () -> {
              throw new InvalidBlockName("Attribute not recognized: " + attribute);
            });
  }

  /**
   * Adds a behavior to the block.
   *
   * @param behavior The behavior to add.
   */
  public void addBehavior(Strategy behavior) {
    behaviors.add(behavior);
  }

  /**
   * Retrieves the block's behaviors.
   */
  private void loadAttributeMappings() {
    Properties mappingProps = PropertiesLoader.loadProperties(ATTRIBUTE_MAPPINGS);
    attributeTranslationMap = mappingProps.stringPropertyNames()
        .stream()
        .collect(Collectors.toMap(key -> key, mappingProps::getProperty));
  }

  /**
   * Initializes the block's attributes.
   */
  private void initializeAttributes() {
    String attributesList = properties.getProperty(ATTRIBUTES);
    attributes = Arrays.stream(attributesList.split(REGEX_SPLIT))
        .collect(Collectors.toMap(String::trim, k -> false));
  }

}
