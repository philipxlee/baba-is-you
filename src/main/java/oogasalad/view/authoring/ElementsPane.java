package oogasalad.view.authoring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import oogasalad.controller.authoring.LevelController;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;

public class ElementsPane {

  private final String IMAGE_FILE_PATH = "src/main/resources/blocktypes/blocktypes.json";
  private final BuilderPane builderPane;
  private final BlockLoader blockLoader = new BlockLoader();
  private final WidgetFactory factory;
  private final LevelController levelController;
  private ScrollPane scrollPane;
  private VBox layout;
  private boolean removeMode = false;

  public ElementsPane(BuilderPane builderPane, LevelController levelController) {
    this.factory = new WidgetFactory();
    this.builderPane = builderPane;
    this.levelController = levelController;
    initializeElementsLayout();
  }

  private void initializeElementsLayout() {
    layout = new VBox(30);

    Text title = factory.generateHeader(new WidgetConfiguration("BIU"));
    HBox header = factory.wrapInHBox(title, (int) layout.getWidth());

    Text descriptionLabel = factory.generateLine(new WidgetConfiguration
        ("DragInstructions"));

    // Category selection setup
    ComboBox<String> categoryComboBox = new ComboBox<>();
    categoryComboBox.getItems().addAll("Visual", "Text", "Logic", "All"); // Example categories
    categoryComboBox.setValue("All"); // Default value

    HBox descriptionBox = factory.wrapInHBox(descriptionLabel, (int) layout.getWidth());

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
        200, 40, "ChangeGridSize","white-button"));
    changeGridSizeDialog(changeGridSizeButton);

    // Button for toggling remove mode
    Button removeButton = factory.makeButton(new WidgetConfiguration (
        200, 30, "RemoveBlock","white-button"));

    removeButton.setOnAction(event -> {
      removeMode = !removeMode;
      if (removeMode) {
        removeButton.setText("Removing mode: Press blocks to remove");
      } else {
        removeButton.setText("Remove block");
      }
      builderPane.setRemove(removeMode);
    });

    List<Node> buttons = new ArrayList<>();
    buttons.add(changeGridSizeButton);
    buttons.add(removeButton);
    HBox buttonsHBox = factory.wrapInHBox(buttons, (int) layout.getWidth());
    buttonsHBox.setSpacing(50);

    // Scroll pane for blocks container
    scrollPane = new ScrollPane(blocksContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.getStyleClass().add("flowpane");

    scrollPane.setPadding(new Insets(20));
    scrollPane.setMaxHeight(350);

    Button saveJson = factory.makeButton(new WidgetConfiguration(
        200, 40, "SaveJson","white-button"));
    saveJson.setOnAction(event -> {
      levelController.serializeLevel();
    });

    HBox jsonBox = factory.wrapInHBox(saveJson, (int) layout.getWidth());

    layout.getStyleClass().add("elements-background");
    layout.getChildren().addAll(header, categoryComboBox, buttonsHBox, descriptionBox, scrollPane, jsonBox);
    VBox.setVgrow(scrollPane, Priority.ALWAYS);
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

  public VBox getLayout() {
    return layout;
  }

  private void updateBlocksDisplay(String category) {
    FlowPane blocksContainer = (FlowPane) scrollPane.getContent();
    blocksContainer.getChildren().clear();
    if (category.equals("All")) {
      blockLoader.loadBlocks(blocksContainer, IMAGE_FILE_PATH); // Load all blocks
    } else {
      blockLoader.loadBlocks(blocksContainer, IMAGE_FILE_PATH, category); // Load blocks based on category
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
      confirmButton.setDisable(true);
      widthField.textProperty().addListener((observable, oldValue, newValue) -> {
        confirmButton.setDisable(
            newValue.trim().isEmpty() || heightField.getText().trim().isEmpty());
      });
      heightField.textProperty().addListener((observable, oldValue, newValue) -> {
        confirmButton.setDisable(
            newValue.trim().isEmpty() || widthField.getText().trim().isEmpty());
      });
      dialog.getDialogPane().setContent(grid);
      dialog.setResultConverter(dialogButton -> {
        if (dialogButton == confirmButtonType) {
          try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            return new Pair<>(width, height);
          } catch (NumberFormatException e) {
            return null;
          }
        }
        return null;
      });
      dialog.showAndWait().ifPresent(pair -> {
        if (pair != null) {
          int width = pair.getKey();
          int height = pair.getValue();
          builderPane.updateGridSize(width, height);
        }
      });
    });
  }
}
