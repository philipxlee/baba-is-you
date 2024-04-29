package oogasalad.model.gameplay.exceptions;

/**
 * Custom exception class that extends Exception. This exception is specifically thrown
 * to signal errors encountered during the parsing of JSON data. It is designed to provide
 * a clear indication of issues related to JSON operations, such as format errors or
 * missing expected fields. This class provides constructors to specify an error message
 * and optionally include the cause of the exception.
 */
public class JsonParsingException extends Exception {

  /**
   * Constructs a new JsonParsingException with the specified detail message.
   * This message can be used to provide additional information about the parsing error.
   *
   * @param message the detailed message describing the error encountered during JSON parsing.
   */
  public JsonParsingException(String message) {
    super(message);
  }

  /**
   * Constructs a new JsonParsingException with the specified detail message and cause.
   * This constructor is useful for wrapping lower-level exceptions that indicate problems
   * with JSON processing and attaching a more contextual high-level error message.
   *
   * @param message the detailed message describing the error encountered during JSON parsing.
   * @param cause the cause of the exception.
   */
  public JsonParsingException(String message, Throwable cause) {
    super(message, cause);
  }
}
