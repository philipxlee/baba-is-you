package oogasalad.shared.config.exceptions;

/**
 * Custom exception class that extends RuntimeException. This exception is thrown when an error
 * occurs during the file loading process. It provides constructors to specify the error message and
 * optionally include the underlying cause of the exception. This makes it easier to trace back to
 * the root cause of the error during debugging.
 */
public class FileLoadingException extends RuntimeException {

  /**
   * Constructs a new FileLoadingException with the specified detail message. The message can be
   * used to provide additional information about the error.
   *
   * @param message the detailed message.
   */
  public FileLoadingException(String message) {
    super(message);
  }

  /**
   * Constructs a new FileLoadingException with the specified detail message and cause.
   * <p>
   * Note that the detail message associated with {@code cause} is not automatically incorporated in
   * this exception's detail message.
   *
   * @param message the detailed message.
   * @param cause   the cause of the error.
   */
  public FileLoadingException(String message, Throwable cause) {
    super(message, cause);
  }
}
