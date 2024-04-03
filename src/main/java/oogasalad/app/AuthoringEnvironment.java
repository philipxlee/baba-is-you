package oogasalad.app;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import oogasalad.controller.authoring.LevelController;
import oogasalad.controller.authoring.SceneController;
import oogasalad.model.authoring.block.BlockTypeManager;

public class AuthoringEnvironment extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    // initialize models
    BlockTypeManager blockTypeManager = new BlockTypeManager("/blocktypes/blocktypes.properties");

    // initialize controllers
    LevelController levelController = new LevelController(blockTypeManager);
    SceneController sceneController = new SceneController(stage);

    // Initialize views
    sceneController.initializeViews();
  }
}
