package oogasalad.view.authoring.blockDisplay;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.shared.config.JsonManager;
import oogasalad.view.authoring.blockDisplay.BlockData;

public class BlockNameManager extends JsonManager {

  /**
   * Load all blocks' data from a JSON file containing block names, image paths, and categories.
   *
   * @param fileName The name of the JSON file containing block data.
   * @param category Optional category filter; if null or empty, loads all blocks.
   * @return A list of BlockData objects extracted from the JSON file.
   * @throws IOException If an I/O error occurs.
   */
  public List<BlockData> loadBlocksFromJsonFile(String fileName, String category)
      throws IOException {
    JsonObject jsonObject = loadJsonFromFile(new File(fileName));
    return extractBlocks(jsonObject, category);
  }

  public List<BlockData> extractBlocks(JsonObject jsonObject, String category) {
    List<BlockData> blocks = new ArrayList<>();
    if (jsonObject != null) {
      for (String key : jsonObject.keySet()) {
        JsonObject blockObject = jsonObject.getAsJsonObject(key);
        String imagePath = blockObject.has("imagePath") ? blockObject.get("imagePath").getAsString() : null;
        JsonArray categories = blockObject.getAsJsonArray("category");

        if (isCategoryMatch(categories, category)) {
          blocks.add(new BlockData(key, imagePath, category));
        }
      }
    }
    return blocks;
  }

  // Method to check if a block belongs to a specified category
  public boolean isCategoryMatch(JsonArray categories, String category) {
    if (category == null || category.isEmpty() || category.equals("All")) {
      return categories.contains(new JsonPrimitive("All"));
    } else {
      return categories.contains(new JsonPrimitive(category));
    }
  }
}
