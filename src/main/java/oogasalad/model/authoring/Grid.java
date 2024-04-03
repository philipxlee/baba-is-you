package oogasalad.model.authoring;

import java.util.ArrayList;
import java.util.List;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

/**
 * GridModel holds the state of a grid of blocks.
 */
public class Grid implements Observable<Grid> {

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
   * Add GridModel observer to list of observers.
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
}
