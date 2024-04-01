package oogasalad.shared.viewblocks.textblocksview;

import oogasalad.shared.viewblocks.AbstractBlockView;

/**
 * A view for the text block that represents the "is" verb.
 */
public class IsTextBlockView extends AbstractBlockView {

  /**
   * Initializes a new "is" text view block.
   *
   * @param imgPath path to image that will initialize the view block.
   */
  public IsTextBlockView(String imgPath) {

    super(imgPath);
    System.out.println("Is created");
  }
}
