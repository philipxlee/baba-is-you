package oogasalad.shared.blockview;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlockViewFactory {

  private final Map<String, Image> imageCache;

  public BlockViewFactory(String imagesFilePath) throws Exception {
    imageCache = new HashMap<>();
    loadImages(imagesFilePath);
  }

  private void loadImages(String imagesFilePath) throws Exception {
    try (InputStream input = getClass().getResourceAsStream(imagesFilePath)) {
      if (input == null) {
        throw new IllegalArgumentException("Image Properties file not found: " + imagesFilePath);
      }

      // Define the type of the data we want to read
      Type type = new TypeToken<Map<String, JsonObject>>() {
      }.getType();

      // Create a Gson instance and parse the JSON
      Gson gson = new Gson();
      Map<String, JsonObject> blocksMap = gson.fromJson(new InputStreamReader(input), type);

      // Iterate over the map and populate imageCache and validBlockTypes
      blocksMap.forEach((name, blockTypeJson) -> {
        String imagePath = blockTypeJson.get("imagePath").getAsString();
        Image image = new Image(imagePath);
        imageCache.put(name, image);
      });
    } catch (IOException e) {
      throw new Exception("Failed to load images from properties file", e);
    }
  }

  public ImageView createBlockView(String blockType) throws Exception {
    if (!imageCache.containsKey(blockType)) {
      throw new Exception("Invalid block type: " + blockType);
    }
    Image blockImage = imageCache.get(blockType);
    if (blockImage == null) {
      throw new Exception("Image for block type not found, but type is valid: " + blockType);
    }
    return new ImageView(blockImage);
  }
}
