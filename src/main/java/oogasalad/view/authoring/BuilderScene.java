package oogasalad.view.authoring;

import java.io.File;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BuilderScene {

  private Pane root; // Your root node for the builder scene
  private final int gridSize = 5; // Set the grid size
  private final int cellSize = 100; // Set the cell size
  private GridPane gridPane;

  public BuilderScene() {
    initializeBuilderScene();
  }

  public void initializeBuilderScene() {
    this.root = new Pane();
    this.gridPane = new GridPane();
    setUpGrid();
    setUpDropHandling();
  }

  private void setUpGrid() {
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        Pane cell = new Pane();
        cell.setPrefSize(cellSize, cellSize);
        cell.setStyle("-fx-border-color: black;");
        gridPane.add(cell, j, i);
      }
    }
    root.getChildren().add(gridPane);
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
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        Bounds cellBounds = gridPane.getChildren().get(i * gridSize + j).getBoundsInParent();
        if (cellBounds.contains(x, y)) {
          double cellX = cellBounds.getMinX();
          double cellY = cellBounds.getMinY();
          return new Point2D(cellX, cellY);
        }
      }
    }
    return null;
  }

  public Pane getRoot() {
    return root;
  }
}