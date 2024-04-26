package oogasalad.view.authoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;


import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import oogasalad.controller.authoring.LevelController;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;


public class ElementsPane {

  private final String IMAGE_FILE_PATH = "src/main/resources/blocktypes/blocktypes.json";

  private ResourceBundle resourceBundle = ResourceBundle.getBundle("error_bundle/authoring_errors");
  private final BuilderPane builderPane;
  private final BlockLoader blockLoader = new BlockLoader();
  private final WidgetFactory factory;
  private final LevelController levelController;
  private ScrollPane scrollPane;
  private VBox layout;
  private boolean removeMode = false;
  private String language;
  private MainScene mainScene;

  public ElementsPane(BuilderPane builderPane, LevelController levelController, MainScene
      mainScene) {
    this.factory = new WidgetFactory();
    this.builderPane = builderPane;
    this.levelController = levelController;
    this.language = levelController.getLanguage();
    this.mainScene = mainScene;
    initializeElementsLayout();
  }

  private void initializeElementsLayout() {
    layout = new VBox(15);

    Text title = factory.generateHeader(new WidgetConfiguration("BIU", language));
    Text subtitle = factory.generateSubHeader(new WidgetConfiguration(
        "AuthEnv", language));
    VBox header = factory.wrapInVBox(new ArrayList<Node>(Arrays.asList(title, subtitle)),
        (int) layout.getHeight(), 10);
    header.setSpacing(0);

    Text descriptionLabel = factory.generateLine(new WidgetConfiguration
        ("DragInstructions", language));

    // Category selection setup
    ComboBox<String> categoryComboBox = factory.makeComboBox(new WidgetConfiguration(200, 50,
            "combo-box-white", language), new ArrayList<>(Arrays.asList("Visual", "Text", "All")),
        "All");

    // Difficulty chooser setup
    ComboBox<String> difficultyComboBox = factory.makeComboBox(new WidgetConfiguration(170, 50,
        "combo-box-white", language), new ArrayList<>(Arrays.asList("Easy", "Medium",
        "Hard")), "Medium");

    HBox descriptionBox = factory.wrapInHBox(descriptionLabel, (int) layout.getWidth(), 15);

    // Create a container for blocks
    FlowPane blocksContainer = new FlowPane();
    blocksContainer.setHgap(30);
    blocksContainer.setVgap(30);
    blocksContainer.setAlignment(Pos.CENTER);
    blocksContainer.getStyleClass().add("flowpane");
    blocksContainer.setMinHeight(350);
    blocksContainer.setPadding(new Insets(20));

    // Initialize the ScrollPane here before using it in updateBlocksDisplay
    scrollPane = new ScrollPane(blocksContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.getStyleClass().add("flowpane");
    scrollPane.setPadding(new Insets(20));
    scrollPane.setMaxHeight(350);

    // Now, safely call updateBlocksDisplay
    categoryComboBox.setOnAction(event -> updateBlocksDisplay(categoryComboBox.getValue()));
    updateBlocksDisplay(categoryComboBox.getValue());

    // Button for changing grid size
    Button changeGridSizeButton = factory.makeButton(new WidgetConfiguration(
        170, 40, "ChangeGridSize", "white-button", language));
    changeGridSizeDialog(changeGridSizeButton);

    // Button for toggling remove mode
    Button removeButton = factory.makeButton(new WidgetConfiguration(
        170, 30, "RemoveBlock", "white-button", language));

    Button gpt = factory.makeButton(new WidgetConfiguration(170, 40,
        "GPTGenerate", "white-button", language));

    gpt.setOnAction(event -> showLoadingScreen());

    gpt.setOnMouseClicked(event -> {
      try {
        levelController.generateLevel();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });


    removeButton.setOnAction(e -> toggleRemoveMode(removeButton));

    List<Node> buttons = new ArrayList<>();
    buttons.add(changeGridSizeButton);
    buttons.add(removeButton);
    buttons.add(gpt);
    HBox buttonsHBox = factory.wrapInHBox(buttons, (int) layout.getWidth());
    buttonsHBox.setSpacing(20);

    // Scroll pane for blocks container
    scrollPane = new ScrollPane(blocksContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.getStyleClass().add("flowpane");

    scrollPane.setPadding(new Insets(20));
    scrollPane.setMaxHeight(350);

    Button saveJson = factory.makeButton(new WidgetConfiguration(
            200, 40, "SaveJson", "black-button", language));
    Button entryPoint = makeEntryPointButton();
    HBox jsonAndEntryPoint = factory.wrapInHBox(new ArrayList<>(Arrays.asList(saveJson, entryPoint)),
        (int)layout.getWidth());
    saveJson.setOnAction(event -> {
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
          LevelMetadata levelMetadata = new LevelMetadata(levelName, levelDescription, builderPane.gridHeight, builderPane.gridWidth);
          levelController.serializeLevel();

          // Optionally, show a success message
          Alert success = new Alert(Alert.AlertType.INFORMATION);
          success.setTitle("Success");
          success.setHeaderText(null);
          success.setContentText("JSON saved successfully!");
          success.showAndWait();
        }
      });
    });

    layout.getStyleClass().add("elements-background");

    HBox comboBoxes = factory.wrapInHBox(new ArrayList<>(Arrays.asList(difficultyComboBox,
        categoryComboBox)), (int) layout.getWidth());
    layout.getChildren().addAll(header, buttonsHBox, comboBoxes, descriptionBox,
        scrollPane, jsonAndEntryPoint);
    VBox.setVgrow(scrollPane, Priority.ALWAYS);
  }

