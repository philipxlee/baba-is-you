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

  public void executeKey(Grid grid, KeyCode code) {
    keyHandler = switch (code) {
      case UP -> new UpKeyHandler(grid, gameStateController);
      case DOWN -> new DownKeyHandler(grid, gameStateController);
      case LEFT -> new LeftKeyHandler(grid, gameStateController);
      case RIGHT -> new RightKeyHandler(grid, gameStateController);
      default -> null;
    };

    if (keyHandler != null) {
      keyHandler.execute();
    }
  }
}

