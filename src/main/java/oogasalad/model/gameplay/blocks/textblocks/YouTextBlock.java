package oogasalad.model.gameplay.blocks.textblocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
  public List<String> getBlockGrammar() {
    return Arrays.asList("PROPERTY");
  }
}
