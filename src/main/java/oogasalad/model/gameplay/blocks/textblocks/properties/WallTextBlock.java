package oogasalad.model.gameplay.blocks.textblocks.properties;

import oogasalad.model.gameplay.blocks.textblocks.AbstractTextBlock;

/**
 * A text block that represents the "wall" noun.
 */
public class WallTextBlock extends AbstractTextBlock {

  /**
   * Creates a new "wall" text block.
   *
   * @param name the name of the block.
   */
  public WallTextBlock(String name) {
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
