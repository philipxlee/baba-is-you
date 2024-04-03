package oogasalad.view.authoring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import oogasalad.view.authoring.MainScene;

public class ElementsScene {

  private ScrollPane scrollPane;
  private VBox blocksContainer;
  private MainScene scene;

  public ElementsScene() {
    this.blocksContainer = new VBox(10); // Adjust spacing as needed
    this.scrollPane = new ScrollPane(blocksContainer);
    scrollPane.setFitToWidth(true); // Ensure the scroll pane fits the width of the blocks container
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
  }

  public void initializeElementsScene(int width, int height, MainScene scene) {
    this.scene = scene;
    loadBlocksFromDirectory();
  }

  protected Pane setUpScreen() {
    Pane pane = new Pane();
    pane.getChildren().add(scrollPane);
    return pane;
  }

  public ScrollPane getScrollPane() {
    return scrollPane;
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
    // Show a directory chooser dialog for the user to select the directory containing block images
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
      event.consume();
    });
  }
}