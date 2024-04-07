package oogasalad.controller.gameplay;

import oogasalad.view.gameplay.gamestates.LoseScene;
import oogasalad.view.gameplay.gamestates.WinScene;

public class GameOverController {

  private final SceneController sceneController;

  public GameOverController(SceneController sceneController) {
    this.sceneController = sceneController;
  }

  public void displayGameOver(boolean isWin) {
    if (isWin) {
      sceneController.switchToScene(new WinScene());
    } else {
      sceneController.switchToScene(new LoseScene());
    }
  }
}
