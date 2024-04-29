package oogasalad.app;

import javafx.stage.Stage;
import oogasalad.controller.authoring.LevelController;
import oogasalad.controller.authoring.SceneController;
import oogasalad.model.authoring.level.LevelMetadata;

/**
 * The app for the Authoring Environment.
 */
public class AuthoringEnvironment {

  private String language = "";

  public void start(Stage stage) {
    // initialize models
    LevelMetadata levelMetadata = new LevelMetadata("Level Name", "Level Desc.",
        10, 10, "Medium", "BabaIsUs", "Try harder");

    // initialize controllers
    LevelController levelController = new LevelController(levelMetadata);
    SceneController sceneController = new SceneController(stage, levelController);
    sceneController.setLanguage(language);

    // initialize views
    sceneController.initializeViews();
  }

  /**
   * Set the language of the authoring environment.
   * @param newLanguage new language to set to.
   */
  public void setLanguage(String newLanguage) {
    this.language = newLanguage;
  }
}
