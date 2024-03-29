package oogasalad.model.gameplay.blocks.textblocks;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.Grid;

public abstract class AbstractTextBlock extends AbstractBlock {

  private final String name;

  public AbstractTextBlock(String name) {
    this.name = name;
  }

  @Override
  public boolean isTextBlock() {
    return true;
  }


  public String getBlockName() {
    return this.name;
  }

}
