package oogasalad.controller.authoring;

import com.google.gson.JsonObject;
import java.io.FileWriter;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.shared.config.JsonManager;

/**
 * LevelController handles user input to modify the current level. Provides methods to interface
 * with backend model.
 */
public class LevelController {

  private final LevelParser levelParser;
  private Level currentLevel;

  /**
   * LevelController constructor.
   */
  public LevelController() {
    levelParser = new LevelParser(new JsonManager());
  }

  /**
   * Initialize level being tracked by LevelController.
   *
   * @param levelName The name of the level.
   * @param levelDesc The description of the level.
   * @param rows      Number of rows in level.
   * @param cols      Number of cols in level.
   */
  public void initializeLevel(String levelName, String levelDesc, int rows, int cols) {
    LevelMetadata levelMetadata = new LevelMetadata(levelName, levelDesc, rows, cols);
    currentLevel = new Level(levelMetadata);
  }

  /**
   * Set Cell of the level to specific block type (type must be in block type properties file)
   *
   * @param row       The row of the cell.
   * @param col       The column of the cell.
   * @param blockName The new block type.
   * @throws Exception Throws exception if block type is invalid (not in properties file).
   */
  public void setCell(int row, int col, String blockName) throws Exception {
    if (currentLevel == null) {
      throw new Exception("Level has to be initialized first!");
    }
    currentLevel.setCell(row, col, blockName);
  }

  /**
   * Serialize current level to JSON using Gson library. Parse according to configuration format.
   */
  public void serializeLevel() {
    JsonObject levelJson = levelParser.parseLevelToJSON(currentLevel);
    String fileName = currentLevel.getLevelMetadata().levelName() + ".json";
    try (FileWriter writer = new FileWriter(fileName)) {
      writer.write(levelJson.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}