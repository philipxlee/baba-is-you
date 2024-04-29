package oogasalad.view.authoring;

import java.util.Optional;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import oogasalad.controller.authoring.LevelController;
import oogasalad.model.authoring.level.Grid;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.shared.blockview.BlockViewFactory;
import oogasalad.shared.widgetfactory.WidgetFactory;

import java.util.ResourceBundle;

/**
 * Represents the pane where users can build and edit levels within the authoring environment.
 */
public class BuilderPane {

  protected Pane root;
  protected GridPane gridPane;
  private final ResourceBundle messages = ResourceBundle.getBundle("error_bundle/authoring_errors");

  public int gridWidth;
  protected boolean removeMode;
  public int gridHeight;
  private double cellSize;
  private BlockViewFactory blockViewFactory;
  private final LevelController levelController;

  /**
   * Constructor for BuilderPane which initializes the UI and block factory.
   * @param levelController The controller responsible for managing level data.
   * @param language The language setting for localization, not used directly but useful for extension.
   */
  public BuilderPane(LevelController levelController, String language) {
    WidgetFactory factory = new WidgetFactory();
    this.levelController = levelController;
    try {
      String BLOCK_CONFIG_FILE_PATH = "/blocktypes/blocktypes.json";
      this.blockViewFactory = new BlockViewFactory(BLOCK_CONFIG_FILE_PATH);
    } catch (Exception e) {
      e.printStackTrace();
    }
    initializeBuilderScene();
  }

  /**
   * Initializes the visual components of the builder scene.
   */
  public void initializeBuilderScene() {
    this.root = new Pane();
    VBox container = new VBox(10);
    container.setMinWidth(root.getWidth());
    container.setAlignment(Pos.CENTER);
    this.gridPane = new GridPane();
    LevelMetadata levelMetadata = levelController.getLevelMetadata();
    this.gridWidth = levelMetadata.cols();
    this.gridHeight = levelMetadata.rows();
    root.widthProperty().addListener((obs, oldVal, newVal) -> setUpGrid());
    root.heightProperty().addListener((obs, oldVal, newVal) -> setUpGrid());
    setUpGrid();
    setUpDropHandling();
  }

  /**
   * Displays a confirmation dialog when clearing the grid or making significant modifications.
   */
  protected void alertGrid() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(messages.getString("grid.confirmationTitle"));
    alert.setHeaderText(messages.getString("grid.warning"));
    alert.setContentText(messages.getString("grid.changeConfirmation"));
    ButtonType buttonTypeYes = new ButtonType(messages.getString("yes"));
    ButtonType buttonTypeNo = new ButtonType(messages.getString("no"));
    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == buttonTypeYes) {
      setUpGrid();
    }
  }

  /**
   * Sets up the grid based on the current metadata settings, adjusting for the size of the root pane.
   */
  protected void setUpGrid() {
    gridPane.getChildren().clear();
    root.getChildren().clear();
    double availableWidth = root.getWidth() - 20 - 2 * gridWidth;
    double availableHeight = root.getHeight() - 20 - 2 * gridHeight;
    cellSize = Math.min(availableWidth / gridWidth, availableHeight / gridHeight);
    double totalGridWidth = gridWidth * cellSize;
    double totalGridHeight = gridHeight * cellSize;
    double layoutX = (root.getWidth() - totalGridWidth) / 2;
    double layoutY = (root.getHeight() - totalGridHeight) / 2;
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
    root.getChildren().add(gridPane);
  }

  /**
   * Sets up handling for drag-and-drop events on the grid, enabling block placement.
   */
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
              levelController.addBlockToCell((int) cellIndices.getY(), (int) cellIndices.getX(), blockType);
            } catch (Exception e) {
              e.printStackTrace();
            }
            setRemoveModeEventHandlers();
            success = true;
          }
        }
      }
      event.setDropCompleted(success);
      event.consume();
    });
  }

  /**
   * Creates an ImageView for a given block type.
   * @param blockType The type of block to create a view for.
   * @return The created ImageView or null if an error occurs.
   */
  protected ImageView createBlockView(String blockType) {
    try {
      return blockViewFactory.createBlockView(blockType);
    } catch (Exception e) {
      System.err.println("Failed to create block view for type: " + blockType + " with error: " + e.getMessage());
      return null;
    }
  }

  /**
   * Returns the root pane of this builder.
   * @return The root pane.
   */
  public Pane getRoot() {
    return root;
  }

  /**
   * Sets event handlers for removing blocks when in remove mode.
   */
  protected void setRemoveModeEventHandlers() {
    root.getChildren().forEach(node -> {
      if (node instanceof ImageView) {
        node.setOnMouseClicked(event -> {
          if (removeMode) {
            Point2D cellIndices = getCellIndices(node.getLayoutX(), node.getLayoutY());
            try {
              levelController.removeBlockFromCell((int) cellIndices.getY(), (int) cellIndices.getX());
              root.getChildren().remove(node);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
      }
    });
  }

  /**
   * Toggles the removal mode for blocks.
   * @param remove_bool True to enable removal mode, false to disable.
   */
  public void setRemove(boolean remove_bool) {
    removeMode = remove_bool;
    setRemoveModeEventHandlers();
  }

  /**
   * Renders a grid based on a pre-loaded set of grid data.
   * @param loadedGrid The grid data to load.
   */
  public void renderLoadedGrid(Grid loadedGrid) {
    gridWidth = loadedGrid.getNumColumns() - 2;
    gridHeight = loadedGrid.getNumRows() - 2;
    setUpGrid();
    levelController.getLevel().setGrid(new Grid(gridWidth, gridHeight));
    for (int row = 0; row < gridHeight; row++) {
      for (int col = 0; col < gridWidth; col++) {
        String[] blockTypes = loadedGrid.getCell(row, col);
        for (String blockType : blockTypes) {
          if (!"EmptyVisualBlock".equals(blockType)) {
            ImageView blockView = createBlockView(blockType);
            if (blockView != null) {
              blockView.setFitWidth(cellSize);
              blockView.setFitHeight(cellSize);
              double xPos = col * cellSize + gridPane.getLayoutX();
              double yPos = row * cellSize + gridPane.getLayoutY();
              blockView.setLayoutX(xPos);
              blockView.setLayoutY(yPos);
              root.getChildren().add(blockView);
              blockView.toFront();
              try {
                levelController.addBlockToCell(row, col, blockType);
              } catch (Exception e) {
                System.err.println("Error adding block to cell: " + e.getMessage());
              }
            }
          }
        }
      }
    }
  }

  /**
   * Calculates the column and row indices for a given x, y coordinate within the grid.
   * @param x the x-coordinate relative to the grid
   * @param y the y-coordinate relative to the grid
   * @return a Point2D object containing the column index (x value) and row index (y value)
   */
  protected Point2D getCellIndices(double x, double y) {
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

  /**
   * Calculates the pixel coordinates of the cell's upper-left corner based on a given x, y point within the grid.
   * @param x the x-coordinate relative to the grid
   * @param y the y-coordinate relative to the grid
   * @return a Point2D object representing the upper-left corner of the cell at the given x, y coordinates
   */
  protected Point2D getCellCoordinates(double x, double y) {
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
}
