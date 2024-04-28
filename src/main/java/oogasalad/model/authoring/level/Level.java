package oogasalad.model.authoring.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import oogasalad.model.authoring.block.Block;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

/**
 * Level abstraction encapsulates a Grid, LevelMetadata. Implements Observable to provide
 * notifications on state changes.
 */
public class Level implements Observable<Level> {

  private final LevelMetadata levelMetadata;
  private Grid grid;
  private final List<Observer<Level>> observers;

  /**
   * Level Constructor. Initialized with LevelMetadata record.
   *
   * @param levelMetadata The levelMetadata record representing the level.
   */
  public Level(LevelMetadata levelMetadata) {
    this.levelMetadata = levelMetadata;
    grid = new Grid(levelMetadata.getRows(), levelMetadata.getCols());
    observers = new ArrayList<>();
  }

  /**
   * Level Constructor. Initialized with LevelMetadata record and grid.
   *
   * @param levelMetadata The levelMetadata record representing the level.
   */
  public Level(LevelMetadata levelMetadata, Grid inputGrid) {
    this.levelMetadata = levelMetadata;
    grid = inputGrid;
    observers = new ArrayList<>();
  }

  /**
   * Set Cell of a level with block type. (type must be in block types properties file).
   *
   * @param row       The row position to be modified.
   * @param col       The column position to be modified.
   * @param blockType New block type.
   * @throws Exception Throws exception if block type is invalid (not in properties file).
   */
  public void addBlockToCell(int row, int col, String blockType) throws Exception {
    grid.addBlockToCell(row, col, blockType);
  }

  /**
   * Remove last block from cell at given row and col position from grid.
   *
   * @param row The row of the cell.
   * @param col The col of the cell.
   * @throws Exception Throws exception if row or col is invalid.
   */
  public void removeBlockFromCell(int row, int col) throws Exception {
    grid.removeBlockFromCell(row, col);
  }

  /**
   * Returns a 3D grid of block types (strings) representing the current state of the grid.
   *
   * @return 3D List of strings representing the grid.
   */
  public List<List<List<String>>> getParsedGrid() {

    int originalRows = grid.isLoading() ? levelMetadata.getRows() - 2 : levelMetadata.getRows();
    int originalCols = grid.isLoading() ? levelMetadata.getCols() - 2 : levelMetadata.getCols();

    List<List<List<String>>> extendedGrid = new ArrayList<>();
    for (int row = 0; row < originalRows + 2; row++) {
      List<List<String>> rowList = new ArrayList<>();
      for (int col = 0; col < originalCols + 2; col++) {
        rowList.add(new ArrayList<>());
      }
      extendedGrid.add(rowList);
    }

    for (int row = 0; row < originalRows + 2; row++) {
      for (int col = 0; col < originalCols + 2; col++) {
        if (row == 0 || row == originalRows + 1 || col == 0 || col == originalCols + 1) {
          extendedGrid.get(row).get(col).add("EmptyVisualBlock");
        }
      }
    }

    Iterator<Stack<Block>> it = grid.iterator();
    int row = 1, col = 1;
    while (it.hasNext()) {
      Stack<Block> blockStack = it.next();
      for (Block block : blockStack) {
        extendedGrid.get(row).get(col).add(block.type().name());
      }
      col++;
      if (col == originalCols + 1) {
        col = 1;
        row++;
      }
    }

    return extendedGrid;
  }

  /**
   * LevelMetadata record of level.
   *
   * @return Current LevelMetadata.
   */
  public LevelMetadata getLevelMetadata() {
    return levelMetadata;
  }

  /**
   * Returns the grid.
   *
   * @return Current LevelMetadata.
   */
  public Grid getGrid() {
    return grid;
  }

  /**
   * Sets the grid.
   */
  public void setGrid(Grid grid) {
    this.grid = grid;
  }

  /**
   * Add Grid observer to list of observers.
   *
   * @param o The Observer to add to notification service.
   */
  @Override
  public void addObserver(Observer<Level> o) {
    observers.add(o);
  }

  /**
   * Notify observers of an update.
   */
  @Override
  public void notifyObserver() {
    for (Observer<Level> observer : observers) {
      observer.update(this);
    }
  }
}