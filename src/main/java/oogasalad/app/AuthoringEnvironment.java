package oogasalad.app;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import oogasalad.controller.authoring.SceneController;

public class AuthoringEnvironment extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Get the primary screen
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();

    // Set the dimensions of the stage based on the screen dimensions
    double screenWidth = bounds.getWidth();
    double screenHeight = bounds.getHeight();

    // You can adjust these values based on your requirements
    double stageWidth = screenWidth * 0.8; // 80% of screen width
    double stageHeight = screenHeight * 0.8; // 80% of screen height

    // Set the dimensions of the stage
    primaryStage.setWidth(stageWidth);
    primaryStage.setHeight(stageHeight);

    // Initialize controllers
    SceneController sceneController = new SceneController(primaryStage);

    // Initialize views
    sceneController.initializeViews();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
