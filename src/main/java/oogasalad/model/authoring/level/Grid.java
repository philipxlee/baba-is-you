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
public class Grid implements Observable<Grid>, Iterable<Stack<Block>> {

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
   * Remove last block from cell at given row and col position from 2D Array of Stacks.
   *
   * @param row The row of the cell.
   * @param col The col of the cell.
   * @throws Exception Throws exception if row or col is invalid.
   */
  public void removeBlockFromCell(int row, int col) throws Exception {
    if (row < 0 || row >= cells.length || col < 0 || col >= cells[row].length) {
      throw new Exception("Invalid Row/Col Position: " + row + " " + col);
    }
    cells[row][col].pop();
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
   * Iterator over the Stack of blocks in each grid position.
   *
   * @return Iterator<Stack < Block>>.
   */
  @Override
  public Iterator<Stack<Block>> iterator() {
    return new GridIterator(cells);
  }

  /**
   * Updates the current grid using the specified JSON grid data. The JSON grid includes a border of
   * empty blocks which are ignored during the update. Only the inner part of the JSON grid is used
   * to update the corresponding cells of the current grid. Each cell is cleared and then populated
   * with new blocks based on the JSON grid data.
   *
   * @param jsonGrid A 3D array representing the new grid state with a surrounding square of empty
   *                 blocks.
   */
  public void updateGrid(String[][][] jsonGrid) {
    try {
      int startRow = 1;
      int endRow = jsonGrid.length - 1;
      int startCol = 1;
      int endCol = jsonGrid[0].length - 1;

      for (int row = 0; row < cells.length; row++) {
        for (int col = 0; col < cells[row].length; col++) {
          int newRow = startRow + row;
          int newCol = startCol + col;

          if (newRow < endRow && newCol < endCol) {
            cells[row][col].clear();

            String[] blockTypes = jsonGrid[newRow][newCol];

            for (String blockType : blockTypes) {
              Block block = blockFactory.createBlock(blockType);
              cells[row][col].push(block);
            }
          }
        }
      }
      notifyObserver();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
