package oogasalad.shared.viewblocks;

import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a generic view block within the game. This class provides the base functionality and
 * interface for the view blocks and will be shared between the game player and authoring
 * environment.
 */
public abstract class AbstractBlockView {

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
    System.out.println(imgPath);
    InputStream inputStream = AbstractBlockView.class.getResourceAsStream(imgPath);
    if (inputStream != null) {
      Image image = new Image(inputStream);
      imageView = new ImageView(image);
    }
    else {
      System.out.println("fail");
    }
  }

  /**
   * The getView() method returns the stackPane that represents the view of the block.
   */
  public ImageView getView() {
    return this.imageView;
  }
}
