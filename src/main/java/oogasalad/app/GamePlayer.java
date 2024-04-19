package oogasalad.app;

import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.DatabaseController;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.database.DatabaseConfig;
import javafx.application.Platform;
import oogasalad.model.gameplay.level.JsonGameParser;
import oogasalad.model.gameplay.level.Level;
import oogasalad.shared.config.JsonManager;

/**
 * MainController is the entrypoint for the Game Player.
 */
public class GamePlayer {
  private JsonManager jsonManager = new JsonManager();
  private JsonGameParser jsonGameParser = new JsonGameParser();
  private final File defaultJson = new File("data/defaultJson.json");
  private SceneController sceneController;
  private String language = "English";

  /**
   * Connects Model, Views and Controllers together.
   *
   * @param stage primary stage of the application
   */

  public void start(Stage stage) throws Exception {
    Level defaultLevel = jsonGameParser.parseLevel(jsonManager.loadJsonFromFile(defaultJson));
    LevelController levelController = new LevelController(defaultLevel);

    // Set up database
    DatabaseConfig databaseConfig = new DatabaseConfig();
    DatabaseController databaseDataController = new DatabaseController(databaseConfig.getDatabase(), levelController);

    // initialize controllers
    sceneController = new SceneController(stage, databaseDataController,
        levelController);
    sceneController.setLanguage(language);

    // initialize views
    sceneController.initializeViews();


    // Close database connection when the application is closed
    stage.setOnCloseRequest(event -> {
      databaseConfig.close();  // Ensure the database connection is closed
      Platform.exit();
    });

  }

  public void setLanguage(String newLanguage) {
    this.language = newLanguage;
  }
}
