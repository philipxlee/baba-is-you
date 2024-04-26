package oogasalad.view.authoring.jsonOps;

import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import oogasalad.controller.authoring.LevelController;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.view.authoring.BuilderPane;

public class JsonSaver {

  private final LevelController levelController;
  private final BuilderPane builderPane;

  public JsonSaver(LevelController levelController, BuilderPane builderPane) {
    this.levelController = levelController;
    this.builderPane = builderPane;
  }

  public void saveJson() {
    GridPane inputGrid = createInputGrid();
    Alert confirmation = createConfirmationAlert(inputGrid);
    confirmation.showAndWait().ifPresent(buttonType -> handleConfirmation(buttonType, confirmation));
  }

  private GridPane createInputGrid() {
    TextField levelNameField = createTextField("Enter level name");
    TextField levelDescriptionField = createTextField("Enter level description");
    TextField authorNameField = createTextField("Enter author name");

    GridPane inputGrid = new GridPane();
    inputGrid.setHgap(10);
    inputGrid.setVgap(10);
    inputGrid.addRow(0, new Label("Level Name:"), levelNameField);
    inputGrid.addRow(1, new Label("Level Description:"), levelDescriptionField);
    inputGrid.addRow(2, new Label("Author Name:"), authorNameField);
    return inputGrid;
  }

  private TextField createTextField(String promptText) {
    TextField textField = new TextField();
    textField.setPromptText(promptText);
    return textField;
  }

  private Alert createConfirmationAlert(GridPane inputGrid) {
    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    confirmation.setTitle("Save JSON");
    confirmation.setHeaderText("Enter level details and save JSON:");
    confirmation.getDialogPane().setContent(inputGrid);
    confirmation.getButtonTypes().setAll(new ButtonType("Yes", ButtonBar.ButtonData.YES), new ButtonType("No", ButtonBar.ButtonData.NO));
    return confirmation;
  }

  private void handleConfirmation(ButtonType buttonType, Alert confirmation) {
    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
      String levelName = getFieldText(0, confirmation);
      String levelDescription = getFieldText(1, confirmation);
      String authorName = getFieldText(2, confirmation);
      LevelMetadata levelMetadata = new LevelMetadata(levelName, levelDescription, authorName, builderPane.gridHeight, builderPane.gridWidth);
      File savedFile = levelController.serializeLevel();
      if (savedFile != null) {
        showAlert("Success", "JSON saved successfully!");
      }
    }
  }

  private String getFieldText(int rowIndex, Alert confirmation) {
    GridPane dialogContent = (GridPane) confirmation.getDialogPane().getContent();
    return ((TextField) dialogContent.getChildren().get(rowIndex * 2 + 1)).getText();
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}

