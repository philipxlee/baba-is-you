package oogasalad.controller.authoring;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import oogasalad.model.authoring.block.Block;
import oogasalad.model.authoring.level.Grid;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.shared.config.JsonManager;

/**
 * LevelController handles user input to modify the current level. Provides methods to interface
 * with backend model.
 */
public class LevelController {

  private final LevelParser levelParser;
  private final OpenAIClient openAIClient;
  private Level currentLevel;
  private String language;
  private final JsonParser jsonParser;
  private final JsonManager jsonManager = new JsonManager();

  /**
   * LevelController constructor.
   */
  public LevelController(LevelMetadata levelMetadata) {
    jsonParser = new JsonParser();
    openAIClient = new OpenAIClient();
    currentLevel = new Level(levelMetadata);
    levelParser = new LevelParser(currentLevel.getGrid());
  }

  /**
   * Set Cell of the level to specific block type (type must be in block type properties file)
   *
   * @param row       The row of the cell.
   * @param col       The column of the cell.
   * @param blockType The new block type.
   * @throws Exception Throws exception if block type is invalid (not in properties file).
   */
  public void addBlockToCell(int row, int col, String blockType) throws Exception {
    currentLevel.addBlockToCell(row, col, blockType);
  }

  /**
   * Remove last block from cell at given row and col position from level.
   *
   * @param row The row of the cell.
   * @param col The column of the cell.
   * @throws Exception Throws exception if row or col is invalid.
   */
  public void removeBlockFromCell(int row, int col) throws Exception {
    currentLevel.removeBlockFromCell(row, col);
  }

  /**
   * Serialize current level to JSON using Gson library. Parse according to configuration format.
   * Save to file directory.
   */
  public File serializeLevel() {
    JsonObject levelJson = levelParser.parseLevelToJSON(currentLevel);

    String fileName = currentLevel.getLevelMetadata().levelName() + ".json";
    try {
      return levelParser.saveJSON(levelJson, fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Loads the level configuration from a JSON file and updates the current level's grid. It parses
   * the JSON object to extract level details and grid configuration and updates the current level's
   * grid with the new data.
   *
   * @throws IOException If there is an error reading from the file.
   */
  public void loadLevel() throws IOException {
    JsonObject editJson = jsonManager.loadFromFile();
    this.currentLevel = jsonParser.parseLevel(editJson);
    this.currentLevel.getGrid().updateGrid(jsonParser.getJsonGrid(editJson));
  }

  /**
   * Use GPT to randomly generate a valid level configuration.
   */
  public void generateLevel() {
    openAIClient.fetchLevelConfiguration().thenAccept(json -> {
      System.out.println("Received level configuration: " + json);
    });
  }

  /**
   * Reset level to a new level configuration.
   *
   * @param levelMetadata The level metadata configuration to reset to.
   */
  public void resetLevel(LevelMetadata levelMetadata) {
    currentLevel = new Level(levelMetadata);
  }

  /**
   * Return the current level's metadata.
   *
   * @return Level Metadata of current level.
   */
  public LevelMetadata getLevelMetadata() {
    return currentLevel.getLevelMetadata();
  }

  /**
   * Set the language set for the authoring env.
   *
   * @param newLanguage new language to change the env to
   */
  public void setLanguage(String newLanguage) {
    this.language = newLanguage;
  }

  /**
   * Set the current level.
   *
   * @param level new level to change the current level to
   */
  public void setCurrentLevel(Level level) {
    this.currentLevel = level;
  }

  /**
   * Return current level.
   *
   * @return the current set level.
   */
  public Level getLevel() {
    return this.currentLevel;
  }

  /**
   * Get the language for the authoring env.
   *
   * @return the current set language.
   */
  public String getLanguage() {
    return this.language;
  }
}