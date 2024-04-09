package oogasalad.model.gameplay.blocks.textblocks;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import java.util.List;

// Assuming TextBlock is adjusted to be more generalized
public class TextBlock extends AbstractBlock {
  private final String name;
  private final List<String> grammar;

  public TextBlock(String name, List<String> grammar) {
    super();
    this.name = name;
    this.grammar = grammar;
  }

  @Override
  public boolean isTextBlock() {
    return true;
  }

  @Override
  public String getBlockName() {
    return name;
  }

  public List<String> getBlockGrammar() {
    return grammar;
  }
}
