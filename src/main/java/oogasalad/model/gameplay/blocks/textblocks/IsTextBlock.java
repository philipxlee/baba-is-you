package oogasalad.model.gameplay.blocks.textblocks;

import java.util.List;

/**
 * A text block that represents the "is" verb.
 */
public class IsTextBlock extends AbstractTextBlock {

  /**
   * Creates a new "is" text block.
   *
   * @param name the name of the block.
   */
  public IsTextBlock(String name) {
    super(name);
  }

  /**
   * Gets the grammar of the block.
   *
   * @return The grammar of the block.
   */
  @Override
  public List<String> getBlockGrammar() {
    return List.of("VERB");
  }
}
