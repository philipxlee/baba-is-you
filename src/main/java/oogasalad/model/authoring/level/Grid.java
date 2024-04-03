package oogasalad.model.authoring.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import oogasalad.model.authoring.block.Block;
import oogasalad.model.authoring.block.BlockType;
import oogasalad.model.authoring.block.BlockTypeManager;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

/**
 * Grid holds the state of a grid of blocks. Implements observable to provide notifications on state
 * changes. Implements Iterable to provide iterator interface.
 */
public class Grid implements Observable<Grid>, Iterable<Block> {

  private final BlockTypeManager blockTypeManager;
  private final Block[][] cells;
  private List<Observer<Grid>> observers;

  /**
   * Grid constructor. Initialized with number of rows and number of columns.
   */
  public Grid(int rows, int cols, BlockTypeManager blockTypeManager) {
    this.blockTypeManager = blockTypeManager;
    cells = new Block[rows][cols];
    observers = new ArrayList<>();
    initializeGrid();
  }

  /**
   * Initializes grid with Empty blocks.
   */
  private void initializeGrid() {
    try {
      BlockType emptyType = blockTypeManager.findBlockTypeByName("Empty");
      for (int row = 0; row < cells.length; row++) {
        for (int col = 0; col < cells[row].length; col++) {
          cells[row][col] = new Block(emptyType);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a Block of a specific type to the grid. Type must be in block type properties file.
   *
   * @param row  Row position of new block.
   * @param col  Column position of new block.
   * @param name The name of the new block type.
   * @throws Exception Throws exception if the name is invalid (not in properties file).
   */
  public void setCell(int row, int col, String name) throws Exception {
    BlockType blockType = blockTypeManager.findBlockTypeByName(name);
    if (row >= 0 && row < cells.length && col >= 0 && col < cells[row].length) {
      cells[row][col] = new Block(blockType);
      notifyObserver();
    }
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
        return row < cells.length && col < cells[row].length;
      }

      /**
       * Returns next block in iterator and updates pointers.
       *
       * @return Next block.
       */
      @Override
      public Block next() {
        Block block = cells[row][col++];
        if (col >= cells[row].length) {
          col = 0;
          row++;
        }
        return block;
      }
    };
  }
}
