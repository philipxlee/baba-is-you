package oogasalad.view.authoring;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

public class BuilderScene {
  private Pane root; // Your root node for the builder scene
  private int width;
  private int height;
  private MainScene scene;

  public void initializeBuilderScene(int width, int height, MainScene scene) {
    this.root = new Pane();
    this.width = width;
    this.height = height;
    this.scene = scene;
    setUpDropHandling();
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
        // Handle the dropped item. For example, instantiate a new block view based on the identifier.
        String blockType = db.getString();
        // TODO: Create and add a new block view to 'root' based on 'blockType'
        success = true;
      }
      event.setDropCompleted(success);
      event.consume();
    });
  }

  public Pane getRoot() {
    return root;
  }
}
