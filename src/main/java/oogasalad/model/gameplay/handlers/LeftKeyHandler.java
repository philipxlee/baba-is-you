package oogasalad.model.gameplay.handlers;

import oogasalad.controller.gameplay.GameStateController;
import oogasalad.model.gameplay.grid.Grid;

public class LeftKeyHandler extends KeyHandler {


  public LeftKeyHandler(Grid grid, GameStateController gameStateController) {
    super(grid, gameStateController);
  }

  @Override
  public void execute() {
    handleKeyPress(0, -1);
  }
}
