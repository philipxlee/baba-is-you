package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

/**
 * A visual block that represents the Water block in the game.
 *
 * @author Philip Lee.
 */
public class WaterVisualBlock extends AbstractVisualBlock {

  /**
   * Constructor for the WallVisualBlock class.
   *
   * @param name the name of the block.
   */
  public WaterVisualBlock(String name, int row, int col) {
    super(name, row, col);
  }

  /**
   * Initializes the behaviors of the wall block.
   *
   * @param visitor The visitor to accept.
   */
  @Override
  public void accept(BlockVisitor visitor) {
    visitor.visit(this);
  }


}
