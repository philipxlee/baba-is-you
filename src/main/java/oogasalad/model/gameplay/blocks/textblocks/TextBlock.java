package oogasalad.model.gameplay.blocks.textblocks;

import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.GrammarLoader;

public class TextBlock extends AbstractBlock {

  private static final GrammarLoader GRAMMAR_LOADER = new GrammarLoader();
  private static final String TEXT_BLOCK_SUFFIX = "TextBlock";
  private final String name;

  /**
   * Creates a new text block with the given type.
   *
   * @param type The type of the text block.
   */
  public TextBlock(String type) {
    super();
    this.name = type + TEXT_BLOCK_SUFFIX;  // Use the type as the name
  }

  /**
   * Gets the grammar for this text block.
   *
   * @return The grammar for this text block.
   */
  @Override
  public List<String> getBlockGrammar() {
    return GRAMMAR_LOADER.getGrammarForBlock(this.name);
  }

  /**
   * Indicates that this block is a text block.
   *
   * @return Always true for text blocks.
   */
  @Override
  public boolean isTextBlock() {
    return true;
  }

  /**
   * Gets the name of the text block.
   *
   * @return The name of the text block.
   */
  @Override
  public String getBlockName() {
    return this.name;
  }
}
