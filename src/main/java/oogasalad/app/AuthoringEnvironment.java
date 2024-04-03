package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.authoring.LevelController;
import oogasalad.controller.authoring.SceneController;
import oogasalad.model.authoring.block.BlockTypeManager;

/**
 * MainController is the entrypoint for the Authoring Environment.
 */
public class AuthoringEnvironment extends Application {

  /**
   * Connects Model, Views and Controllers together.
   *
   * @param stage primary stage of the application
   */

  @Override
  public void start(Stage stage) throws Exception {
    // initialize models
    BlockTypeManager blockTypeManager = new BlockTypeManager("/blocktypes/blocktypes.properties");

    // initialize controllers
    LevelController levelController = new LevelController(blockTypeManager);
    SceneController sceneController = new SceneController(stage);

    // initialize views
    sceneController.initializeViews();
  }
}