package oogasalad.model.authoring.level;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.authoring.block.Block;
import oogasalad.model.authoring.block.BlockFactory;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

/**
 * Level abstraction encapsulates a Grid, LevelMetadata. Implements Observable to provide
 * notifications on state changes.
 */
public class Level implements Observable<Level> {

  private final Grid grid;
  private final LevelMetadata levelMetadata;
  private final List<Observer<Level>> observers;

  /**
   * Level Constructor. Initialized with LevelMetadata record and BlockTypeManager
   *
   * @param levelMetadata The levelMetadata record representing the level.
   * @param blockFactory  The blockTypeManager being used in the application.
   */
  public Level(LevelMetadata levelMetadata, BlockFactory blockFactory) {
    this.levelMetadata = levelMetadata;
    grid = new Grid(levelMetadata.rows(), levelMetadata.cols(), blockFactory);
    observers = new ArrayList<>();
  }

  /**
   * Set Cell of a level with block type. (type must be in block types properties file).
   *
   * @param row       The row position to be modified.
   * @param col       The column position to be modified.
   * @param blockName New block type.
   * @throws Exception Throws exception if block type is invalid (not in properties file).
   */
  public void setCell(int row, int col, String blockName) throws Exception {
    grid.setCell(row, col, blockName);
  }

  /**
   * Returns a 2D grid of block types (strings) representing the current state of the grid.
   *
   * @return 2D array of strings representing the grid.
   */
  public String[][] getParsedGrid() {
    String[][] parsedGrid = new String[levelMetadata.rows()][levelMetadata.cols()];

    int row = 0;
    int col = 0;
    for (Block block : grid) {
      parsedGrid[row][col] = block.type().name();
      col++;
      if (col == levelMetadata.cols()) {
        col = 0;
        row++;
      }
    }

    return parsedGrid;
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