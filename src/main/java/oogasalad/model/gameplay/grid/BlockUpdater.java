package oogasalad.model.gameplay.grid;

import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;

/**
 * This class is responsible for updating the block in the grid.
 */
public class BlockUpdater {

  private final Grid grid;
  private final BlockFactory factory;

  /**
   * Constructor for BlockUpdater.
   *
   * @param grid    grid to update block.
   * @param factory factory to create block.
   */
  public BlockUpdater(Grid grid, BlockFactory factory) {
    this.grid = grid;
    this.factory = factory;
  }

  /**
   * Updates the block in the grid.
   *
   * @param block      block to update.
   * @param newBlockType new block type.
   */
  public void updateBlock(AbstractBlock block, String newBlockType) {
    int i = block.getRow();
    int j = block.getCol();
    List<AbstractBlock> cellBlocks = grid.getGrid()[i][j];
    int k = cellBlocks.indexOf(block);
    if (k != -1) {  // If the block is found
      cellBlocks.set(k, factory.createBlock(newBlockType, i, j));
    }
  }
}
