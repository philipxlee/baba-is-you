package oogasalad.controller.gameplay;

import oogasalad.view.gameplay.gamestates.LoseScene;
import oogasalad.view.gameplay.gamestates.WinScene;

/**
 * Controller that manages the game over screen.
 */
public class GameStateController {

  private final SceneController sceneController;

  /**
   * Constructor for GameOverController.
   *
   * @param sceneController SceneController that manages the scenes.
   */
  public GameStateController(SceneController sceneController) {
    this.sceneController = sceneController;
  }

  /**
   * Display the game over screen.
   *
   * @param isWin boolean representing if the player won the game.
   */
  public void displayGameOver(boolean isWin) {
    if (isWin) {
      sceneController.switchToScene(new WinScene(sceneController));
    } else {
      sceneController.switchToScene(new LoseScene(sceneController));
    }
  }
}
