package oogasalad.model.authoring.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import oogasalad.model.authoring.block.Block;
import oogasalad.model.authoring.block.BlockFactory;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

/**
 * Grid holds the state of a grid of blocks. Implements observable to provide notifications on state
 * changes. Implements Iterable to provide iterator interface.
 */
public class Grid implements Observable<Grid>, Iterable<Block> {

  private final BlockFactory blockFactory;
  private final Stack<Block>[][] cells;
  private final List<Observer<Grid>> observers;

  /**
   * Grid constructor. Initialized with number of rows and number of columns.
   */
  public Grid(int rows, int cols) {
    this.blockFactory = BlockFactory.getInstance();
    cells = new Stack[rows][cols];
    observers = new ArrayList<>();
    initializeGrid();
  }

  /**
   * Initializes grid as 2D grid of Stacks. Each stack contains one EmptyVisualBlock.
   */
  private void initializeGrid() {
    try {
      for (int row = 0; row < cells.length; row++) {
        for (int col = 0; col < cells[row].length; col++) {
          Stack<Block> stack = new Stack<>();
          stack.push(blockFactory.createBlock("EmptyVisualBlock"));
          cells[row][col] = stack;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a Block of a specific type to the grid. Type must be in block type properties file.
   *
   * @param row       Row position of new block.
   * @param col       Column position of new block.
   * @param blockType The block type as a string.
   * @throws Exception Throws exception if the block type or cell position is invalid.
   */
  public void addBlockToCell(int row, int col, String blockType) throws Exception {
    if (row < 0 || row >= cells.length || col < 0 || col >= cells[row].length) {
      throw new Exception("Invalid Row/Col Position: " + row + " " + col);
    }
    Block block = blockFactory.createBlock(blockType);
    cells[row][col].push(block);
    notifyObserver();
  }

  /**
   * Add Grid observer to list of observers.
   *
   * @param o The Observer to add to notification service.
   */
  @Override
  public void addObserver(Observer<Grid> o) {
    observers.add(o);
  }

  /**
   * Notify observers of an update.
   */
  @Override
  public void notifyObserver() {
    for (Observer<Grid> observer : observers) {
      observer.update(this);
    }
  }

  /**
   * Iterator over the blocks of the grid.
   *
   * @return Iterator<Block>.
   */
  @Override
  public Iterator<Block> iterator() {
    return new Iterator<>() {
      private int row = 0, col = 0;

      /**
       * Checks if there is another block in iterator.
       *
       * @return Boolean if there is at least one block left.
       */
      @Override
      public boolean hasNext() {
        while (row < cells.length && (col >= cells[row].length || cells[row][col].isEmpty())) {
          col++;
          if (col >= cells[row].length) {
            col = 0;
            row++;
          }
        }
        return row < cells.length;
      }

      /**
       * Returns next block in iterator and updates pointers.
       *
       * @return Next block.
       */
      @Override
      public Block next() {
        Block block = cells[row][col].pop();
        if (cells[row][col].isEmpty()) {
          col++;
          if (col >= cells[row].length) {
            col = 0;
            row++;
          }
        }
        return block;
      }
    };
  }
}
