package oogasalad.controller.gameplay;

import javafx.stage.Stage;
import oogasalad.model.gameplay.level.Level;
import oogasalad.shared.scene.Scene;
import oogasalad.view.gameplay.MainScene;
import oogasalad.view.gameplay.StartingScene;

/**
 * SceneController is Controller that manages Scenes and the JavaFX Stage.
 */
public class SceneController {

  private static final int WIDTH = 1500;
  private static final int HEIGHT = 800;
  private final Stage stage;
  private final PlayerDataController playerDataController;
  private boolean isGuestSession;
  private Level level;

  /**
   * ViewController constructor. Initialized with a JavaFX stage.
   *
   * @param stage primary stage of JavaFX application
   */
  public SceneController(Stage stage, PlayerDataController playerDataController, Level level) {
    this.stage = stage;
    this.playerDataController = playerDataController;
    this.isGuestSession = false;
    this.level = level;
    stage.setTitle("Baba is Us: Game Player");
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Initialize Main Scene.
   */
  public void initializeViews() {
    switchToScene(new StartingScene(this, playerDataController));
  }

  /**
   * Switch to new Scene.
   *
   * @param scene is new Scene to switch to.
   */
  public void switchToScene(Scene scene) {
    scene.initializeScene(WIDTH, HEIGHT);
    stage.setScene(scene.getScene());
  }

  /**
   * Begin the game.
   */
  public void beginGame(boolean isGuest) {
    this.isGuestSession = isGuest;
    switchToScene(new MainScene(this, level));
  }

  /**
   * Check if the current session is a guest session.
   * @return true if it's a guest session, false otherwise.
   */
  public boolean isGuestSession() {
    return isGuestSession;
  }

  /**
   * Get player data controller.
   */
  public PlayerDataController getPlayerDataController() {
    return playerDataController;
  }

  public Stage getStage() {
    return stage;
  }
}