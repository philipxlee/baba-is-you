package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.authoring.ViewController;

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

    // initialize controllers
    ViewController viewController = new ViewController(stage);

    // initialize views
    viewController.initializeViews();
  }
}