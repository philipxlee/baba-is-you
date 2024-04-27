package oogasalad.view.authoring.jsonOps;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import oogasalad.model.authoring.block.Block;
import oogasalad.model.authoring.level.Level;
import oogasalad.shared.config.JsonManager;
import oogasalad.view.authoring.blockDisplay.BlockNameManager;


public class LevelValidator extends JsonManager{
  private final String fileName = "src/main/resources/blocktypes/blocktypes.json";
  public boolean validateLevel(Level level) throws IOException {
    List<Block> allBlocks = new ArrayList<>();
    JsonObject jsonObject = loadJsonFromFile(new File(fileName));
    Iterator<Stack<Block>> it = level.getGrid().iterator();
    BlockNameManager blockNameManager = new BlockNameManager();

    while (it.hasNext()) {
      Stack<Block> blockStack = it.next();
      allBlocks.addAll(blockStack);
    }

    int winBlockCount = 0;
    int youBlockCount = 0;
    int isBlockCount = 0;
    int visualBlockCount = 0;
    int textBlockCount = 0;

    for (Block block : allBlocks) {
      String blockType = block.type().name();
      JsonArray categories = getBlockCategories(jsonObject, blockType);

      if (blockType.equals("WinTextBlock")) {
        winBlockCount++;
        textBlockCount++;
      } else if (blockType.equals("YouTextBlock")) {
        youBlockCount++;
        textBlockCount++;
      } else if (blockType.equals("IsTextBlock")) {
        isBlockCount++;
        textBlockCount++;
      } else if (blockNameManager.isCategoryMatch(categories, "Visual")) {
        visualBlockCount++;
      } else if (blockNameManager.isCategoryMatch(categories, "Text")) {
        textBlockCount++;
      }
    }

    return allBlocks.size() >= 8 &&
        winBlockCount >= 1 &&
        youBlockCount >= 1 &&
        isBlockCount >= 2 &&
        visualBlockCount >= 2 &&
        textBlockCount >= 6;
  }

  private JsonArray getBlockCategories(JsonObject jsonObject, String blockType) {
    JsonObject blockObject = jsonObject.getAsJsonObject(blockType);
    return blockObject.getAsJsonArray("category");
  }
}