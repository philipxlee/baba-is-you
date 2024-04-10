package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import oogasalad.controller.authoring.LevelController;
import oogasalad.controller.authoring.SceneController;
import oogasalad.model.authoring.block.BlockFactory;
import oogasalad.shared.blockview.BlockViewFactory;

public class AuthoringEnvironment extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    // initialize models
    BlockFactory blockFactory = new BlockFactory("/blocktypes/blocktypes.json");
    BlockViewFactory blockViewFactory = new BlockViewFactory("/blocktypes/blocktypes.json");

    // initialize controllers
    LevelController levelController = new LevelController(blockFactory);
    SceneController sceneController = new SceneController(stage);

    // Initialize views
    sceneController.initializeViews();
  }
}
