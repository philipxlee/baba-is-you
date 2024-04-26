package oogasalad.view.authoring;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;



public class GridSizeChanger {

  private final BuilderPane builderPane;


  public GridSizeChanger(BuilderPane builderPane) {
    this.builderPane = builderPane;
  }

  public void changeGridSize() {
    Optional<Pair<Integer, Integer>> result = showGridSizeDialog();
    result.ifPresent(this::processGridSizeChange);
  }

  private Optional<Pair<Integer, Integer>> showGridSizeDialog() {
    Dialog<Pair<Integer, Integer>> dialog = new Dialog<>();
    dialog.setTitle("Change Grid Size");
    ButtonType confirmButtonType = ButtonType.OK;
    dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    TextField widthField = new TextField();
    widthField.setPromptText("Width");
    TextField heightField = new TextField();
    heightField.setPromptText("Height");
    grid.add(new Label("Width:"), 0, 0);
    grid.add(widthField, 1, 0);
    grid.add(new Label("Height:"), 0, 1);
    grid.add(heightField, 1, 1);

    dialog.getDialogPane().setContent(grid);
    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == confirmButtonType) {
        try {
          int width = Integer.parseInt(widthField.getText());
          int height = Integer.parseInt(heightField.getText());
          if (isValidSize(width) && isValidSize(height)) {
            return new Pair<>(width, height);
          } else {
            showErrorMessage("Width and height must be between 5 and 20");
            return null;
          }
        } catch (NumberFormatException e) {
          showErrorMessage("Invalid number format");
          return null;
        }
      }
      return null;
    });

    return dialog.showAndWait();
  }

  private void processGridSizeChange(Pair<Integer, Integer> newSize) {
    builderPane.gridWidth = newSize.getKey();
    builderPane.gridHeight = newSize.getValue();
    builderPane.setUpGrid();
  }

  private boolean isValidSize(int size) {
    return size > 4 && size <= 20;
  }

  private void showErrorMessage(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
