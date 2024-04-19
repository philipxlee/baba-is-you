package oogasalad.controller.gameplay;

import com.google.gson.JsonObject;
import java.io.IOException;
import oogasalad.database.DataManager;
import oogasalad.database.DatabaseConfig;
import oogasalad.model.gameplay.level.JsonGameParser;
import oogasalad.model.gameplay.level.Level;
import oogasalad.shared.config.JsonManager;

/**
 * LevelController responds to view and allows for model to parse Json files and then load them
 * or to save game data to json files.
 */
public class LevelController {

  private Level level;
  private final JsonGameParser jsonGameParser = new JsonGameParser();
  private final JsonManager jsonManager = new JsonManager();
  private SceneController sceneController;

  /**
   * Constructs a new LevelController for managing a specific level.
   *
   * @param level The level to manage.
   */
  public LevelController(Level level) {
    this.level = level;
  }

  /**
   * Retrieves the current level managed by this controller.
   *
   * @return The current level.
   */
  public Level getLevel() {
    return level;
  }

  /**
   * Returns current level name
   */
  public String getLevelName() {
    return level.getLevelMetadata().levelName();
  }

  /**
   * Loads a new level from a file, initializes related controllers and updates the scene.
   * This method orchestrates the loading of a new level configuration, creating necessary data
   * management and scene setup to transition to the new level.
   *
   * @param sceneController The current scene controller to be updated with new level data.
   * @throws IOException If there is an error reading the level data from the file.
   */
  public void loadNewLevel(SceneController sceneController) throws IOException {
    Level newLevel = jsonGameParser.parseLevel(jsonManager.loadFromFile());
    LevelController levelController = new LevelController(newLevel);
    DatabaseConfig databaseConfig = new DatabaseConfig();
    DatabaseController databaseController = new DatabaseController(databaseConfig.getDatabase(), levelController);
    this.sceneController = new SceneController(sceneController.getStage(), databaseController,
        levelController);
    this.sceneController.initializeViews();
  }
}
