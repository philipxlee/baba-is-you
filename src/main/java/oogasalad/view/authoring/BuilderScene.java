package oogasalad.view.authoring;

import java.io.File;

import java.util.Optional;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BuilderScene {

  private Pane root; // Your root node for the builder scene
  private GridPane gridPane;
  private int gridWidth;
  private int gridHeight;
  private int cellSize;
  private final int GRID_MARGIN = 5;

  public BuilderScene() {
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

    setUpGrid();
    setUpDropHandling();
  }

  private void setUpGrid() {
    gridPane.getChildren().clear(); // Clear the existing grid

    // Adjust the maximum width and height available for the grid, accounting for margins
    double availableWidth = root.getWidth() - 2 * GRID_MARGIN;
    double availableHeight = root.getHeight() - 2 * GRID_MARGIN;

    // Calculate cell size based on the available space and the grid dimensions
    double calculatedCellSize = Math.min(availableWidth / gridWidth, availableHeight / gridHeight);
    this.cellSize = (int) calculatedCellSize;

    // Calculate total size of the grid
    double totalGridWidth = gridWidth * cellSize;
    double totalGridHeight = gridHeight * cellSize;

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
        cell.setStyle("-fx-border-color: black; -fx-background-color: white;");
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
    root.setOnDragOver(event -> {
      if (event.getGestureSource() != root && event.getDragboard().hasString()) {
        event.acceptTransferModes(TransferMode.MOVE);
      }
      event.consume();
    });

    root.setOnDragDropped(event -> {
      Dragboard db = event.getDragboard();
      boolean success = false;
      if (db.hasString()) {
        String blockType = db.getString();
        ImageView blockView = createBlockView(blockType);
        if (blockView != null) {
          Point2D cellCoords = getCellCoordinates(event.getX(), event.getY());
          if (cellCoords != null) {
            blockView.setFitWidth(cellSize);
            blockView.setFitHeight(cellSize);
            blockView.setLayoutX(cellCoords.getX());
            blockView.setLayoutY(cellCoords.getY());
            root.getChildren().add(blockView);
            success = true;
          }
        }
      }
      event.setDropCompleted(success);
      event.consume();
    });
  }

  private ImageView createBlockView(String blockType) {
    String imagePath =
        "src/main/resources/images/" + blockType + ".png"; // Adjust path as necessary
    File imageFile = new File(imagePath);
    if (!imageFile.exists()) {
      System.err.println("Image file not found: " + imagePath);
      return null; // Or handle this case as needed.
    }
    Image image = new Image(imageFile.toURI().toString(), 100, 100, true, true);
    return new ImageView(image);
  }

  private Point2D getCellCoordinates(double x, double y) {
    for (int i = 0; i < gridWidth; i++) {
      for (int j = 0; j < gridHeight; j++) {
        double cellX = i * cellSize + gridPane.getLayoutX();
        double cellY = j * cellSize + gridPane.getLayoutY();
        double cellWidth = cellSize;
        double cellHeight = cellSize;

        // Check if the coordinates (x, y) are within the bounds of this cell
        if (x >= cellX && x <= cellX + cellWidth && y >= cellY && y <= cellY + cellHeight) {
          return new Point2D(cellX, cellY);
        }
      }
    }
    return null; // Coordinates (x, y) do not fall within any cell
  }

  public Pane getRoot() {
    return root;
  }
}