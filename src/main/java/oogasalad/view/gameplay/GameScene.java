package oogasalad.view.gameplay;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.handlers.KeyHandler;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.shared.blockviews.AbstractBlockView;
import oogasalad.shared.observer.Observer;

public class GameScene implements Observer<Grid> {

  private int cellSize;
  private Grid gameGrid;
  private Group root;
  private KeyHandler keyHandler;
  private MainScene scene;
  private int width;
  private int height;

  public void initializeGameGrid(int width, int height, MainScene scene, SceneController sceneController) {
    this.width = width;
    this.height = height;
    createGrid();
    this.keyHandler = new KeyHandler(gameGrid, sceneController);
    this.root = new Group();
    this.scene = scene;
    this.gameGrid.addObserver(this);

    this.scene.getScene().setOnKeyPressed(event -> {
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

  public Group getGrid() {
    return this.root;
  }

  @Override
  public void update(Grid data) {
    renderGrid();
  }

  protected Pane setUpScreen() {
    StackPane gameScreen = new StackPane(root);
    gameScreen.setAlignment(Pos.CENTER);
    gameScreen.setPrefWidth(width);

    Pane pane = new Pane();
    pane.setPrefSize(width, height);
    pane.getChildren().add(gameScreen);
    return pane;
  }

  private void createGrid() {
    try {
      int n = 15;
      this.gameGrid = new Grid(n, n);
      int w = width / n;
      int h = height / n;
      cellSize = Math.min(w, h);
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
    double blockOffset = cellSize * 0.2; // Offset for displaying stacked blocks

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        List<AbstractBlock> blocks = grid[i][j];
        for (int k = 0; k < blocks.size(); k++) {
          try {
            AbstractBlock block = blocks.get(k);

            // Calculate the offset position for each block within the same cell
            double offsetX = j * cellSize + k * blockOffset;
            double offsetY = i * cellSize + k * blockOffset;

            // Ensure that the block does not exceed the boundaries of the cell
            offsetX = Math.min(offsetX, j * cellSize + cellSize - blockOffset);
            offsetY = Math.min(offsetY, i * cellSize + cellSize - blockOffset);

            AbstractBlockView obj = reflect(block);
            //Fix below to throw an exception or smth
            if (obj == null) {
              return;
            }
            ImageView visualObj = obj.getView();
            visualObj.setFitWidth(cellSize - k * blockOffset);
            visualObj.setFitHeight(cellSize - k * blockOffset);
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

  private AbstractBlockView reflect(AbstractBlock block) {
    try {
      String path = "/images/" + block.getBlockName() + ".png";
      String className = block.getBlockName() + "View";
      String source = "oogasalad.shared.blockviews.";
      if (className.contains("Visual")) {
        source += "visualblocksview.";
      }
      //do this for the rest
      else if (className.contains("Text")) {
        source += "textblocksview.";
      }
      //temporary, delete below when implementation is done
//          Rectangle rect = new Rectangle(offsetX, offsetY, CELL_SIZE - k * blockOffset, CELL_SIZE - k * blockOffset);
//          rect.setFill(getColorForBlock(block.getBlockName()));
//          root.getChildren().add(rect);
      Class<?> clazz = Class.forName(source + className);
      AbstractBlockView obj = (AbstractBlockView) clazz.getDeclaredConstructor(String.class)
          .newInstance(path);
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
