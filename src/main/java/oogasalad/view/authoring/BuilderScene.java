package oogasalad.view.authoring;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BuilderScene {

  private Pane root; // Your root node for the builder scene
  private int gridSize = 5; // Set the grid size
  private int cellSize = 100; // Set the cell size
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
        // Hypothetical method to create a view based on block type
        ImageView blockView = createBlockView(blockType);
        if (blockView != null) {
          blockView.setFitWidth(cellSize);
          blockView.setFitHeight(cellSize);
          blockView.setLayoutX(event.getX());
          blockView.setLayoutY(event.getY());
          root.getChildren().add(blockView);
          success = true;
        }
      }
      event.setDropCompleted(success);
      event.consume();
    });
  }

  private ImageView createBlockView(String blockType) {
    String imagePath = "src/main/resources/images/" + blockType + ".png"; // Adjust path as necessary
    // Creating a File object to ensure the path is correctly formed.
    File imageFile = new File(imagePath);
    if (!imageFile.exists()) {
      System.err.println("Image file not found: " + imagePath);
      return null; // Or handle this case as needed.
    }

    Image image = new Image(imageFile.toURI().toString(), 100, 100, true, true);
    return new ImageView(image);
  }

  public Pane getRoot() {
    return root;
  }
}