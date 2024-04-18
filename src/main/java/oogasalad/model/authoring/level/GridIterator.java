package oogasalad.model.authoring.level;

import java.util.Iterator;
import java.util.Stack;
import oogasalad.model.authoring.block.Block;

public class GridIterator implements Iterator<Stack<Block>> {

  private int currentRow;
  private int currentCol;
  private Stack<Block>[][] cells;

  public GridIterator(Stack<Block>[][] cells) {
    this.cells = cells;
    currentRow = 0;
    currentCol = 0;
    // Ensure the iterator starts at a valid position
    advanceToNextCell();
  }

  private void advanceToNextCell() {
    while (currentRow < cells.length && cells[currentRow][currentCol] == null) {
      currentCol++;
      if (currentCol >= cells[currentRow].length) {
        currentCol = 0;
        currentRow++;
      }
    }
  }

  @Override
  public boolean hasNext() {
    return currentRow < cells.length && currentCol < cells[currentRow].length;
  }

  @Override
  public Stack<Block> next() {
    Stack<Block> currentStack = cells[currentRow][currentCol];
    currentCol++;
    if (currentCol >= cells[currentRow].length) {
      currentCol = 0;
      currentRow++;
    }
    advanceToNextCell();
    return currentStack;
  }
}