package oogasalad.model.authoring;

import java.util.ArrayList;
import java.util.List;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

/**
 * GridModel holds the state of a grid of blocks.
 */
public class GridModel implements Observable<GridModel> {

  private List<Observer<GridModel>> observers;

  /**
   * GridModel constructor.
   */
  public GridModel() {
    observers = new ArrayList<>();
  }

  /**
   * Add GridModel observer to list of observers.
   *
   * @param o The Observer to add to notification service.
   */
  @Override
  public void addObserver(Observer<GridModel> o) {
    observers.add(o);
  }

  /**
   * Notify observers of an update.
   */
  @Override
  public void notifyObserver() {
    for (Observer<GridModel> observer : observers) {
      observer.update(this);
    }
  }
}
