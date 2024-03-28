package oogasalad.model.gameplay.blocks;

import oogasalad.model.gameplay.grid.Grid;

public class AbstractBlock {

  public boolean isTextBlock() {
    return false;
  }

  public void checkRuleIfTextBlock(AbstractBlock abstractBlock, AbstractBlock block, Grid grid,
      int col, int row) {
    // Intentionally empty, to be overridden
  }
}
