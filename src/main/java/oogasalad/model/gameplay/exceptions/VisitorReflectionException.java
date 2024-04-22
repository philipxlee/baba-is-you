package oogasalad.model.gameplay.exceptions;

/**
 * Exception thrown when the reflection for the visitor fails.
 *
 * @author Philip Lee.
 */
public class VisitorReflectionException extends RuntimeException {

  /**
   * Throws an exception when the reflection for the visitor fails.
   *
   * @param visitorName the name of the visitor.
   */
  public VisitorReflectionException(String visitorName) {
    super(visitorName);
  }

}
