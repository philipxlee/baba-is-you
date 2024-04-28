package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

/**
 * CrabVisualBlock is a visual block that represents a crab in the game.
 *
 * @author Philip Lee.
 */
public class CrabVisualBlock extends AbstractVisualBlock {

    /**
     * Constructor for CrabVisualBlock.
     *
     * @param name the name of the block
     * @param row  the row position of the block
     * @param col the column position of the block
     */
    public CrabVisualBlock(String name, int row, int col) {
        super(name, row, col);
    }

    /**
     * Accept method for the BlockVisitor pattern.
     *
     * @param visitor The BlockVisitor instance to process this block.
     */
    @Override
    public void accept(BlockVisitor visitor) {
        // Do nothing, as CrabVisualBlock does not have any visitor or rule associated with it.
    }

}
