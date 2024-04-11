package oogasalad.view.authoring;

import java.io.IOException;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;

public class BlockLoader {

  private final BlockNameManager blockManager = new BlockNameManager();
  private final String IMAGE_FILE_PATH = "src/main/resources/blocktypes/blocktypes.json";

  protected void loadBlocks(FlowPane blocksContainer, String jsonFileName) {
    try {
      // Load block names from the specified JSON file
      List<String> blockNames = blockManager.loadBlockNamesFromJsonFile(jsonFileName);

      if (blockNames != null) {
        for (String blockName : blockNames) {
          // Assuming addBlockViewToRoot can handle blockName directly without needing the directory
          addBlockViewToRoot(blockName, blocksContainer); // Adjust the method signature as needed
        }
      } else {
        System.err.println("Failed to load blocks from JSON.");
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Error loading blocks from JSON.");
    }
  }

  /**
   * Dummy method to mimic adding blocks to the UI. This should be replaced with the actual
   * implementation that adds block images to the FlowPane.
   *
   * @param blockName       The name of the block to add.
   * @param blocksContainer The container to add the block view to.
   */
  private void addBlockViewToRoot(String blockName, FlowPane blocksContainer) {
    try {
      // Get the image paths for each block name from BlockNameManager
      List<String> imagePaths = blockManager.loadImagePathsFromJsonFile(IMAGE_FILE_PATH);

      // Find the index of the blockName in the list of block names
      int blockIndex = blockManager.loadBlockNamesFromJsonFile(IMAGE_FILE_PATH).indexOf(blockName);

      // Check if the blockName is found and its corresponding imagePath exists
      if (blockIndex != -1 && blockIndex < imagePaths.size()) {
        String imagePath = imagePaths.get(blockIndex);

        // Create ImageView with the retrieved imagePath
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        imageView.setPreserveRatio(true);

        // Add the ImageView to the blocksContainer
        blocksContainer.getChildren().add(imageView);

        // Call makeDraggable to make the ImageView draggable
        makeDraggable(imageView, blockName);
      } else {
        // Handle the case where the blockName or its corresponding imagePath is not found
        System.err.println("Block not found or image path not available.");
      }
    } catch (IOException e) {
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

