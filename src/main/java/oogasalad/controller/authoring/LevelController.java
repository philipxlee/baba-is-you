package oogasalad.controller.authoring;

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

  }
}
