package oogasalad.view.authoring.jsonOps;

import java.io.File;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import oogasalad.controller.authoring.LevelController;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.view.authoring.BuilderPane;

/**
 * Handles the saving of level data into a JSON format, including collecting necessary metadata
 * through a user interface. It ensures that the level meets certain validation criteria before
 * saving.
 */
public class JsonSaver {

  private final LevelController levelController;
  private final BuilderPane builderPane;
  private Level level;
  LevelValidator validator;
  private String difficulty;

  /**
   * Constructs a JsonSaver that interacts with the given LevelController and BuilderPane.
   *
   * @param levelController the controller managing level-related actions
   * @param builderPane     the pane that displays the level and interfaces with the user
   */
  public JsonSaver(LevelController levelController, BuilderPane builderPane) {
    this.levelController = levelController;
    this.builderPane = builderPane;
    this.level = levelController.getLevel();
    this.validator = new LevelValidator();
  }

  /**
   * Initiates the JSON saving process by displaying a confirmation dialog with input fields for
   * level metadata.
   *
   * @param difficulty the difficulty setting of the level as defined by the user
   */
  public void saveJson(String difficulty) {
    this.difficulty = difficulty;
    this.level = levelController.getLevel();
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

  /**
   * Creates a grid pane with text fields for entering level metadata.
   *
   * @return a GridPane containing labeled text fields for user input
   */
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

  /**
   * Creates a TextField with a placeholder text.
   *
   * @param promptText the placeholder text to display when the text field is empty
   * @return a TextField configured with the specified placeholder text
   */
  private TextField createTextField(String promptText) {
    TextField textField = new TextField();
    textField.setPromptText(promptText);
    return textField;
  }

  /**
   * Creates a confirmation alert that contains the input grid.
   *
   * @param inputGrid the GridPane containing input fields for level metadata
   * @return an Alert configured as a confirmation dialog
   */
  private Alert createConfirmationAlert(GridPane inputGrid) {
    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    confirmation.setTitle("Save JSON");
    confirmation.setHeaderText("Enter level details and save JSON:");
    confirmation.getDialogPane().setContent(inputGrid);
    confirmation.getButtonTypes().setAll(new ButtonType("Yes", ButtonBar.ButtonData.YES),
        new ButtonType("No", ButtonBar.ButtonData.NO));
    return confirmation;
  }

  /**
   * Handles the user's response to the save confirmation alert.
   *
   * @param buttonType   the type of button pressed by the user
   * @param confirmation the confirmation Alert dialog
   * @throws IOException if there is an error during file saving
   */
  public void handleConfirmation(ButtonType buttonType, Alert confirmation) throws IOException {
    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
      String levelName = getFieldText(0, confirmation);
      String levelDescription = getFieldText(1, confirmation);
      String authorName = getFieldText(2, confirmation);
      String hint = getFieldText(3, confirmation);
      boolean isValidLevel = validator.validateLevel(level);
      if (isValidLevel) {
        LevelMetadata levelMetadata = new LevelMetadata(levelName, levelDescription,
            builderPane.gridHeight, builderPane.gridWidth, difficulty, authorName, hint);
        levelController.setCurrentLevel(new Level(levelMetadata, level.getGrid()));
        File savedFile = levelController.serializeLevel();
        if (savedFile != null) {
          showAlert("Success", "JSON saved successfully!");
        }
      } else {
        showAlert("Invalid Level",
            "The level must contain at least 2 rules: {Object 1 Is You}, {Object 2 Is Win} and at least Object 1 and Object 2. Please add the required blocks before saving.");
      }
    }
  }

  /**
   * Retrieves the text from a specified TextField within the dialog content.
   *
   * @param rowIndex     the index of the row in the grid containing the TextField
   * @param confirmation the Alert from which to retrieve the grid
   * @return the text from the TextField at the specified row
   */
  public String getFieldText(int rowIndex, Alert confirmation) {
    GridPane dialogContent = (GridPane) confirmation.getDialogPane().getContent();
    return ((TextField) dialogContent.getChildren().get(rowIndex * 2 + 1)).getText();
  }

  /**
   * Displays an alert message to the user.
   *
   * @param title   the title of the alert
   * @param message the message to display to the user
   */
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

