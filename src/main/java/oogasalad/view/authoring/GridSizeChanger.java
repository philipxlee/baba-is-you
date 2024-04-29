package oogasalad.view.authoring;

import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import oogasalad.controller.authoring.LevelController;
import oogasalad.model.authoring.level.Grid;
import oogasalad.model.authoring.level.LevelMetadata;

/**
 * Manages the functionality to change the size of the grid in a BuilderPane.
 * Provides a dialog interface for user input and handles validation and updating
 * of grid dimensions within the LevelController.
 */
public class GridSizeChanger {

  private final BuilderPane builderPane;
  private final LevelController levelController;

  private ResourceBundle messages = ResourceBundle.getBundle("error_bundle/authoring_errors");

  /**
   * Constructs a new GridSizeChanger.
   *
   * @param builderPane the BuilderPane associated with this changer, which displays the grid
   * @param levelController the controller responsible for managing level data and operations
   */
  public GridSizeChanger(BuilderPane builderPane, LevelController levelController) {
    this.builderPane = builderPane;
    this.levelController = levelController;
  }

  /**
   * Initiates the process to change the grid size by displaying a dialog for user input.
   * Processes the input and applies the grid size change if the input is valid.
   */
  public void changeGridSize() {
    Optional<Integer> result = showGridSizeDialog();
    result.ifPresent(this::processGridSizeChange);
  }

  /**
   * Displays a dialog to the user for entering the new grid size. Validates the input
   * to ensure it meets specific criteria.
   *
   * @return an Optional containing an Integer representing the size of the grid if valid input was provided, otherwise an empty Optional
   */
  private Optional<Integer> showGridSizeDialog() {
    Dialog<Integer> dialog = new Dialog<>();
    dialog.setTitle("Change Grid Size");
    ButtonType confirmButtonType = ButtonType.OK;
    dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    TextField sizeField = new TextField();
    sizeField.setPromptText("Size");
    grid.add(new Label("Size:"), 0, 0);
    grid.add(sizeField, 1, 0);

    dialog.getDialogPane().setContent(grid);
    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == confirmButtonType) {
        try {
          int size = Integer.parseInt(sizeField.getText());
          if (isValidSize(size)) {
            return size;
          } else {
            showErrorMessage(messages.getString("grid_size_error_message"));
            return null;
          }
        } catch (NumberFormatException e) {
          showErrorMessage(messages.getString("invalid_number_error_message"));
          return null;
        }
      }
      return null;
    });

    return dialog.showAndWait();
  }

  /**
   * Processes the grid size change by updating the BuilderPane and LevelController
   * with the new grid dimensions.
   *
   * @param newSize the new size of the grid
   */
  private void processGridSizeChange(Integer newSize) {
    builderPane.gridWidth = newSize;
    builderPane.gridHeight = newSize;
    alertGrid();
    LevelMetadata levelMetadata = new LevelMetadata("", "", builderPane.gridHeight, builderPane.gridWidth, "", "", "");
    levelController.resetLevel(levelMetadata);
  }

  /**
   * Displays a confirmation dialog when clearing the grid or making significant modifications.
   */
  protected void alertGrid() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(messages.getString("grid.confirmationTitle"));
    alert.setHeaderText(messages.getString("grid.warning"));
    alert.setContentText(messages.getString("grid.changeConfirmation"));
    ButtonType buttonTypeYes = new ButtonType(messages.getString("yes"));
    ButtonType buttonTypeNo = new ButtonType(messages.getString("no"));
    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == buttonTypeYes) {
      builderPane.setUpGrid();
    }
  }

  /**
   * Validates the provided grid size.
   *
   * @param size the size of the grid to validate
   * @return true if the size is within the valid range (5 to 20, inclusive), false otherwise
   */
  private boolean isValidSize(int size) {
    return size > 4 && size <= 20;
  }

  /**
   * Displays an error message to the user in case of invalid input.
   *
   * @param message the error message to display
   */
  private void showErrorMessage(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(messages.getString("error.creation"));
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
