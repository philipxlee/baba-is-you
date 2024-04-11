package oogasalad.model.gameplay.handlers;

import oogasalad.controller.gameplay.GameStateController;
import oogasalad.model.gameplay.grid.Grid;

public class RightKeyHandler extends KeyHandler {

  public RightKeyHandler(Grid grid, GameStateController gameStateController) {
    super(grid, gameStateController);
  }

  @Override
  public void execute() {
    handleKeyPress(0, 1);
  }
}
