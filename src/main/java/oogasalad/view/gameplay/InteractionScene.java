package oogasalad.view.gameplay;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.shared.scene.Scene;

public class InteractionScene {
  private Group root;
  private MainScene scene;

  public void initializeInteractionPane(int width, int height, MainScene scene) {
    this.root = new Group();

    Rectangle rectangle = new Rectangle(0, 0, width, height);
    rectangle.setFill(Color.PURPLE);
    root.getChildren().addAll(rectangle);
  }

  public Group getPane() {
    return this.root;
  }
}
