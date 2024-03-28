package oogasalad.model.gameplay.grid;

import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.handlers.KeyHandler;
import oogasalad.model.gameplay.interpreter.RuleInterpreter;

public class Grid {
  private static final int ROWS = 10;
  private static final int COLS = 10;
  private AbstractBlock[][] grid;
  private RuleInterpreter parser;
  private KeyHandler keyHandler;
  private BlockFactory factory;

  public Grid() {
    this.grid = new AbstractBlock[ROWS][COLS];
    this.parser = new RuleInterpreter();
    this.keyHandler = new KeyHandler();
    tempInitializeGrid();
    this.factory = new BlockFactory();
  }

  public void handleKeyPress(KeyCode key) {
    keyHandler.handleKeyPress(key);
  }

  // Call everytime there's a handle key press
  public void checkForRules() {
    parser.interpretRules(grid);
  }

  public AbstractBlock[][] getGrid() {
    return this.grid;
  }

  private void tempInitializeGrid() {
    // Initialize grid with blocks
    String tempStringGrid[][] = {
      {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
      {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
      {"Empty", "Empty", "RockTextBlock", "Empty", "Empty", "Empty", "Empty", "Empty"},
      {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
      {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
      {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
      {"Empty", "Empty", "Empty", "Empty", "BabaTextBlock", "IsTextBlock", "YouTextBlock", "Empty"},
      {"BabaVisualBlock", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"}
    };

    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        this.grid[i][j] = factory.createBlock(tempStringGrid[i][j]);
      }
    }
  }
}
