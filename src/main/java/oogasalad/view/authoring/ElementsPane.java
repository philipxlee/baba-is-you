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
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oogasalad.controller.authoring.LevelController;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import oogasalad.view.authoring.blockDisplay.BlockLoader;
import oogasalad.view.authoring.jsonOps.JsonLoader;
import oogasalad.view.authoring.jsonOps.JsonSaver;


public class ElementsPane {

  private final String IMAGE_FILE_PATH = "src/main/resources/blocktypes/blocktypes.json";
  private final JsonSaver jsonSaver;
  private final JsonLoader jsonLoader;
  private final ResourceBundle resourceBundle = ResourceBundle.getBundle("error_bundle/authoring_errors");
  private final BuilderPane builderPane;
  private final BlockLoader blockLoader = new BlockLoader();
  private final WidgetFactory factory;
  private final LevelController levelController;
  private ScrollPane scrollPane;
  private VBox layout;
  private boolean removeMode = false;
  private String language;
  private String difficulty;
  private final GridSizeChanger gridSizeChanger;

  public ElementsPane(BuilderPane builderPane, LevelController levelController, String language) {
    this.gridSizeChanger = new GridSizeChanger(builderPane);
    this.factory = new WidgetFactory();
    this.builderPane = builderPane;
    this.levelController = levelController;
    this.jsonSaver = new JsonSaver(levelController, builderPane);
    this.jsonLoader = new JsonLoader(levelController, builderPane);
    this.language = language;
    initializeElementsLayout();

  }

  private void initializeElementsLayout() {
    layout = new VBox(15);

    Text title = factory.generateHeader(new WidgetConfiguration("BIU", language));
    Text subtitle = factory.generateSubHeader(new WidgetConfiguration(
        "AuthEnv", language));
    VBox header = factory.wrapInVBox(new ArrayList<>(Arrays.asList(title, subtitle)),
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

    difficultyComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
      if (newValue != null) {
        difficulty = newValue;
      }
    });

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

    Button changeGridSizeButton = factory.makeButton(new WidgetConfiguration(
        170, 40, "ChangeGridSize", "white-button", language));
    changeGridSizeButton.setOnAction(event -> gridSizeChanger.changeGridSize());

    // Button for toggling remove mode
    Button removeButton = factory.makeButton(new WidgetConfiguration(
        170, 30, "RemoveBlock", "white-button", language));
    removeButton.setOnAction(event -> toggleRemoveMode(removeButton));

    Button gptButton = factory.makeButton(new WidgetConfiguration(170, 40,
        "GPTGenerate", "white-button", language));

    gptButton.setOnAction(event -> showLoadingScreen());

    gptButton.setOnMouseClicked(event -> {
      try {
        levelController.generateLevel();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    List<Node> buttons = new ArrayList<>();
    buttons.add(changeGridSizeButton);
    buttons.add(removeButton);
    buttons.add(gptButton);
    HBox buttonsHBox = factory.wrapInHBox(buttons, (int) layout.getWidth());
    buttonsHBox.setSpacing(20);

    // Scroll pane for blocks container
    scrollPane = new ScrollPane(blocksContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.getStyleClass().add("flowpane");

    scrollPane.setPadding(new Insets(20));
    scrollPane.setMaxHeight(350);


    Button saveJsonButton = factory.makeButton(new WidgetConfiguration(
        200, 40, "SaveJson", "black-button", language));
    saveJsonButton.setOnAction(event -> jsonSaver.saveJson());

    // Create the Load Level button
    Button loadLevelButton = factory.makeButton(new WidgetConfiguration(
        200, 40, "LoadLevel", "black-button", language));
    loadLevelButton.setOnAction(event -> jsonLoader.loadLevel());

    List<Node> SLbuttons = new ArrayList<>();
    SLbuttons.add(saveJsonButton);
    SLbuttons.add(loadLevelButton);
    HBox SLbuttonsHBox = factory.wrapInHBox(SLbuttons, (int) layout.getWidth());
    SLbuttonsHBox.setSpacing(15);

    layout.getStyleClass().add("elements-background");

    HBox comboBoxes = factory.wrapInHBox(new ArrayList<>(Arrays.asList(difficultyComboBox,
        categoryComboBox)), (int) layout.getWidth());
    layout.getChildren().addAll(header, buttonsHBox, comboBoxes, descriptionBox,
        scrollPane, SLbuttonsHBox);
    VBox.setVgrow(scrollPane, Priority.ALWAYS);


    TooltipManager.setTooltips(categoryComboBox, difficultyComboBox, changeGridSizeButton, removeButton, gptButton, saveJsonButton, loadLevelButton);
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

  private void toggleRemoveMode(Button removeButton) {
    removeMode = !removeMode;
    if (removeMode) {
      removeButton.setText("Removing mode: Press blocks to remove");
    } else {
      removeButton.setText("Remove block");
    }
    builderPane.setRemove(removeMode);
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
    Task<Void> generateTask = new Task<>() {
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
