package oogasalad.controller.authoring;

import javafx.stage.Stage;
import oogasalad.controller.entrypoint.EntryPointController;
import oogasalad.shared.entrypoint.EntryPoint;
import oogasalad.shared.scene.Scene;
import oogasalad.view.authoring.MainScene;

/**
 * SceneController is Controller that manages Scenes and the JavaFX Stage.
 */
public class SceneController {

  private static final int WIDTH = 1500;
  private static final int HEIGHT = 800;
  private final Stage stage;
  private final LevelController levelController;
  private String language;

  /**
   * ViewController constructor. Initialized with a JavaFX stage and LevelController.
   *
   * @param stage           The primary stage of JavaFX application
   * @param levelController The level controller being used in the application.
   */
  public SceneController(Stage stage, LevelController levelController) {
    this.stage = stage;
    this.language = "English";
    this.levelController = levelController;
    stage.setTitle("Baba is Us: Authoring Environment");
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Initialize Main Scene.
   */
  public void initializeViews() {
    switchToScene(new MainScene(levelController, this));
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
   * Switch to entrypoint.
   */
  public void goToEntryPoint() {
    EntryPointController entryPointController = new EntryPointController(stage);
    entryPointController.setLanguage(this.language);
    switchToScene(new EntryPoint(entryPointController));
  }

  /**
   * Set the language of the game.
   *
   * @param newLanguage the language to change to
   */
  public void setLanguage(String newLanguage) {
    this.language = newLanguage;
    this.levelController.setLanguage(newLanguage);
  }

  /**
   * Get the language of the game.
   *
   * @return the current configured language
   */
  public String getLanguage() {
    return this.language;
  }
}