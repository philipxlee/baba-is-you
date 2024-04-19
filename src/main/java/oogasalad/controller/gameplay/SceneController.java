package oogasalad.controller.gameplay;

import javafx.stage.Stage;
import oogasalad.model.gameplay.level.Level;
import oogasalad.shared.scene.Scene;
import oogasalad.view.gameplay.mainscene.MainScene;
import oogasalad.view.gameplay.StartingScene;

/**
 * SceneController is Controller that manages Scenes and the JavaFX Stage.
 */
public class SceneController {

  private static final int WIDTH = 1500;
  private static final int HEIGHT = 800;
  private final Stage stage;
  private final DatabaseController databaseController;
  private boolean isGuestSession;
  private Level level;
  private LevelController levelController;
  private String language = "English";

  /**
   * ViewController constructor. Initialized with a JavaFX stage.
   *
   * @param stage primary stage of JavaFX application
   */
  public SceneController(Stage stage, DatabaseController databaseController, LevelController
      levelController) {
    this.stage = stage;
    this.databaseController = databaseController;
    this.isGuestSession = false;
    this.levelController = levelController;
    this.level = levelController.getLevel();
    stage.setTitle("Baba Is Us: Game Player");
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Initialize Main Scene.
   */
  public void initializeViews() {
    switchToScene(new StartingScene(this, databaseController, language));

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
    switchToScene(new MainScene(this, levelController));
  }

  /**
   * Check if the current session is a guest session.
   *
   * @return true if it's a guest session, false otherwise.
   */
  public boolean isGuestSession() {
    return isGuestSession;
  }

  /**
   * Get player data controller.
   */
  public DatabaseController getDatabaseController() {
    return databaseController;
  }

  /**
   * Return stage.
   */
  public Stage getStage() {
    return stage;
  }

  /**
   * Return level.
   */
  public Level getLevel() {
    return level;
  }

  /**
   * Return level controller.
   */
  public LevelController getLevelController() { return levelController; }

  public void setLanguage(String newLanguage) {
    this.language = newLanguage;
  }

  public String getLanguage() {
    return this.language;
  }
}