  private Button makeEntryPointButton() {
    Button entryPoint = factory.makeButton(new WidgetConfiguration(200, 40,
        "Back", "black-button", language));
    entryPoint.setOnAction(e -> mainScene.goToEntryPoint());
    return entryPoint;
  }

  private void toggleRemoveMode(Button removeButton) {
    removeMode = !removeMode;
    if (removeMode) {
      factory.changeButtonLabel(removeButton, new WidgetConfiguration(
          "RemovingMode", language));
    } else {
      factory.changeButtonLabel(removeButton, new WidgetConfiguration(
          "RemoveBlock", language));
    }
    builderPane.setRemove(removeMode);
  }

  public VBox getLayout() {
    return layout;
  }

  private void updateBlocksDisplay(String category) {
    FlowPane blocksContainer = (FlowPane) scrollPane.getContent();
    blocksContainer.getChildren().clear();
    if (category.equals("All")) {
      blockLoader.loadBlocks(blocksContainer, IMAGE_FILE_PATH); // Load all blocks
    } else {
      blockLoader.loadBlocks(blocksContainer, IMAGE_FILE_PATH,
          category); // Load blocks based on category
    }
  }

  private boolean isValidSize(String value) {
    try {
      int size = Integer.parseInt(value);
      if (size <= 4 || size > 20) {
        return false; // Return false if size is out of range
      }
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private void changeGridSizeDialog(Button button) {
    button.setOnAction(event -> {
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
      Button confirmButton = (Button) dialog.getDialogPane().lookupButton(confirmButtonType);

      dialog.getDialogPane().setContent(grid);
      dialog.setResultConverter(dialogButton -> {
        if (dialogButton == confirmButtonType) {
          try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            if (isValidSize(String.valueOf(width)) && isValidSize(String.valueOf(height))) {
              return new Pair<>(width, height);
            } else {
              if (!isValidSize(String.valueOf(width))) {
                showErrorMessage(resourceBundle.getString("grid_size_error_message"));
              } else if (!isValidSize(String.valueOf(height))) {
                showErrorMessage(resourceBundle.getString("grid_size_error_message"));
              }
              return null;
            }
          } catch (NumberFormatException e) {
            showErrorMessage(resourceBundle.getString("invalid_number_error_message"));
            return null;
          }
        }
        return null;
      });

      dialog.showAndWait().ifPresent(pair -> {
          int width = pair.getKey();
          int height = pair.getValue();
          builderPane.updateGridSize(width, height);
          LevelMetadata levelMetadata = new LevelMetadata("Level Name", "Level Desc.", height,
                  width);
          levelController.resetLevel(levelMetadata);
      });
    });
  }
  private void showErrorMessage(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void showLoadingScreen() {
    // Create a progress bar for the loading screen
    ProgressBar progressBar = new ProgressBar();
    progressBar.setPrefWidth(200);

    // Create a stack pane to hold the progress bar
    VBox loadingPane = new VBox();
    loadingPane.getChildren().addAll(progressBar);
    loadingPane.setPadding(new Insets(20));
    loadingPane.setAlignment(Pos.CENTER);

    // Create a dialog window for the loading screen
    Stage loadingStage = new Stage();
    loadingStage.initModality(Modality.APPLICATION_MODAL);
    loadingStage.setTitle("Generating Level");
    loadingStage.setScene(new Scene(loadingPane, 250, 100));

    // Start a task for generating the level
    Task<Void> generateTask = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        // Simulate generation process
        for (int i = 0; i <= 100; i++) {
          updateProgress(i, 100);
          Thread.sleep(50);
        }
        return null;
      }
    };

    // Bind the progress of the task to the progress bar
    progressBar.progressProperty().bind(generateTask.progressProperty());

    // When the task completes, close the loading screen
    generateTask.setOnSucceeded(event -> {
      loadingStage.close();
      // You can add any additional actions after the task completes here
    });

    // Show the loading screen
    loadingStage.show();

    // Start the task
    new Thread(generateTask).start();
  }


}
