package oogasalad.model.gameplay.grid;

import java.util.List;
import java.util.Optional;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;

/**
 * This class is responsible for updating the block in the grid.
 *
 * @author Philip Lee.
 */
public class BlockUpdater {

  private static final int INDEX_NOT_FOUND = -1;
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
   * @param block        block to update.
   * @param newBlockType new block type.
   */
  public void updateBlock(AbstractBlock block, String newBlockType) {
    int i = block.getRow();
    int j = block.getCol();
    List<AbstractBlock> cellBlocks = grid.getGrid()[i][j];
    findBlockIndex(cellBlocks, block)
        .ifPresent(index -> replaceBlock(cellBlocks, index, newBlockType, i, j));
  }

  /**
   * Finds the index of the block in the cell.
   *
   * @param cellBlocks list of blocks in the cell.
   * @param block      block to find.
   * @return index of the block.
   */
  private Optional<Integer> findBlockIndex(List<AbstractBlock> cellBlocks, AbstractBlock block) {
    int index = cellBlocks.indexOf(block);
    return index == INDEX_NOT_FOUND ? Optional.empty() : Optional.of(index);
  }

  /**
   * Replaces the block in the cell.
   *
   * @param cellBlocks   list of blocks in the cell.
   * @param index        index of the block to replace.
   * @param newBlockType new block type.
   * @param row          row of the block.
   * @param col          column of the block.
   */
  private void replaceBlock(List<AbstractBlock> cellBlocks, int index, String newBlockType, int row,
      int col) {
    cellBlocks.set(index, factory.createBlock(newBlockType, row, col));
  }

}
