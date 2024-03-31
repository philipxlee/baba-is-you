package oogasalad.view.gameplay;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.handlers.KeyHandler;
import oogasalad.shared.scene.Scene;

public class MainScene implements Scene {

  private static final int CELL_SIZE = 50;
  private Grid gameGrid;
  private Group root;
  private KeyHandler keyHandler;
  private javafx.scene.Scene scene;

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
    AbstractBlock[][] grid = gameGrid.getGrid();
    for (AbstractBlock[] blocks : grid) {
      for (AbstractBlock block : blocks) {
        if (!block.isTextBlock()) {
          block.resetAllBehaviors();
        }
      }
    }
  }

  private void renderGrid() {
    root.getChildren().clear();
    AbstractBlock[][] grid = gameGrid.getGrid();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        Rectangle rect = new Rectangle(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        rect.setFill(getColorForBlock(grid[i][j].getBlockName()));
        root.getChildren().add(rect);
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
      default -> Color.BLACK;
    };
  }
}
