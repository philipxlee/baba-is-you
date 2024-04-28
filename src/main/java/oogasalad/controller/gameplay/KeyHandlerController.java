package oogasalad.controller.gameplay;

import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.handlers.*;

public class KeyHandlerController {

  private KeyHandler keyHandler;
  private final GameStateController gameStateController;

  private Grid grid;


  private EnemyKeyHandler enemyKeyHandler;

  public KeyHandlerController(GameStateController gameStateController) {
    this.gameStateController = gameStateController;
  }

  public boolean executeKey(Grid grid, KeyCode code) {
    this.grid = grid;
    keyHandler = switch (code) {
      case UP -> new UpKeyHandler(grid, gameStateController);
      case DOWN -> new DownKeyHandler(grid, gameStateController);
      case LEFT -> new LeftKeyHandler(grid, gameStateController);
      case RIGHT -> new RightKeyHandler(grid, gameStateController);
      case E -> new EnemyKeyHandler(grid, gameStateController);
      default -> null;
    };

    if (keyHandler != null) {
      keyHandler.execute();
      return true;
    }
    return false;
  }

  public void moveEnemy(){
    enemyKeyHandler = new EnemyKeyHandler(this.grid, this.gameStateController);
    enemyKeyHandler.moveEnemy();
  }
  //EMPHASIZE why i did it this way on my analysis.

}

