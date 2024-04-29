package oogasalad.view.authoring.blockDisplay;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.shared.config.JsonManager;

/**
 * Manages block names and data by loading and categorizing block information from JSON configuration files.
 * This class facilitates filtering and retrieving block data based on specified categories.
 */
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

  /**
   * Extracts block data from a JSON object, filtering by category. This method constructs a list of
   * BlockData instances, each representing a block that matches the specified category.
   *
   * @param jsonObject The JSON object containing blocks' data.
   * @param category The category to filter blocks by. If null, empty, or "All", blocks from all categories are included.
   * @return A list of BlockData instances for blocks that fit the specified category.
   */
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

  /**
   * Checks whether a block belongs to a specified category based on the categories listed in a JSON array.
   * This method supports filtering blocks by a specific category or including all if "All" is specified.
   *
   * @param categories A JSON array of categories from a block's data.
   * @param category The category to match against. If "All", matches any category.
   * @return true if the block's categories include the specified category or if "All" is specified, false otherwise.
   */
  public boolean isCategoryMatch(JsonArray categories, String category) {
    if (category == null || category.isEmpty() || category.equals("All")) {
      return categories.contains(new JsonPrimitive("All"));
    } else {
      return categories.contains(new JsonPrimitive(category));
    }
  }
}
