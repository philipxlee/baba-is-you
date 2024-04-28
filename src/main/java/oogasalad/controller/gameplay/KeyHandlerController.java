package oogasalad.controller.gameplay;

import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.handlers.DownKeyHandler;
import oogasalad.model.gameplay.handlers.KeyHandler;
import oogasalad.model.gameplay.handlers.LeftKeyHandler;
import oogasalad.model.gameplay.handlers.RightKeyHandler;
import oogasalad.model.gameplay.handlers.UpKeyHandler;

public class KeyHandlerController {

  private KeyHandler keyHandler;
  private final GameStateController gameStateController;

  public KeyHandlerController(GameStateController gameStateController) {
    this.gameStateController = gameStateController;
  }

  public boolean executeKey(Grid grid, KeyCode code) {
    //Cheat keys: implement further ones here
    switch (code) {
      case W -> gameStateController.displayGameOver(true);
      case L -> gameStateController.displayGameOver(false);
      case R -> gameStateController.restartGame();
      default -> {}
    }
    keyHandler = switch (code) {
      case UP -> new UpKeyHandler(grid, gameStateController);
      case DOWN -> new DownKeyHandler(grid, gameStateController);
      case LEFT -> new LeftKeyHandler(grid, gameStateController);
      case RIGHT -> new RightKeyHandler(grid, gameStateController);
      default -> null;
    };

    if (keyHandler != null) {
      keyHandler.execute();
      return true;
    }
    return false;
  }
}

