package oogasalad.controller.authoring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.authoring.block.BlockTypeManager;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;

public class LevelController {

  private List<Level> levels;

  public LevelController() {
    this.levels = new ArrayList<>();
  }

  public Level createLevel(String name, String description, String author, int width, int height,
      String blockPropertiesFilePath) throws IOException {
    LevelMetadata metadata = new LevelMetadata(name, description, width, height);
    BlockTypeManager blockTypeManager = new BlockTypeManager(blockPropertiesFilePath);
    Level level = new Level(metadata, blockTypeManager);
    levels.add(level);
    return level;
  }

  public Level loadLevel(String levelId) {

    return null; // Placeholder for now, actual implementation pending
  }

  public void saveLevel(String levelId) {

  }

  public List<Level> listLevels() {
    return levels;
  }
}
