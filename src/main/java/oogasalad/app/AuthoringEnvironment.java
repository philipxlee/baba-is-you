package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.authoring.LevelController;
import oogasalad.controller.authoring.SceneController;
import oogasalad.model.authoring.block.BlockFactory;

public class AuthoringEnvironment extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    // initialize models
    BlockFactory blockFactory = new BlockFactory("/blocktypes/blocktypes.properties");

    // initialize controllers
    LevelController levelController = new LevelController(blockFactory);
    SceneController sceneController = new SceneController(stage);

    // Initialize views
    sceneController.initializeViews();
  }
}
