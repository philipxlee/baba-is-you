package oogasalad.app;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.PlayerDataController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.database.DataManager;
import oogasalad.database.DatabaseConfig;
import javafx.application.Platform;

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

    // Set up database
    DatabaseConfig databaseConfig = new DatabaseConfig();
    DataManager dataManager = new DataManager(databaseConfig.getDatabase());
    PlayerDataController playerDataController = new PlayerDataController(dataManager);

    // initialize controllers
    SceneController sceneController = new SceneController(stage, playerDataController);

    // initialize views
    sceneController.initializeViews();


    // Close database connection when the application is closed
    stage.setOnCloseRequest(event -> {
      databaseConfig.close();  // Ensure the database connection is closed
      Platform.exit();
    });

  }
}
