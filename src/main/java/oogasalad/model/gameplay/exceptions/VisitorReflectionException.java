package oogasalad.model.gameplay.exceptions;

/**
 * Exception thrown when the reflection for the visitor fails.
 */
public class VisitorReflectionException extends RuntimeException {

  /**
   * Throws an exception when the reflection for the visitor fails.
   *
   * @param visitorName the name of the visitor.
   * @param cause       the cause of the exception.
   */
  public VisitorReflectionException(String visitorName, Throwable cause) {
    super(visitorName, cause);
  }

}
