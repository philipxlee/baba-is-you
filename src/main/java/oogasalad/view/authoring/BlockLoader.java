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

  /**
   * Load and display all blocks without category filtering.
   *
   * @param blocksContainer The container to add the block views to.
   * @param jsonFileName The JSON file name where blocks are defined.
   */
  public void loadBlocks(FlowPane blocksContainer, String jsonFileName) {
    try {
      List<BlockData> blocksData = blockManager.loadBlocksFromJsonFile(jsonFileName, null);
      displayBlocks(blocksContainer, blocksData);
    } catch (IOException e) {
    }
  }

  /**
   * Load and display blocks filtered by category.
   *
   * @param blocksContainer The container to add the block views to.
   * @param jsonFileName The JSON file name where blocks are defined.
   * @param category The category to filter the blocks by.
   */
  public void loadBlocks(FlowPane blocksContainer, String jsonFileName, String category) {
    try {
      List<BlockData> blocksData = blockManager.loadBlocksFromJsonFile(jsonFileName, category);
      displayBlocks(blocksContainer, blocksData);
    } catch (IOException e) {
    }
  }

  /**
   * Display blocks in the given container.
   *
   * @param blocksContainer The container where blocks are to be added.
   * @param blocksData List of BlockData containing block information.
   */
  private void displayBlocks(FlowPane blocksContainer, List<BlockData> blocksData) {
    blocksContainer.getChildren().clear();
    for (BlockData block : blocksData) {
      Image blockImage = new Image(block.getImagePath());
      ImageView imageView = new ImageView(blockImage);
      imageView.setFitWidth(60);
      imageView.setFitHeight(60);
      imageView.setPreserveRatio(true);
      blocksContainer.getChildren().add(imageView);
      makeDraggable(imageView, block.getName());
    }
  }

  /**
   * Set up drag-and-drop functionality for an ImageView.
   * @param imageView The ImageView to make draggable.
   * @param blockType The type of block, used as the identifier for the drag content.
   */
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