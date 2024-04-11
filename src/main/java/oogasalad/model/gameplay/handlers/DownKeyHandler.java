package oogasalad.model.gameplay.handlers;

import oogasalad.controller.gameplay.GameStateController;
import oogasalad.model.gameplay.grid.Grid;

public class DownKeyHandler extends KeyHandler {

  public DownKeyHandler(Grid grid, GameStateController gameStateController) {
    super(grid, gameStateController);
  }

  @Override
  public void execute() {
    handleKeyPress(1, 0);
  }
}
