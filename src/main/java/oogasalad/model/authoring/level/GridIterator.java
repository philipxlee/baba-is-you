package oogasalad.model.authoring.level;

import java.util.Iterator;
import java.util.Stack;
import oogasalad.model.authoring.block.Block;

/**
 * GridIterator is an iterator over the 2D grid of Stacks of blocks.
 */
public class GridIterator implements Iterator<Stack<Block>> {

  private int currentRow;
  private int currentCol;
  private Stack<Block>[][] cells;

  /**
   * GridIterator constructor.
   *
   * @param cells The 2D Grid of stacks of blocks.
   */
  public GridIterator(Stack<Block>[][] cells) {
    this.cells = cells;
    currentRow = 0;
    currentCol = 0;
    // Ensure the iterator starts at a valid position
    advanceToNextCell();
  }

  /**
   * Advance pointers to next cell.
   */
  private void advanceToNextCell() {
    while (currentRow < cells.length && cells[currentRow][currentCol] == null) {
      currentCol++;
      if (currentCol >= cells[currentRow].length) {
        currentCol = 0;
        currentRow++;
      }
    }
  }

  /**
   * Method to check if there is next stack in iteration.
   *
   * @return Boolean if there is another Stack<Block> in sequence.
   */
  @Override
  public boolean hasNext() {
    return currentRow < cells.length && currentCol < cells[currentRow].length;
  }

  /**
   * Method to return next stack in iteration.
   *
   * @return Next Stack<Block> in sequence.
   */
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