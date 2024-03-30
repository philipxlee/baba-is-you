package oogasalad.controller.gameplay;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import oogasalad.shared.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.handlers.KeyHandler;

public class MainController extends Application {

  private static final int CELL_SIZE = 50;
  private Grid gameGrid;
  private Group root;
  private KeyHandler keyHandler;

  @Override
  public void start(Stage stage) throws Exception {
    gameGrid = new Grid(8, 8);
    keyHandler = new KeyHandler(gameGrid);
    Group root = new Group();
    Scene scene = new Scene(root, 400, 400);
    this.root = root;
    renderGrid(root);

    // NOTE: This ORDER of event handling is important and INTENTIONAL
    scene.setOnKeyPressed(event -> {
      gameGrid.checkForRules(); // FIRST, SCAN GRID FOR RULES
      keyHandler.handleKeyPress(event.getCode()); // THEN HANDLE USER KEY PRESS
      renderGrid(root);  // THEN RENDER THE NEW GRID
      resetAllBlocks(); // THEN RESET ALL BLOCKS
    });

    stage.setScene(scene);
    stage.setTitle("Game Grid");
    stage.show();
  }

  private void resetAllBlocks() {
    // Reset all behaviors
    AbstractBlock[][] grid = gameGrid.getGrid();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        AbstractBlock block = grid[i][j];
        if (!block.isTextBlock()) {
          block.resetAllBehaviors();
        }
      }
    }
  }

  private void renderGrid(Group root) {
    root.getChildren().clear();
    AbstractBlock[][] gameActualGrid = gameGrid.getGrid();
    for (int i = 0; i < gameActualGrid.length; i++) {
      for (int j = 0; j < gameActualGrid[i].length; j++) {
        Rectangle rect = new Rectangle(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        String blockType = gameActualGrid[i][j].getBlockName();
        rect.setFill(getColorForBlock(blockType));
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