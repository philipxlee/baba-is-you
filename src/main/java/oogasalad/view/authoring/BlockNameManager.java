package oogasalad.view.authoring;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.shared.config.JsonManager;

public class BlockNameManager extends JsonManager {

  /**
   * Load block names from a JSON file containing block names and image paths.
   *
   * @param fileName The name of the JSON file containing block names and image paths.
   * @return A list of block names extracted from the JSON file.
   * @throws IOException If an I/O error occurs.
   */
  public List<String> loadBlockNamesFromJsonFile(String fileName) throws IOException {
    JsonObject jsonObject = loadJsonFromFile(new File(fileName));
    return extractBlockNames(jsonObject);
  }

  /**
   * Extract block names from a JsonObject containing block names and image paths.
   *
   * @param jsonObject The JsonObject containing block names and image paths.
   * @return A list of block names extracted from the JsonObject.
   */
  private List<String> extractBlockNames(JsonObject jsonObject) {
    List<String> blockNames = new ArrayList<>();
    if (jsonObject != null) {
      for (String blockName : jsonObject.keySet()) {
        blockNames.add(blockName);
      }
    }
    return blockNames;
  }

  /**
   * Load image paths from a JSON file containing block names and image paths.
   *
   * @param fileName The name of the JSON file containing block names and image paths.
   * @return A list of image paths extracted from the JSON file.
   * @throws IOException If an I/O error occurs.
   */
  public List<String> loadImagePathsFromJsonFile(String fileName) throws IOException {
    JsonObject jsonObject = loadJsonFromFile(new File(fileName));
    return extractImagePaths(jsonObject);
  }

  /**
   * Extract image paths from a JsonObject containing block names and image paths.
   *
   * @param jsonObject The JsonObject containing block names and image paths.
   * @return A list of image paths extracted from the JsonObject.
   */
  private List<String> extractImagePaths(JsonObject jsonObject) {
    List<String> imagePaths = new ArrayList<>();
    if (jsonObject != null) {
      for (String blockName : jsonObject.keySet()) {
        JsonElement blockElement = jsonObject.get(blockName);
        if (blockElement != null && blockElement.isJsonObject()) {
          JsonObject blockObject = blockElement.getAsJsonObject();
          JsonElement imagePathElement = blockObject.get("imagePath");
          if (imagePathElement != null && imagePathElement.isJsonPrimitive()) {
            imagePaths.add(imagePathElement.getAsString());
          }
        }
      }
    }
    return imagePaths;
  }

}
