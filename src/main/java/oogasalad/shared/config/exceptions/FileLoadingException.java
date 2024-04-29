package oogasalad.shared.config.exceptions;

public class FileLoadingException extends RuntimeException {
  public FileLoadingException(String message) {
    super(message);
  }

  public FileLoadingException(String message, Throwable cause) {
    super(message, cause);
  }
}
