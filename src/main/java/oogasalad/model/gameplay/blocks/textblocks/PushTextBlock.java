package oogasalad.model.gameplay.blocks.textblocks;

import oogasalad.model.gameplay.blocks.textblocks.AbstractTextBlock;

/**
 * A text block that represents the "push" property.
 */
public class PushTextBlock extends AbstractTextBlock {

  /**
   * Creates a new "push" text block.
   *
   * @param name the name of the block.
   */
  public PushTextBlock(String name) {
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
