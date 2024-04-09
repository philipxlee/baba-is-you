package oogasalad.model.gameplay.blocks.textblocks;

import java.util.Arrays;
import java.util.List;

/**
 * A text block that represents the "stop" property.
 */
public class StopTextBlock extends AbstractTextBlock {

  /**
   * Creates a new "stop" text block.
   *
   * @param name the name of the block.
   */
  public StopTextBlock(String name) {
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
