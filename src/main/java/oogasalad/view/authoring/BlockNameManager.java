package oogasalad.view.authoring;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.shared.config.JsonManager;

public class BlockNameManager extends JsonManager {

  /**
   * Load all blocks' data from a JSON file containing block names, image paths, and categories.
   *
   * @param fileName The name of the JSON file containing block data.
   * @param category Optional category filter; if null or empty, loads all blocks.
   * @return A list of BlockData objects extracted from the JSON file.
   * @throws IOException If an I/O error occurs.
   */
  public List<BlockData> loadBlocksFromJsonFile(String fileName, String category) throws IOException {
    JsonObject jsonObject = loadJsonFromFile(new File(fileName));
    return extractBlocks(jsonObject, category);
  }

  /**
   * Extract blocks data from a JsonObject, optionally filtering by category.
   *
   * @param jsonObject The JsonObject containing blocks' data.
   * @param category Optional category to filter blocks by.
   * @return A list of BlockData objects.
   */
  private List<BlockData> extractBlocks(JsonObject jsonObject, String category) {
    List<BlockData> blocks = new ArrayList<>();
    if (jsonObject != null) {
      for (String key : jsonObject.keySet()) {
        JsonObject blockObject = jsonObject.getAsJsonObject(key);
        String blockName = key;
        String imagePath = blockObject.has("imagePath") ? blockObject.get("imagePath").getAsString() : null;
        JsonArray categories = blockObject.getAsJsonArray("category");  // Extract the categories array
        String blockCategory = (categories != null && categories.size() > 0) ? categories.get(0).getAsString() : "Unknown";

        // Check if the category matches or load all if no specific category is requested
        if (category == null || category.isEmpty() || blockCategory.equals(category)) {
          blocks.add(new BlockData(blockName, imagePath, blockCategory));
        }
      }
    }
    return blocks;
  }

}
