package oogasalad.shared.viewblocks.visualblocksview;

import oogasalad.shared.viewblocks.AbstractBlockView;

/**
 * A view object that represents the empty block.
 */
public class EmptyVisualBlockView extends AbstractBlockView {

  /**
   * Initializes a new empty visual view block.
   *
   * @param imgPath path to image that will initialize the view block.
   */
  public EmptyVisualBlockView(String imgPath) {
    super(imgPath);
    System.out.println("Empty created");
  }

}
