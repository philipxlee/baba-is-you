package oogasalad.model.gameplay.blocks.textblocks;

import oogasalad.model.gameplay.blocks.textblocks.AbstractTextBlock;

/**
 * A text block that represents the "you" property.
 */
public class YouTextBlock extends AbstractTextBlock {

  /**
   * Creates a new "you" text block.
   *
   * @param name the name of the block.
   */
  public YouTextBlock(String name) {
    super(name);
  }

  /**
   * Gets the grammar of the block.
   *
   * @return The grammar of the block.
   */
  @Override
  public String getBlockGrammar() {
    return TextType.PROPERTY.toString();
  }
}
