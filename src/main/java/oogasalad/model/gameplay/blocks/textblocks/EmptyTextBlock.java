package oogasalad.model.gameplay.blocks.textblocks;

import java.util.Arrays;
import java.util.List;

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
  public List<String> getBlockGrammar() {
    return Arrays.asList("PROPERTY", "NOUN");
  }


}
