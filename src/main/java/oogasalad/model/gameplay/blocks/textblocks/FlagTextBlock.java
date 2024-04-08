package oogasalad.model.gameplay.blocks.textblocks;

import java.util.Arrays;
import java.util.List;

/**
 * A text block that represents the "flag" noun.
 */
public class FlagTextBlock extends AbstractTextBlock {

  /**
   * Creates a new "flag" text block.
   *
   * @param name the name of the block.
   */
  public FlagTextBlock(String name) {
    super(name);
  }

  /**
   * Gets the grammar of the block.
   *
   * @return The grammar of the block.
   */
  @Override
  public List<String> getBlockGrammar() {
    return List.of("NOUN", "PROPERTY");
  }

}
