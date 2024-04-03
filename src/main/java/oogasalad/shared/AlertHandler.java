package oogasalad.shared;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Interface for handling alerts and displaying alert messages.
 */
public interface AlertHandler {


  /**
   * Displays an error message in an alert dialog.
   *
   * @param title   the title of the alert dialog.
   * @param message the message to be displayed in the alert dialog.
   */
  default void showError(String title, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    createAlert(title, message, alert);
  }


  /**
   * Displays a warning message in an alert dialog.
   *
   * @param title   the title of the alert dialog.
   * @param message the message to be displayed in the alert dialog.
   */
  default void showWarning(String title, String message) {
    Alert alert = new Alert(AlertType.WARNING);
    createAlert(title, message, alert);
  }


  /**
   * Displays an information message in an alert dialog.
   *
   * @param title   the title of the alert dialog.
   * @param message the message to be displayed in the alert dialog.
   */
  default void showInformation(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    createAlert(title, message, alert);
  }


  /**
   * Creates and displays an alert dialog with the specified title and message.
   *
   * @param title   the title of the alert dialog.
   * @param message the message to be displayed in the alert dialog.
   * @param alert   the alert instance to be used for displaying the alert dialog.
   */
  private void createAlert(String title, String message, Alert alert) {
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }


}


