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
   * @param grid  grid to update block.
   * @param factory factory to create block.
   */
  public BlockUpdater(Grid grid, BlockFactory factory) {
    this.grid = grid;
    this.factory = factory;
  }

  /**
   * Update the block in the grid.
   *
   * @param i row index.
   * @param j column index.
   * @param k index of block in the list.
   * @param newBlockType new block type.
   */
  public void updateBlock(int i, int j, int k, String newBlockType) {
    List<AbstractBlock>[][] gameGrid = grid.getGrid();
    gameGrid[i][j].set(k, factory.createBlock(newBlockType));
  }
}
