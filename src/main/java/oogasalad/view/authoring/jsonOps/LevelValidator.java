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

/**
 * Validates the composition of a game level to ensure it meets specific criteria necessary for the
 * game to function correctly. This involves checking that the level contains the required types and
 * quantities of blocks as defined by the game rules.
 */
public class LevelValidator extends JsonManager {

  private final String fileName = "src/main/resources/blocktypes/blocktypes.json";

  /**
   * Validates the given level by ensuring it contains a sufficient number and variety of blocks as
   * specified by game rules.
   *
   * @param level The level to validate.
   * @return true if the level contains at least 8 blocks, including a minimum number of specific
   * block types crucial for gameplay (Win, You, Is, Visual, and Text blocks).
   * @throws IOException If there is an error reading from the block types configuration file.
   */
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

  /**
   * Retrieves the categories of a specific block type from a JSON object that contains
   * configurations of various block types.
   *
   * @param jsonObject The JSON object containing block type configurations.
   * @param blockType  The block type for which categories are to be retrieved.
   * @return A JsonArray containing categories of the specified block type.
   */
  private JsonArray getBlockCategories(JsonObject jsonObject, String blockType) {
    JsonObject blockObject = jsonObject.getAsJsonObject(blockType);
    return blockObject.getAsJsonArray("category");
  }
}