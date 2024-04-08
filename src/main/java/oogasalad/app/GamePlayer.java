package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.SceneController;

/**
 * MainController is the entrypoint for the Game Player.
 */
public class GamePlayer extends Application {

  /**
   * Connects Model, Views and Controllers together.
   *
   * @param stage primary stage of the application
   */


  @Override
  public void start(Stage stage) throws Exception {
    // initialize models

    // initialize controllers
    SceneController sceneController = new SceneController(stage);

    // initialize views
    sceneController.initializeViews();
  }
}
