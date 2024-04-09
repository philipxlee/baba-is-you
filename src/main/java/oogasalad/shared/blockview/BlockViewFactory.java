package oogasalad.shared.blockview;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlockViewFactory {

  private final Map<String, Image> imageCache;
  private final Set<String> validBlockTypes;

  public BlockViewFactory(String imagesFilePath) throws Exception {
    imageCache = new HashMap<>();
    validBlockTypes = new HashSet<>();
    loadImages(imagesFilePath);
  }

  private void loadImages(String imagesFilePath) throws Exception {
    try (InputStream input = getClass().getResourceAsStream(imagesFilePath)) {
      if (input == null) {
        throw new IllegalArgumentException("Image Properties file not found: " + imagesFilePath);
      }
      Properties properties = new Properties();
      properties.load(input);
      // validBlockTypes
    } catch (IOException e) {
      throw new Exception("Failed to load images from properties file");
    }
  }

  public ImageView createBlockView(String blockType) throws Exception {
    if (!validBlockTypes.contains(blockType)) {
      throw new Exception("Invalid block type: " + blockType);
    }
    Image blockImage = imageCache.get(blockType);
    if (blockImage == null) {
      throw new Exception("Image for block type not found, but type is valid: " + blockType);
    }
    return new ImageView(blockImage);
  }
}
