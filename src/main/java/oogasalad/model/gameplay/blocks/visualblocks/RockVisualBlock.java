package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

/**
 * A visual block that represents a rock in the game.
 *
 * @author Philip Lee.
 */
public class RockVisualBlock extends AbstractVisualBlock {

  /**
   * Constructor for the RockVisualBlock class.
   *
   * @param name the name of the block.
   */
  public RockVisualBlock(String name, int row, int col) {
    super(name, row, col);
  }

  /**
   * Initializes the behaviors of the rock block.
   *
   * @param visitor The visitor to accept.
   */
  @Override
  public void accept(BlockVisitor visitor) {
    visitor.visit(this);
  }

}
