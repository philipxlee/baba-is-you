package oogasalad.model.gameplay.blocks.textblocks;

import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.GrammarLoader;

/**
 * Represents a text block within the game, providing text-specific functionality.
 * This class encapsulates the concept of a text block, including its name and
 * associated grammar.
 */
public class TextBlock extends AbstractBlock {

  private static final GrammarLoader GRAMMAR_LOADER = new GrammarLoader();
  private static final String TEXT_BLOCK_SUFFIX = "TextBlock";
  private final String name;

  /**
   * Creates a new text block with the given type.
   *
   * @param type The type of the text block, not including the 'TextBlock' suffix.
   */
  public TextBlock(String type) {
    super();
    this.name = type + TEXT_BLOCK_SUFFIX;
  }

  /**
   * Gets the grammar associated with this text block.
   *
   * @return A list of grammatical elements associated with this text block.
   */
  @Override
  public List<String> getBlockGrammar() {
    return GRAMMAR_LOADER.getGrammarForBlock(this.name);
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
