package oogasalad.model.gameplay.blocks.textblocks;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.Grid;

public abstract class AbstractTextBlock extends AbstractBlock {

  private final String name;
  private final String imagePath;

  public AbstractTextBlock(String name, String imagePath) {
    this.name = name;
    this.imagePath = imagePath;
  }

  @Override
  public boolean isTextBlock() {
    return true;
  }

  @Override
  public void checkRuleIfTextBlock(AbstractBlock second, AbstractBlock third, Grid grid, int x, int y) {
    if (second.isTextBlock() && third.isTextBlock()) {
      grid.processRule(this, second, third, x, y);
    }
  }

  public String getName() {
    return name;
  }

}
