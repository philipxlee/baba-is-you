package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

/**
 * A visual block that represents the Baba block in the game.
 *
 * @author Philip Lee.
 */
public class BabaVisualBlock extends AbstractVisualBlock {

  /**
   * Constructor for the BabaVisualBlock class.
   *
   * @param name the name of the block
   */
  public BabaVisualBlock(String name, int row, int col) {
    super(name, row, col);
  }

  /**
   * Accepts a visitor to visit this block.
   *
   * @param visitor The visitor to accept.
   */
  @Override
  public void accept(BlockVisitor visitor) {
    visitor.visit(this);
  }

}
