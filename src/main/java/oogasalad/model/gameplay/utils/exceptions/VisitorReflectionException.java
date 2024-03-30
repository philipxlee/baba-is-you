package oogasalad.model.gameplay.utils.exceptions;

/**
 * Exception thrown when the reflection for the visitor fails.
 */
public class VisitorReflectionException extends Exception {

  /**
   * Throws an exception when the reflection for the visitor fails.
   *
   * @param visitorName the name of the visitor.
   * @param cause       the cause of the exception.
   */
  public VisitorReflectionException(String visitorName, Throwable cause) {
    super("Could not instantiate a Visitor " +
          "Possible error in reflection for visitor " + visitorName, cause);
  }

}
