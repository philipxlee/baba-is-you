package oogasalad.shared.blockviews;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a generic view block within the game. This class provides the base functionality and
 * interface for the view blocks and will be shared between the game player and authoring
 * environment.
 */
public abstract class AbstractBlockView {

  private static final Map<String, Image> imageCache = new HashMap<>(); // Important for performance
  private ImageView imageView;

  /**
   * AbstractBlockView constructor that initializes the block view.
   */
  public AbstractBlockView(String imgPath) {
    initializeBlock(imgPath);
  }

  /**
   * This method initializes the view block by going to the image path and making it the image for
   * the imageView.
   *
   * @param imgPath String that holds the path to the block image.
   */
  private void initializeBlock(String imgPath) {
    Image image = imageCache.get(imgPath);
    if (image == null) {
      try (InputStream inputStream = AbstractBlockView.class.getResourceAsStream(imgPath)) {
        if (inputStream == null) {
          System.out.println("Resource not found: " + imgPath);
        } else {
          // This is to prevent loading the same image multiple times
          // Fixes a loading bug and also increases performance
          // Do not remove!
          image = new Image(inputStream);
          imageCache.put(imgPath, image);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (image != null) {
      imageView = new ImageView(image);
    }
  }


  /**
   * The getView() method returns the stackPane that represents the view of the block.
   */
  public ImageView getView() {
    return this.imageView;
  }
}
