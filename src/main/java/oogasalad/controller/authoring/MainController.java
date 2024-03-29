package oogasalad.controller.authoring;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * MainController is the entrypoint for the Authoring Environment.
 */
public class MainController extends Application {

  /**
   * Connects Model, Views and Controllers together.
   *
   * @param stage primary stage of the application
   */

  @Override
  public void start(Stage stage) throws Exception {
    // initialize models

    // initialize controllers
    ViewController viewController = new ViewController(stage);

    // initialize views
    viewController.initializeViews();
  }
}