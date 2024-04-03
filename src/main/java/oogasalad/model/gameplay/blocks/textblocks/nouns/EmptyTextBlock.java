package oogasalad.model.gameplay.blocks.textblocks.nouns;

import oogasalad.model.gameplay.blocks.textblocks.AbstractTextBlock;

/**
 * A text block that represents the "empty" noun.
 */
public class EmptyTextBlock extends AbstractTextBlock {

  /**
   * Creates a new "empty" text block.
   *
   * @param name the name of the block.
   */
  public EmptyTextBlock(String name) {
    super(name);
  }

  /**
   * Gets the grammar of the block.
   *
   * @return The grammar of the block.
   */
  @Override
  public String getBlockGrammar() {
    return TextType.NOUN.toString();
  }

}
