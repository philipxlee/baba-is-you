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
  private final Grid grid;
  private final List<Observer<Level>> observers;

  /**
   * Level Constructor. Initialized with LevelMetadata record.
   *
   * @param levelMetadata The levelMetadata record representing the level.
   */
  public Level(LevelMetadata levelMetadata) {
    this.levelMetadata = levelMetadata;
    grid = new Grid(levelMetadata.rows(), levelMetadata.cols());
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
   * Returns a 3D grid of block types (strings) representing the current state of the grid.
   *
   * @return 3D List of strings representing the grid.
   */
  public List<List<List<String>>> getParsedGrid() {
    // Initialize a 3D list to dynamically handle varying numbers of blocks per stack
    List<List<List<String>>> dynamicGrid = new ArrayList<>();

    // Iterate through the entire grid using the iterator
    for (int row = 0; row < levelMetadata.rows(); row++) {
      List<List<String>> rowList = new ArrayList<>();
      for (int col = 0; col < levelMetadata.cols(); col++) {
        rowList.add(new ArrayList<>());
      }
      dynamicGrid.add(rowList);
    }

    Iterator<Stack<Block>> it = grid.iterator();
    int row = 0, col = 0;
    while (it.hasNext()) {
      Stack<Block> blockStack = it.next();
      for (Block block : blockStack) {
        dynamicGrid.get(row).get(col).add(block.type().name());
      }
      col++;
      if (col == levelMetadata.cols()) {
        col = 0;
        row++;
      }
    }

    return dynamicGrid;
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