package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.entrypoint.EntryPointController;

/**
 * The main class for the application. This is the entry point for the application.
 *
 * @author Philip Lee, Divyansh Jain, Nikita Daga, Yasha Doddabele, Arnav Nayak, Joseph Ogunbadewa,
 * Jonathan Esponada.
 */
public class Main extends Application {

  /**
   * The main method for the application. This is the entry point for the application.
   *
   * @param primaryStage the primary stage for this application, onto which the application scene
   *                     can be set.
   * @throws Exception if an error occurs
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    EntryPointController entryPointController = new EntryPointController(primaryStage);
    entryPointController.initializeViews();
    primaryStage.show();
  }
}
