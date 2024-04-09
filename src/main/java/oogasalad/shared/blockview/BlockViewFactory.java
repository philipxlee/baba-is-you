package oogasalad.shared.blockview;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlockViewFactory {

  private final Map<String, Image> imageCache;
  private final Set<String> validBlockTypes;

  public BlockViewFactory(String imagesFilePath) {
    imageCache = new HashMap<>();
    validBlockTypes = new HashSet<>();
    loadImages(imagesFilePath);
  }

  private void loadImages(String imagesFilePath) {

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
