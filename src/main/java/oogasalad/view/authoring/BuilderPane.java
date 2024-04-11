package oogasalad.view.authoring;

import java.util.Optional;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import oogasalad.controller.authoring.LevelController;
import oogasalad.model.authoring.block.BlockFactory;
import oogasalad.shared.blockview.BlockViewFactory;


public class BuilderPane {

  private final int GRID_MARGIN = 10;
  private final String BLOCK_CONFIG_FILE_PATH = "/blocktypes/blocktypes.json";
  private Pane root; // Your root node for the builder scene
  private double cellSize; // Set the cell size
  private GridPane gridPane;
  private int gridWidth;
  private BlockViewFactory blockViewFactory;
  private BlockFactory blockFactory;
  private LevelController levelController;
  private boolean removeMode;
  private int gridHeight;

  public BuilderPane() {
    try {
      this.blockViewFactory = new BlockViewFactory(BLOCK_CONFIG_FILE_PATH);
      this.blockFactory = new BlockFactory(BLOCK_CONFIG_FILE_PATH);
      this.levelController = new LevelController(blockFactory);
    } catch (Exception e) {
      e.printStackTrace();
    }
    initializeBuilderScene();
  }

  public void initializeBuilderScene() {
    this.root = new Pane();
    this.gridPane = new GridPane();
    this.gridWidth = 10;
    this.gridHeight = 10;

    // Listen for size changes on root to re-setup the grid
    root.widthProperty().addListener((obs, oldVal, newVal) -> setUpGrid());
    root.heightProperty().addListener((obs, oldVal, newVal) -> setUpGrid());

    // Initialize Level
    levelController.initializeLevel("", "", gridHeight, gridWidth);

    setUpGrid();
    setUpDropHandling();
  }

  private void setUpGrid() {
    gridPane.getChildren().clear(); // Clear the existing grid

    // Adjust the maximum width and height available for the grid, accounting for margins
    double availableWidth = root.getWidth() - 2 * GRID_MARGIN - 2 * gridWidth;
    double availableHeight = root.getHeight() - 2 * GRID_MARGIN - 2 * gridHeight;

    // Calculate cell size based on the available space and the grid dimensions
    this.cellSize = Math.min((availableWidth) / gridWidth, (availableHeight) / gridHeight);

    // Calculate total size of the grid
    double totalGridWidth = gridWidth * (cellSize);
    double totalGridHeight = gridHeight * (cellSize);

    // Calculate the starting positions to center the grid within the root pane, considering margins
    double layoutX = (root.getWidth() - totalGridWidth) / 2;
    double layoutY = (root.getHeight() - totalGridHeight) / 2;

    // Apply the calculated layout positions to the gridPane
    gridPane.setLayoutX(layoutX);
    gridPane.setLayoutY(layoutY);

    for (int i = 0; i < gridWidth; i++) {
      for (int j = 0; j < gridHeight; j++) {
        Pane cell = new Pane();
        cell.setPrefSize(cellSize, cellSize);
        cell.getStyleClass().add("grid");
        gridPane.add(cell, i, j);
      }
    }

    // Ensure the gridPane is added to the root if not already present
    if (!root.getChildren().contains(gridPane)) {
      root.getChildren().add(gridPane);
    }
  }


  public void updateGridSize(int width, int height) {
    // Display a confirmation dialog
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText("Warning: Existing game state will be deleted");
    alert.setContentText("Would you like to proceed?");

    // Add buttons for user selection
    ButtonType buttonTypeYes = new ButtonType("Yes");
    ButtonType buttonTypeNo = new ButtonType("No");

    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

    Optional<ButtonType> result = alert.showAndWait();

    // If user confirms, clear existing cells and blocks and set up the grid with the new size
    if (result.isPresent() && result.get() == buttonTypeYes) {
      this.gridWidth = width;
      this.gridHeight = height;

      // Clear existing cells
      this.gridPane.getChildren().clear();
      this.root.getChildren().clear();

      // Re-setup the grid with the new size
      setUpGrid();
    }
  }

  private void setUpDropHandling() {
    gridPane.setOnDragOver(event -> {
      if (event.getGestureSource() != gridPane && event.getDragboard().hasString()) {
        event.acceptTransferModes(TransferMode.MOVE);
      }
      event.consume();
    });

    gridPane.setOnDragDropped(event -> {
      Dragboard db = event.getDragboard();
      boolean success = false;
      if (db.hasString()) {
        String blockType = db.getString();
        ImageView blockView = createBlockView(blockType);
        if (blockView != null) {
          Point2D cellCoords = getCellCoordinates(event.getX(), event.getY());
          Point2D cellIndices = getCellIndices(event.getX(), event.getY());
          if (cellCoords != null && cellIndices != null) {
            blockView.setFitWidth(cellSize);
            blockView.setFitHeight(cellSize);
            blockView.setLayoutX(cellCoords.getX());
            blockView.setLayoutY(cellCoords.getY());
            root.getChildren().add(blockView);
            try {
              // x corresponds to column, y corresponds to row
              levelController.setCell((int) cellIndices.getY(), (int) cellIndices.getX(),
                  blockType);
            } catch (Exception e) {
              e.printStackTrace();
            }
            setRemoveModeEventHandlers(); //allows removing after adding blocks
            success = true;
          }
        }
      }
      event.setDropCompleted(success);
      event.consume();
    });
  }

  private Point2D getCellIndices(double x, double y) {
    for (Node node : gridPane.getChildren()) {
      if (node instanceof Pane cell) {
        Bounds boundsInParent = cell.getBoundsInParent();
        if (boundsInParent.contains(x, y)) {
          // Assuming you know the row and column due to how you added the cell
          Integer colIndex = GridPane.getColumnIndex(cell);
          Integer rowIndex = GridPane.getRowIndex(cell);

          // These can be null if not set; defaulting to 0
          colIndex = colIndex == null ? 0 : colIndex;
          rowIndex = rowIndex == null ? 0 : rowIndex;

          return new Point2D(colIndex, rowIndex);
        }
      }
    }
    return null; // Position does not fall within any cell
  }


  private Point2D getCellCoordinates(double x, double y) {
    for (Node node : gridPane.getChildren()) {
      if (node instanceof Pane cell) {
        Bounds boundsInParent = cell.getBoundsInParent();
        if (boundsInParent.contains(x, y)) {
          // Calculate the cell coordinates based on the layout coordinates of the cell
          double cellX = cell.getLayoutX() + gridPane.getLayoutX();
          double cellY = cell.getLayoutY() + gridPane.getLayoutY();
          return new Point2D(cellX, cellY);
        }
      }
    }
    return null; // Coordinates (x, y) do not fall within any cell
  }


  private ImageView createBlockView(String blockType) {
    try {
      return blockViewFactory.createBlockView(blockType);
    } catch (Exception e) {
      System.err.println(
          "Failed to create block view for type: " + blockType + " with error: " + e.getMessage());
      return null; // Or handle this case as needed.
    }
  }


  public Pane getRoot() {
    return root;
  }

  private void setRemoveModeEventHandlers() {
    root.getChildren().forEach(node -> {
      if (node instanceof ImageView) {
        node.setOnMouseClicked(event -> {
          if (removeMode) {
            root.getChildren().remove(node);
          }
        });
      }
    });
  }

  public void setRemove(boolean remove_bool) {
    removeMode = remove_bool;
    setRemoveModeEventHandlers();
  }
}
