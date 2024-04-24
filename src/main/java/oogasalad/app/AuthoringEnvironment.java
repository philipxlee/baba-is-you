package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.authoring.LevelController;
import oogasalad.controller.authoring.SceneController;
import oogasalad.model.authoring.level.LevelMetadata;

public class AuthoringEnvironment {

  public void start(Stage stage) {
    // initialize models
    LevelMetadata levelMetadata = new LevelMetadata("Level Name", "Level Desc.",
        10, 10, "Medium", "BabaIsUs");

    // initialize controllers
    LevelController levelController = new LevelController(levelMetadata);
    SceneController sceneController = new SceneController(stage, levelController);

    // initialize views
    sceneController.initializeViews();
  }
}