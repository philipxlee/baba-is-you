package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

/**
 * A visual block that represents a flag in the game. The player wins the game when they reach the
 * flag.
 *
 * @author Philip Lee.
 */
public class FlagVisualBlock extends AbstractVisualBlock {

  /**
   * Constructor for the FlagVisualBlock class.
   *
   * @param name the name of the block.
   */
  public FlagVisualBlock(String name, int row, int col) {
    super(name, row, col);
  }

  /**
   * Initializes the behaviors of the flag block.
   *
   * @param visitor The visitor to accept.
   */
  @Override
  public void accept(BlockVisitor visitor) {
    visitor.visit(this);
  }

}
