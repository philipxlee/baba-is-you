package oogasalad.controller.authoring;

import com.google.gson.JsonObject;
import java.io.IOException;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;

/**
 * LevelController handles user input to modify the current level. Provides methods to interface
 * with backend model.
 */
public class LevelController {

  private final LevelParser levelParser;
  private final Level currentLevel;

  /**
   * LevelController constructor.
   */
  public LevelController(Level level) {
    levelParser = new LevelParser();
    currentLevel = level;
  }

  /**
   * Set Cell of the level to specific block type (type must be in block type properties file)
   *
   * @param row       The row of the cell.
   * @param col       The column of the cell.
   * @param blockType The new block type.
   * @throws Exception Throws exception if block type is invalid (not in properties file).
   */
  public void setCell(int row, int col, String blockType) throws Exception {
    currentLevel.setCell(row, col, blockType);
  }

  /**
   * Serialize current level to JSON using Gson library. Parse according to configuration format.
   * Save to file directory.
   */
  public void serializeLevel() {
    JsonObject levelJson = levelParser.parseLevelToJSON(currentLevel);
    String fileName = currentLevel.getLevelMetadata().levelName() + ".json";
    try {
      levelParser.saveJSON(levelJson, fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Return the current level's metadata.
   *
   * @return Level Metadata of current level.
   */
  public LevelMetadata getLevelMetadata() {
    return currentLevel.getLevelMetadata();
  }

}