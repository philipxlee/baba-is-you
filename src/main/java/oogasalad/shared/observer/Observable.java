package oogasalad.shared.observer;

/**
 * Observable defines the behavior for publishing changes to Observers.
 *
 * @param <T> The type of Observable object.
 */
public interface Observable<T> {

  /**
   * Add Observer to collection of subscribers.
   *
   * @param o The Observer to add to notification service.
   */
  void addObserver(Observer<T> o);

  /**
   * Notify Observers of state change.
   */
  void notifyObserver();
}
