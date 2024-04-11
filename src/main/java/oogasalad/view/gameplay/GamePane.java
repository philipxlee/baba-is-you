package oogasalad.view.gameplay;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import oogasalad.controller.gameplay.GameGridController;
import oogasalad.controller.gameplay.GameStateController;
import oogasalad.controller.gameplay.KeyHandlerController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.shared.blockview.BlockViewFactory;
import oogasalad.shared.observer.Observer;

/**
 * Class that encapsulates the grid interactions from the model and displays them. Uses the Observer
 * and Controller paradigms.
 */
public class GamePane implements Observer<Grid> {

  private int cellSize;
  private Group root;
  private KeyHandlerController keyHandlerController;
  private SceneController sceneController;
  private GameGridController gridController;
  private MainScene scene;
  private int width;
  private int height;
  private BlockViewFactory blockFactory;

  public void initializeGameGrid(int width, int height, MainScene scene,
      SceneController sceneController) {
    try {
      this.blockFactory = new BlockViewFactory("/blocktypes/blocktypes.json");
      this.width = width;
      this.height = height;
      this.root = new Group();
      this.scene = scene;
      this.keyHandlerController = new KeyHandlerController(
          new GameStateController(sceneController));
      this.gridController = new GameGridController(this, keyHandlerController);

      handleKeyPresses(scene);

    } catch (Exception e) {
      gridController.showError("ERROR", e.getClass().getName());
    }
    renderGrid(); // Initial grid rendering
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
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

  private void renderGrid() {
    root.getChildren().clear();
    List<AbstractBlock>[][] grid = gridController.getGameGrid().getGrid();
    calculateCellSize(grid.length, grid[0].length);
    double blockOffset = 0; // Offset for displaying stacked blocks

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

            ImageView visualObj = blockFactory.createBlockView(block.getBlockName());

            if (visualObj == null) {
              gridController.showError("ERROR", "ImageView in AbstractBlockView is null");
            }
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

  private void calculateCellSize(int r, int c) {
    int smallerDimension = Math.min(r, c);
    this.cellSize = Math.min(width/smallerDimension, height/smallerDimension);
  }

  private void handleKeyPresses(MainScene scene) {
    // For grid movement
    this.scene.getScene().addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode().isArrowKey()) {
        gridController.sendPlayToModel(event.getCode());
        renderGrid(); // Render grid
        gridController.resetBlocks(); // Reset all blocks
        scene.getInteractionPane().updateKeyPress(event.getCode());
        event.consume();
      }
    });

    // For key press visualizer
    this.scene.getScene().addEventFilter(javafx.scene.input.KeyEvent.KEY_RELEASED, event -> {
      if (event.getCode().isArrowKey()) {
        scene.getInteractionPane().updateKeyRelease(event.getCode());
        event.consume();
      }
    });
  }

//  private AbstractBlockView reflect(String blockType) {
//    try {
//      String path = "/images/" + blockType + ".png";
//      String className = blockType + "View";
//      String source = "oogasalad.shared.blockviews.";
//      if (className.contains("Visual")) {
//        source += "visualblocksview.";
//      }
//      //do this for the rest
//      else if (className.contains("Text")) {
//        source += "textblocksview.";
//      }
//      Class<?> clazz = Class.forName(source + className);
//      AbstractBlockView obj = (AbstractBlockView) clazz.getDeclaredConstructor(String.class)
//          .newInstance(path);
//      return obj;
//    } catch (Exception e) {
//      e.printStackTrace();
//      return null;
//    }
//  }
}
