package oogasalad.model.gameplay.blocks.textblocks;

import oogasalad.model.gameplay.blocks.AbstractBlock;

/**
 * Represents an abstract text block within the game, providing text-specific functionality.
 */
public abstract class AbstractTextBlock extends AbstractBlock {

  private final String name;
  private final String grammar = "default";


  public AbstractTextBlock(String name) {
    super();
    this.name = name;
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
