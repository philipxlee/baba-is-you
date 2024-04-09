package oogasalad.model.gameplay.handlers;

import oogasalad.controller.gameplay.GameOverController;
import oogasalad.model.gameplay.grid.Grid;

public class DownKeyHandler extends KeyHandler {

  public DownKeyHandler(Grid grid, GameOverController gameOverController) {
    super(grid, gameOverController);
  }

  @Override
  public void execute() {
    handleKeyPress(1, 0);
  }
}
