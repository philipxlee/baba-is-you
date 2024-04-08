package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import oogasalad.controller.authoring.LevelController;
import oogasalad.controller.authoring.SceneController;
import oogasalad.model.authoring.block.BlockTypeManager;

public class AuthoringEnvironment extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    // initialize models
    BlockTypeManager blockTypeManager = new BlockTypeManager("/blocktypes/blocktypes.properties");

    // initialize controllers
    LevelController levelController = new LevelController(blockTypeManager);
    SceneController sceneController = new SceneController(stage);

    // Initialize views
    sceneController.initializeViews();

    // Get screen dimensions
    Screen screen = Screen.getPrimary();
    double screenWidth = screen.getBounds().getWidth();
    double screenHeight = screen.getBounds().getHeight();

    // Set stage size dynamically based on screen size
    stage.setWidth(0.8 * screenWidth); // 80% of screen width
    stage.setHeight(0.8 * screenHeight); // 80% of screen height

    stage.centerOnScreen();
    stage.show();
  }
}
