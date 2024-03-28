package oogasalad.model.gameplay.grid;

import javafx.css.Rule;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.interpreter.RuleInterpreter;

public class Grid {
  private static final int ROWS = 10;
  private static final int COLS = 10;
  private AbstractBlock[][] grid;
  private RuleInterpreter parser;

  public Grid() {
    this.grid = new AbstractBlock[ROWS][COLS];
    this.parser = new RuleInterpreter();
  }

  public void checkForRules() {
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[row].length - 2; col++) {
        AbstractBlock firstBlock = grid[row][col];
        AbstractBlock secondBlock = grid[row][col + 1];
        AbstractBlock thirdBlock = grid[row][col + 2];
        if (firstBlock.isTextBlock() && secondBlock.isTextBlock() && thirdBlock.isTextBlock()) {
          processRule(firstBlock, secondBlock, thirdBlock, col, row);
        }
      }
    }
  }

  public void processRule(AbstractBlock first, AbstractBlock second, AbstractBlock third, int x, int y) {
    // Delegates the processing of a rule to RuleInterpreter.
    parser.processRule(first, second, third, this);
  }

  public AbstractBlock[][] getGrid() {
    return this.grid;
  }
}
