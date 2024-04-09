package oogasalad.model.gameplay.blocks.textblocks;

import java.util.Arrays;
import java.util.List;

/**
 * A text block that represents the "baba" noun.
 */
public class BabaTextBlock extends AbstractTextBlock {

  /**
   * Creates a new "baba" text block.
   *
   * @param name the name of the block.
   */
  public BabaTextBlock(String name) {
    super(name);
  }

  /**
   * Gets the grammar of the block.
   *
   * @return The grammar of the block.
   */
  @Override
  public List<String> getBlockGrammar() {
    return Arrays.asList("NOUN", "PROPERTY");
  }
}
