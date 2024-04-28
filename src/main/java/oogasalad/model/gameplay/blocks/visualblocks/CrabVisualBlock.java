package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

public class CrabVisualBlock extends AbstractVisualBlock {

    public CrabVisualBlock(String name, int row, int col) {
        super(name, row, col);
    }

    @Override
    public void accept(BlockVisitor visitor) {
        // Do nothing
    }

}
