package oogasalad.model.gameplay.blocks.textblocks;

import oogasalad.model.gameplay.blocks.textblocks.AbstractTextBlock;

/**
 * A text block that represents the "is" verb.
 */
public class IsTextBlock extends AbstractTextBlock {

  /**
   * Creates a new "is" text block.
   *
   * @param name the name of the block.
   */
  public IsTextBlock(String name) {
    super(name);
  }

  /**
   * Gets the grammar of the block.
   *
   * @return The grammar of the block.
   */
  @Override
  public String getBlockGrammar() {
    return TextType.VERB.toString();
  }
}
