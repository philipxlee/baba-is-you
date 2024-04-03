package oogasalad.model.authoring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

/**
 * GridModel holds the state of a grid of blocks.
 */
public class Grid implements Observable<Grid> {

  private final Block[][] cells;
  private List<Observer<Grid>> observers;

  /**
   * Grid constructor. Initialized with number of rows and number of columns.
   */
  public Grid(int rows, int cols) throws IOException {
    cells = new Block[rows][cols];
    observers = new ArrayList<>();
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
