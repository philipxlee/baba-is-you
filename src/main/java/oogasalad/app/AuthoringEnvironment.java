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
//
//    // Get screen dimensions
//    Screen screen = Screen.getPrimary();
//    double screenWidth = screen.getBounds().getWidth();
//    double screenHeight = screen.getBounds().getHeight();
//
//    // Set stage size dynamically based on screen size
//    stage.setWidth(0.8 * screenWidth); // 80% of screen width
//    stage.setHeight(0.8 * screenHeight); // 80% of screen height
//
//    stage.centerOnScreen();
//    stage.show();
  }
}
