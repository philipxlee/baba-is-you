package oogasalad.view.authoring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.util.Pair;
import oogasalad.view.authoring.MainScene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class ElementsScene {

  private ScrollPane scrollPane;
  private MainScene scene;
  private VBox layout;
  private VBox blocksContainer;
  private BuilderScene builderScene;

  public ElementsScene(BuilderScene builderScene) {
    this.builderScene = builderScene;
    initializeElementsLayout();
  }

  private void initializeElementsLayout() {
    this.layout = new VBox(10); // Adjust spacing as needed
    this.blocksContainer = new VBox(10); // Space between images
    loadBlocksFromDirectory();
    Button changeGridSizeButton = new Button("Change Grid Size");
    changeGridSizeDialog(changeGridSizeButton);
    this.scrollPane = new ScrollPane(blocksContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    layout.getChildren().addAll(changeGridSizeButton, scrollPane);
    VBox.setVgrow(scrollPane, Priority.ALWAYS); // Allow the scroll pane to grow and fill space
  }

  // Method to access the complete scene layout
  public VBox getLayout() {
    return layout;
  }

  private List<String> getBlockNamesFromDirectory(File directory) {
    List<String> blockNames = new ArrayList<>();
    File[] imageFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
    if (imageFiles != null) {
      for (File imageFile : imageFiles) {
        String blockName = imageFile.getName().replace(".png", "");
        blockNames.add(blockName);
      }
    }
    return blockNames;
  }

  private void loadBlocksFromDirectory() {
    String directoryPath = "src/main/resources/images";

    // Create a File object for the directory
    File selectedDirectory = new File(directoryPath);

    // Check if the directory exists
    if (selectedDirectory.exists() && selectedDirectory.isDirectory()) {
      List<String> blockNames = getBlockNamesFromDirectory(selectedDirectory);
      for (String blockName : blockNames) {
        addBlockViewToRoot(selectedDirectory, blockName);
      }
    } else {
      System.err.println("Directory not found or is not a valid directory.");
    }
  }

  private void addBlockViewToRoot(File directory, String blockName) {
    try {
      File imageFile = new File(directory, blockName + ".png");
      ImageView imageView = new ImageView(
          new Image(imageFile.toURI().toString(), 100, 100, true, true));
      imageView.setFitWidth(100); // Set the image width to 100 for a square shape
      imageView.setFitHeight(100); // Set the image height to 100 for a square shape
      imageView.setPreserveRatio(true);
      blocksContainer.getChildren().add(imageView);
      makeDraggable(imageView, blockName);
    } catch (Exception e) {
      e.printStackTrace(); // Handle exception appropriately
    }
  }

  private void makeDraggable(ImageView imageView, String blockType) {
    imageView.setOnDragDetected(event -> {
      Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
      ClipboardContent content = new ClipboardContent();
      content.putString(blockType); // Use blockType as the identifier for the dragged object
      db.setContent(content);
      db.setDragView(imageView.snapshot(null, null));
      event.consume();
    });
  }

  private void changeGridSizeDialog(Button button) {
    button.setOnAction(event -> {
      // Create the custom dialog.
      Dialog<Pair<Integer, Integer>> dialog = new Dialog<>();
      dialog.setTitle("Change Grid Size");

      // Set the button types.
      ButtonType confirmButtonType = ButtonType.OK;
      dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

      // Create the width and height labels and fields.
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

      // Enable/Disable confirm button depending on whether a width and height were entered.
      Button confirmButton = (Button) dialog.getDialogPane().lookupButton(confirmButtonType);
      confirmButton.setDisable(true);

      // Do some validation (using the Java 8 lambda syntax).
      widthField.textProperty().addListener((observable, oldValue, newValue) -> {
        confirmButton.setDisable(newValue.trim().isEmpty() || heightField.getText().trim().isEmpty());
      });
      heightField.textProperty().addListener((observable, oldValue, newValue) -> {
        confirmButton.setDisable(newValue.trim().isEmpty() || widthField.getText().trim().isEmpty());
      });

      dialog.getDialogPane().setContent(grid);

      // Convert the result to a pair of width and height when the confirm button is clicked.
      dialog.setResultConverter(dialogButton -> {
        if (dialogButton == confirmButtonType) {
          try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            return new Pair<>(width, height);
          } catch (NumberFormatException e) {
            // Handle the case where one or both of the inputs are not valid integers
            return null;
          }
        }
        return null;
      });

      dialog.showAndWait().ifPresent(pair -> {
        int width = pair.getKey();
        int height = pair.getValue();// Assuming width and height are the same for a grid
        // Assuming builderScene is a reference to your BuilderScene instance
        builderScene.updateGridSize(width, height);
      });
    });
  }

}