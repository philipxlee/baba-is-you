package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.entrypoint.EntryPointController;
import oogasalad.shared.entrypoint.EntryPoint;

public class Entrance extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    EntryPointController entryPointController = new EntryPointController(primaryStage);
    entryPointController.initializeViews();
  }
}
