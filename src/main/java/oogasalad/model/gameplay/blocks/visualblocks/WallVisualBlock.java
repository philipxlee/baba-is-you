package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

/**
 * A visual block that represents a wall in the game.
 */
public class WallVisualBlock extends AbstractVisualBlock {

  /**
   * Constructor for the WallVisualBlock class.
   *
   * @param name the name of the block.
   */
  public WallVisualBlock(String name) {
    super(name);
  }

  /**
   * Initializes the behaviors of the wall block.
   *
   * @param visitor The visitor to accept.
   */
  @Override
  public void accept(BlockVisitor visitor) {
    System.out.println("Visited wall");
    visitor.visit(this);
  }

}
