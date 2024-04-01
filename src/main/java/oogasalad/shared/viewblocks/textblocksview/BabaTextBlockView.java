package oogasalad.shared.viewblocks.textblocksview;


import oogasalad.shared.viewblocks.AbstractBlockView;

/**
 * A view for the text block that represents the "baba" noun.
 */
public class BabaTextBlockView extends AbstractBlockView {

  /**
   * Initializes a new "baba" text view block.
   *
   * @param imgPath path to image that will initialize the view block.
   */
  public BabaTextBlockView(String imgPath) {

    super(imgPath);
    System.out.println("BabaText created");
  }
}
