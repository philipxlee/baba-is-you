package oogasalad.controller.authoring;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import oogasalad.model.authoring.block.BlockTypeManager;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;

public class LevelController {

  private Level currentLevel;

  public LevelController(BlockTypeManager blockTypeManager) {
    LevelMetadata levelMetadata = new LevelMetadata("", "", 7, 7);
    currentLevel = new Level(levelMetadata, blockTypeManager);
  }

  public void setCell(int row, int col, String blockName) throws Exception {
    currentLevel.setCell(row, col, blockName);
  }

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
