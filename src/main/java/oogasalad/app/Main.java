package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.entrypoint.EntryPointController;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    EntryPointController entryPointController = new EntryPointController(primaryStage);
    entryPointController.initializeViews();
    primaryStage.show();
  }
}
