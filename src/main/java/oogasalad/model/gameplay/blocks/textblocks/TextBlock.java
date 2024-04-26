package oogasalad.model.gameplay.blocks.textblocks;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.shared.loader.PropertiesLoader;

/**
 * Represents a text block within the game, providing text-specific functionality. This class
 * encapsulates the concept of a text block, including its name and associated grammar.
 *
 * @author Philip Lee.
 */
public class TextBlock extends AbstractBlock {

  private static final String GRAMMAR_PROPERTIES_PATH = "grammar/grammar.properties";
  private static final String TEXT_BLOCK_SUFFIX = "TextBlock";
  private static final String GRAMMAR_PROPERTY_SUFFIX = ".grammar";
  private static final String REGEX_DELIMITER = ",";

  private final String name;
  private final Properties properties;

  /**
   * Creates a new text block with the given type.
   *
   * @param type The type of the text block, not including the 'TextBlock' suffix.
   */
  public TextBlock(String type) {
    super();
    this.name = type + TEXT_BLOCK_SUFFIX;
    this.properties = PropertiesLoader.loadProperties(GRAMMAR_PROPERTIES_PATH);
  }

  /**
   * Retrieves the grammar associated with this text block.
   *
   * @return An iterator over the grammar rules for this text block.
   */
  @Override
  public Iterator<String> getBlockGrammarIterator() {
    String grammar = properties.getProperty(this.name + GRAMMAR_PROPERTY_SUFFIX);
    return Arrays.asList(grammar.split(REGEX_DELIMITER)).iterator();
  }

  /**
   * Confirms that this instance represents a text block.
   *
   * @return Always true, indicating this is a text block.
   */
  @Override
  public boolean isTextBlock() {
    return true;
  }

  /**
   * Retrieves the name of the text block.
   *
   * @return The name of this text block, incorporating its type.
   */
  @Override
  public String getBlockName() {
    return this.name;
  }
}
