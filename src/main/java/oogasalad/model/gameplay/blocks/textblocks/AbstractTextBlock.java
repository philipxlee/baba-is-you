package oogasalad.model.gameplay.blocks.textblocks;

import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.GrammarLoader;

/**
 * Represents an abstract text block within the game, providing text-specific functionality.
 */
public abstract class AbstractTextBlock extends AbstractBlock {

  private static final GrammarLoader GRAMMAR_LOADER = new GrammarLoader();
  private final String name;

  /**
   * Creates a new text block with the given name.
   *
   * @param name The name of the text block.
   */
  public AbstractTextBlock(String name) {
    super();
    this.name = name;
  }

  /**
   * Gets the grammar for this text block.
   *
   * @return The grammar for this text block.
   */
  public List<String> getBlockGrammar() {
    return GRAMMAR_LOADER.getGrammarForBlock(getClass().getSimpleName());
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
