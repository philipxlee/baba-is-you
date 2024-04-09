package oogasalad.model.gameplay.blocks.textblocks;

import java.util.List;

/**
 * A text block that represents the "win" property.
 */
public class WinTextBlock extends AbstractTextBlock {

  /**
   * Creates a new "win" text block.
   *
   * @param name the name of the block.
   */
  public WinTextBlock(String name) {
    super(name);
  }

  /**
   * Gets the grammar of the block.
   *
   * @return The grammar of the block.
   */
  @Override
  public List<String> getBlockGrammar() {
    return List.of("PROPERTY");
  }

}
