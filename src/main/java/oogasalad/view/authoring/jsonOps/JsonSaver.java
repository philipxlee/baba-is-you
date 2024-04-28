package oogasalad.view.authoring.jsonOps;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import oogasalad.controller.authoring.LevelController;
import oogasalad.model.authoring.block.Block;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.view.authoring.BuilderPane;

public class JsonSaver {

  private final LevelController levelController;
  private final BuilderPane builderPane;
  private Level level;
  LevelValidator validator;
  private String difficulty;

  public JsonSaver(LevelController levelController, BuilderPane builderPane) {
    this.levelController = levelController;
    this.builderPane = builderPane;
    this.level = levelController.getLevel();
    this.validator = new LevelValidator();
  }

  public void saveJson(String difficulty) {
    this.difficulty = difficulty;
    GridPane inputGrid = createInputGrid();
    Alert confirmation = createConfirmationAlert(inputGrid);
    confirmation.showAndWait().ifPresent(buttonType -> {
      try {
        handleConfirmation(buttonType, confirmation);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private GridPane createInputGrid() {
    TextField levelNameField = createTextField("Enter level name");
    TextField levelDescriptionField = createTextField("Enter level description");
    TextField authorNameField = createTextField("Enter author name");
    TextField hintField = createTextField("Enter a hint");

    GridPane inputGrid = new GridPane();
    inputGrid.setHgap(10);
    inputGrid.setVgap(10);
    inputGrid.addRow(0, new Label("Level Name:"), levelNameField);
    inputGrid.addRow(1, new Label("Level Description:"), levelDescriptionField);
    inputGrid.addRow(2, new Label("Author Name:"), authorNameField);
    inputGrid.addRow(3, new Label("Add a Hint:"), hintField);
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
    confirmation.getButtonTypes().setAll(new ButtonType("Yes", ButtonBar.ButtonData.YES),
        new ButtonType("No", ButtonBar.ButtonData.NO));
    return confirmation;
  }

  private void handleConfirmation(ButtonType buttonType, Alert confirmation) throws IOException {
    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
      String levelName = getFieldText(0, confirmation);
      String levelDescription = getFieldText(1, confirmation);
      String authorName = getFieldText(2, confirmation);
      String hint = getFieldText(3, confirmation);
      boolean isValidLevel = validator.validateLevel(level);
//      if (isValidLevel) {
//        LevelMetadata levelMetadata = levelController.getLevelMetadata();
//        levelController.setCurrentLevel(new Level(levelMetadata, level.getGrid()));
        File savedFile = levelController.serializeLevel();
        if (savedFile != null) {
          showAlert("Success", "JSON saved successfully!");
        }
      } else {
        showAlert("Invalid Level", "The level must contain at least 2 rules: {Object 1 Is You}, {Object 2 Is Win} and at least Object 1 and Object 2. Please add the required blocks before saving.");
      }
//    }
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
    alert.setWidth(10);
    alert.setHeight(200);
    alert.showAndWait();
  }

}

