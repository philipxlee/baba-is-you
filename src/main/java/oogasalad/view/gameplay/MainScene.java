package oogasalad.view.gameplay;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.handlers.KeyHandler;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.viewblocks.AbstractBlockView;

public class MainScene implements Scene {

  private static final int CELL_SIZE = 50;
  private Grid gameGrid;
  private Group root;
  private KeyHandler keyHandler;
  private javafx.scene.Scene scene;
  private static final String IMAGE_DIRECTORY = "oogasalad.shared.resources.images.";

  @Override
  public void initializeScene(int width, int height) {
    createGrid();
    this.keyHandler = new KeyHandler(gameGrid);
    this.root = new Group();
    this.scene = new javafx.scene.Scene(root, width, height);

    scene.setOnKeyPressed(event -> {
      try {
        gameGrid.checkForRules(); // Check for rules
        keyHandler.handleKeyPress(event.getCode()); // Handle key press
        renderGrid(); // Render grid
        resetAllBlocks(); // Reset all blocks
      } catch (Exception e) {
        showErrorDialog(e.getClass().getName());
      }
    });

    renderGrid(); // Initial grid rendering
  }

  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }

  private void createGrid() {
    try {
      this.gameGrid = new Grid(8, 8);
    } catch (InvalidBlockName e) {
      showErrorDialog(e.getMessage());
    }
  }

  private void showErrorDialog(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void resetAllBlocks() {
    List<AbstractBlock>[][] grid = gameGrid.getGrid();
    for (List<AbstractBlock>[] blocksRow : grid) {
      for (List<AbstractBlock> cell : blocksRow) {
        for (AbstractBlock block : cell) {
          if (!block.isTextBlock()) {
            block.resetAllBehaviors();
          }
        }
      }
    }
  }


  private void renderGrid() {
    root.getChildren().clear();
    List<AbstractBlock>[][] grid = gameGrid.getGrid();
    double blockOffset = CELL_SIZE * 0.2; // Offset for displaying stacked blocks

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        List<AbstractBlock> blocks = grid[i][j];
        for (int k = 0; k < blocks.size(); k++) {
          try {
            AbstractBlock block = blocks.get(k);

            // Calculate the offset position for each block within the same cell
            double offsetX = j * CELL_SIZE + k * blockOffset;
            double offsetY = i * CELL_SIZE + k * blockOffset;

            // Ensure that the block does not exceed the boundaries of the cell
            offsetX = Math.min(offsetX, j * CELL_SIZE + CELL_SIZE - blockOffset);
            offsetY = Math.min(offsetY, i * CELL_SIZE + CELL_SIZE - blockOffset);

            String path = "/" + block.getBlockName() + ".png";
            String className = block.getBlockName() + "View";
            String source = "oogasalad.shared.viewblocks.";
            if (className.contains("Visual"))
              source += "visualblocksview.";
              //do this for the rest
            else if (className.contains("Text"))
              source += "textblocksview.";
            //temporary, delete below when implementation is done
//          Rectangle rect = new Rectangle(offsetX, offsetY, CELL_SIZE - k * blockOffset, CELL_SIZE - k * blockOffset);
//          rect.setFill(getColorForBlock(block.getBlockName()));
//          root.getChildren().add(rect);
            Class<?> clazz = Class.forName(source + className);
            AbstractBlockView obj = (AbstractBlockView) clazz.getDeclaredConstructor(String.class)
                .newInstance(path);
            ImageView visualObj = obj.getView();
            visualObj.setFitWidth(CELL_SIZE- k * blockOffset);
            visualObj.setFitHeight(CELL_SIZE- k * blockOffset);
            visualObj.setPreserveRatio(true);
            visualObj.setX(offsetX);
            visualObj.setY(offsetY);
            root.getChildren().add(visualObj);

          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }


  private Color getColorForBlock(String blockType) {
    return switch (blockType) {
      case "BabaVisualBlock" -> Color.WHITE;
      case "RockVisualBlock" -> Color.ORANGE;
      case "BabaTextBlock" -> Color.RED;
      case "IsTextBlock" -> Color.YELLOW;
      case "YouTextBlock" -> Color.GREEN;
      case "RockTextBlock" -> Color.BLUE;
      case "PushTextBlock" -> Color.GRAY;
      default -> Color.BLACK;
    };
  }

}
