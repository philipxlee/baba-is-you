package oogasalad.model.gameplay.blocks.textblocks;

import java.util.Arrays;
import java.util.List;
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
  public List<String> getBlockGrammar() {
    return Arrays.asList("PROPERTY");
  }
}
