package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

/**
 * This class represents an empty visual block that does not have any behavior. It is used to
 * represent empty spaces in the grid.
 *
 * @author Philip Lee.
 */
public class EmptyVisualBlock extends AbstractVisualBlock {

  /**
   * Constructor for the EmptyVisualBlock class.
   *
   * @param name the name of the block.
   */
  public EmptyVisualBlock(String name, int row, int col) {
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

  /**
   * Determines if the block is a text block.
   *
   * @return false by default, indicating this is not a text block.
   */
  @Override
  public boolean isEmptyVisualBlock() {
    return true;
  }
}
