package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.authoring.SceneController;

public class AuthoringEnvironment extends Application {

  @Override
  public void start(Stage stage) {
    // initialize application through scene controller
    SceneController sceneController = new SceneController(stage);
    sceneController.initializeViews();
  }
}
