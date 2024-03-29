package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

/**
 * This class represents an empty visual block that does not have any behavior. It is used to
 * represent empty spaces in the grid.
 */
public class EmptyVisualBlock extends AbstractVisualBlock {

  /**
   * Constructor for the EmptyVisualBlock class
   *
   * @param name the name of the block
   */
  public EmptyVisualBlock(String name) {
    super(name);
  }

  /**
   * Accepts a visitor to visit this block
   *
   * @param visitor The visitor to accept.
   */
  @Override
  public void accept(BlockVisitor visitor) {
    // Do nothing.
  }
}
