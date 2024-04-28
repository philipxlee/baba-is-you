package oogasalad.controller.gameplay;

import oogasalad.view.gameplay.gamestates.LoseScene;
import oogasalad.view.gameplay.gamestates.WinScene;

/**
 * Controller that manages the game over screen.
 *
 * @author Philip Lee.
 */
public class GameStateController {

  private final SceneController sceneController;
  private GameGridController gameGridController;


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
    System.out.println("calling displayGameOver");
    gameGridController.setGameOverStatus(true);
    if (isWin) {
      sceneController.switchToScene(new WinScene(sceneController));
    } else {
      sceneController.switchToScene(new LoseScene(sceneController));
    }
  }

  /**
   * Restarts the game.
   */
  public void restartGame() {
    sceneController.beginGame(sceneController.isGuestSession());
    gameGridController.setGameOverStatus(false);
  }

  public void setGameGridController(GameGridController gameGridController) {
    this.gameGridController = gameGridController;
  }
}
