package oogasalad.model.gameplay.handlers;

import oogasalad.controller.gameplay.GameStateController;
import oogasalad.model.gameplay.grid.Grid;

public class DownKeyHandler extends KeyHandler {

  private static final int DELTA_I = 1;
  private static final int DELTA_J = 0;

  /**
   * Represents a key handler for handling the DOWN arrow key press. This class extends the
   * KeyHandler class and implements the execute method to move the player downwards.
   */
  public DownKeyHandler(Grid grid, GameStateController gameStateController) {
    super(grid, gameStateController);
  }

  /**
   * Executes the action corresponding to the DOWN arrow key press. This method is called when the
   * DOWN arrow key is pressed, and it moves the player downwards.
   */
  @Override
  public void execute() {
    handleKeyPress(DELTA_I, DELTA_J);
  }
}
