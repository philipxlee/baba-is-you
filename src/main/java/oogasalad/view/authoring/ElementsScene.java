package oogasalad.view.authoring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import oogasalad.view.authoring.MainScene;

public class ElementsScene {

  private ScrollPane scrollPane;
  private MainScene scene;
  private VBox layout;
  private VBox blocksContainer;

  public ElementsScene() {
    initializeElementsLayout();
  }

  private void initializeElementsLayout() {
    this.layout = new VBox(10); // Adjust spacing as needed
    this.blocksContainer = new VBox(10); // Space between images
    loadBlocksFromDirectory();
    this.scrollPane = new ScrollPane(blocksContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    Button exampleButton = new Button("Example Button");

    layout.getChildren().addAll(exampleButton, scrollPane);
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
      ImageView imageView = new ImageView(new Image(imageFile.toURI().toString(), 100, 100, true, true));
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
}