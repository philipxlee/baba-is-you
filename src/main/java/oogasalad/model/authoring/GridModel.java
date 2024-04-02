package oogasalad.model.authoring;

import java.util.ArrayList;
import java.util.List;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

public class GridModel implements Observable<GridModel> {

  private List<Observer<GridModel>> observers;

  public GridModel() {
    observers = new ArrayList<>();
  }

  @Override
  public void addObserver(Observer<GridModel> o) {
    observers.add(o);
  }

  @Override
  public void notifyObserver() {
    for (Observer<GridModel> observer : observers) {
      observer.update(this);
    }
  }
}
