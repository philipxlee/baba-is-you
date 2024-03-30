package oogasalad.model.gameplay.utils.exceptions;

public class VisitorReflectionException extends Exception {

  public VisitorReflectionException(String visitorName, Throwable cause) {
    super("Could not instantiate a Visitor " +
          "Possible error in reflection for visitor " + visitorName, cause);
  }

}
