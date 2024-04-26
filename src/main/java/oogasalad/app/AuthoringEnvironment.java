package oogasalad.app;

import javafx.stage.Stage;
import oogasalad.controller.authoring.LevelController;
import oogasalad.controller.authoring.SceneController;
import oogasalad.model.authoring.level.LevelMetadata;

public class AuthoringEnvironment {

  private String language = "English";

  public void start(Stage stage) {
    // initialize models
    LevelMetadata levelMetadata = new LevelMetadata("Level Name", "Level Desc.", 10, 10);

    // initialize controllers
    LevelController levelController = new LevelController(levelMetadata);
    SceneController sceneController = new SceneController(stage, levelController);
    sceneController.setLanguage(language);

    // initialize views
    sceneController.initializeViews();
  }

  public void setLanguage(String newLanguage) {
    this.language = newLanguage;
  }
}