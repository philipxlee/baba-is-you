package oogasalad.model.gameplay.grid;

import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.utils.exceptions.VisitorReflectionException;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.handlers.KeyHandler;
import oogasalad.model.gameplay.interpreter.RuleInterpreter;

public class Grid {
  private AbstractBlock[][] grid;
  private RuleInterpreter parser;
  private KeyHandler keyHandler;
  private BlockFactory factory;

  public Grid(int rows, int cols) throws InvalidBlockName {
    this.grid = new AbstractBlock[rows][cols];
    this.parser = new RuleInterpreter();
    this.factory = new BlockFactory();
    tempInitializeGrid();
  }

  // Call everytime there's a handle key press
  public void checkForRules() throws VisitorReflectionException {
    parser.interpretRules(grid);
  }

  public AbstractBlock[][] getGrid() {
    return this.grid;
  }

  private void tempInitializeGrid() throws InvalidBlockName {
    // Initialize grid with blocks
    String tempStringGrid[][] = {
      {"EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock"},
      {"EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "RockVisualBlock", "EmptyVisualBlock"},
      {"EmptyVisualBlock", "EmptyVisualBlock", "RockTextBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock"},
      {"EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock"},
      {"EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock"},
      {"EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "BabaTextBlock", "IsTextBlock", "YouTextBlock", "EmptyVisualBlock"},
      {"EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock"},
      {"BabaVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock", "EmptyVisualBlock"}
    };

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        this.grid[i][j] = factory.createBlock(tempStringGrid[i][j]);
      }
    }
  }
}
