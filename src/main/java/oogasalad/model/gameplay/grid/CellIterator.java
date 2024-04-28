package oogasalad.model.gameplay.grid;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import oogasalad.model.gameplay.blocks.AbstractBlock;

/**
 * This class represents an iterator for the blocks in a cell.
 *
 * @author Philip Lee.
 */
public class CellIterator implements Iterator<AbstractBlock> {

  private final List<AbstractBlock> cellBlocks;
  private int currentIndex;

  /**
   * Constructor for CellIterator.
   *
   * @param grid grid to iterate over.
   * @param row  row index.
   * @param col  column index.
   */
  public CellIterator(List<AbstractBlock>[][] grid, int row, int col) {
    if (row >= grid.length || col >= grid[row].length) {
      throw new IllegalArgumentException("Row or column index is out of bounds.");
    }
    this.cellBlocks = grid[row][col];
    this.currentIndex = 0;
  }

  /**
   * Checks if there are more blocks in the cell.
   *
   * @return true if there are more blocks, false otherwise.
   */
  @Override
  public boolean hasNext() {
    return currentIndex < cellBlocks.size();
  }

  /**
   * Returns the next block in the cell.
   *
   * @return the next block in the cell.
   */
  @Override
  public AbstractBlock next() {
    if (!hasNext()) {
      throw new NoSuchElementException("No more blocks in the cell.");
    }
    return cellBlocks.get(currentIndex++);
  }

}
