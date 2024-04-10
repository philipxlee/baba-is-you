package oogasalad.model.gameplay.grid;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import oogasalad.model.gameplay.blocks.AbstractBlock;

public class GridIterator implements Iterator<AbstractBlock> {
  private final List<AbstractBlock>[][] grid;
  private int currentRow;
  private int currentCol;
  private int listIndex;

  public GridIterator(List<AbstractBlock>[][] grid) {
    this.grid = grid;
    this.currentRow = 0;
    this.currentCol = 0;
    this.listIndex = 0;
    // Ensure the iterator points to the first non-empty list
    findNextNonEmptyCell();
  }

  private void findNextNonEmptyCell() {
    while (currentRow < grid.length && (grid[currentRow][currentCol] == null || listIndex >= grid[currentRow][currentCol].size())) {
      listIndex = 0;
      currentCol++;
      if (currentCol >= grid[currentRow].length) {
        currentCol = 0;
        currentRow++;
      }
    }
  }

  @Override
  public boolean hasNext() {
    return currentRow < grid.length;
  }

  @Override
  public AbstractBlock next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    AbstractBlock nextBlock = grid[currentRow][currentCol].get(listIndex++);
    if (listIndex >= grid[currentRow][currentCol].size()) {
      currentCol++;
      if (currentCol >= grid[currentRow].length) {
        currentCol = 0;
        currentRow++;
      }
      listIndex = 0;
      findNextNonEmptyCell();
    }
    return nextBlock;
  }
}
