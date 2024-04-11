package oogasalad.controller.authoring;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import oogasalad.model.authoring.block.BlockFactory;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;


/**
 * LevelController handles user input to modify the current level. Provides methods to interface
 * with backend model.
 */
public class LevelController {

  private final BlockFactory blockFactory;
  private Level currentLevel;

  /**
   * LevelController constructor. Initialized with BlockFactory.
   *
   * @param blockFactory The blockFactory used in the application.
   */
  public LevelController(BlockFactory blockFactory) {
    this.blockFactory = blockFactory;
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
    currentLevel = new Level(levelMetadata, blockFactory);
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
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    Map<String, Object> levelPropertiesMap = new HashMap<>();
    levelPropertiesMap.put("levelName", currentLevel.getLevelMetadata().levelName());
    Map<String, Integer> gridSize = new HashMap<>();
    gridSize.put("rows", currentLevel.getLevelMetadata().rows());
    gridSize.put("columns", currentLevel.getLevelMetadata().cols());
    levelPropertiesMap.put("gridSize", gridSize);

    String json = gson.toJson(levelPropertiesMap);
    try (FileWriter writer = new FileWriter("level.json")) {
      writer.write(json);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}