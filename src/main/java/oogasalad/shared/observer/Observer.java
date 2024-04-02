package oogasalad.shared.observer;

/**
 * Observer defines the behavior for listening to Observable T for state changes.
 *
 * @param <T> The type of the observable object.
 */
public interface Observer<T> {

  /**
   * Update function called by the Observable when it wants to notify about a state change.
   *
   * @param data The current (updated) state of the data.
   */
  void update(T data);
}
