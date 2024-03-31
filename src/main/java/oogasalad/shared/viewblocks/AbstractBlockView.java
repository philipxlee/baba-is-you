package oogasalad.shared.viewblocks;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Represents a generic view block within the game. This class provides the base functionality and
 * interface for the view blocks and will be shared between the game player and authoring
 * environment.
 */
public abstract class AbstractBlockView {

  private StackPane stackPane;

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
    ImageView imageView = new ImageView(imgPath);
    stackPane = new StackPane(imageView);
  }

  /**
   * The getView() method returns the stackPane that represents the view of the block.
   */
  public StackPane getView() {
    return this.stackPane;
  }

}
