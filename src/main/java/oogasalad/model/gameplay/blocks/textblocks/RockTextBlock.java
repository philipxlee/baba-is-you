package oogasalad.model.gameplay.blocks.textblocks;

import oogasalad.model.gameplay.blocks.textblocks.AbstractTextBlock;

/**
 * A text block that represents the "rock" noun.
 */
public class RockTextBlock extends AbstractTextBlock {

  /**
   * Creates a new "rock" text block.
   *
   * @param name the name of the block.
   */
  public RockTextBlock(String name) {
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
