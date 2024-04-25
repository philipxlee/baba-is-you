package oogasalad.view.authoring;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import oogasalad.controller.authoring.LevelController;
import oogasalad.model.authoring.level.LevelMetadata;

public class JsonSaver {

  private final LevelController levelController;
  private final BuilderPane builderPane;

  public JsonSaver(LevelController levelController, BuilderPane builderPane) {
    this.levelController = levelController;
    this.builderPane = builderPane;
  }

  public void saveJson() {
    // Create text fields for level name, level description, and author name
    TextField levelNameField = new TextField();
    levelNameField.setPromptText("Enter level name");
    TextField levelDescriptionField = new TextField();
    levelDescriptionField.setPromptText("Enter level description");
    TextField authorNameField = new TextField();
    authorNameField.setPromptText("Enter author name");

    // Create a grid pane to hold the input fields
    GridPane inputGrid = new GridPane();
    inputGrid.setHgap(10);
    inputGrid.setVgap(10);
    inputGrid.addRow(0, new Label("Level Name:"), levelNameField);
    inputGrid.addRow(1, new Label("Level Description:"), levelDescriptionField);
    inputGrid.addRow(2, new Label("Author Name:"), authorNameField);

    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    confirmation.setTitle("Save JSON");
    confirmation.setHeaderText("Enter level details and save JSON:");
    confirmation.getDialogPane().setContent(inputGrid);

    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

    confirmation.getButtonTypes().setAll(yesButton, noButton);

    confirmation.showAndWait().ifPresent(buttonType -> {
      if (buttonType == yesButton) {
        // Retrieve values from text fields
        String levelName = levelNameField.getText();
        String levelDescription = levelDescriptionField.getText();
        String authorName = authorNameField.getText();

        // Proceed with saving the JSON using the provided details
        LevelMetadata levelMetadata = new LevelMetadata(levelName, levelDescription, authorName, builderPane.gridHeight, builderPane.gridWidth);
        levelController.serializeLevel();

        // Optionally, show a success message
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success");
        success.setHeaderText(null);
        success.setContentText("JSON saved successfully!");
        success.showAndWait();
      }
    });
  }
}

