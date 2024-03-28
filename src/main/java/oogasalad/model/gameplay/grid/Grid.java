package oogasalad.model.gameplay.grid;

import oogasalad.model.gameplay.blocks.AbstractBlock;

public class Grid {
  private AbstractBlock[][] grid;

  public Grid(int rows, int cols) {
    grid = new AbstractBlock[rows][cols];
  }

  public void checkForRules() {
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[row].length - 2; col++) {
        AbstractBlock firstBlock = grid[row][col];
        if (firstBlock != null) {
          firstBlock.checkRuleIfTextBlock(grid[row][col + 1], grid[row][col + 2], this, col, row);
        }
      }
    }
  }

  public void processRule(AbstractBlock first, AbstractBlock second, AbstractBlock third, int x, int y) {
    // Call the rule checker here
  }
}

