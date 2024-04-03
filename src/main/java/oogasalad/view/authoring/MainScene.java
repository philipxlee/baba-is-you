


package oogasalad.view.authoring;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import oogasalad.shared.scene.Scene;

public class MainScene implements Scene {
  private javafx.scene.Scene scene;
  private HBox root;
  private BuilderScene builderScene;
  private ElementsScene elementsScene;

  @Override
  public void initializeScene(int width, int height) {
    this.root = new HBox();

    // Initialize builder scene with 60% of width
    this.builderScene = new BuilderScene();
    int builderWidth = (int) (width * 0.6);
    builderScene.initializeBuilderScene(builderWidth, height, this);

    // Initialize elements scene with 40% of width
    this.elementsScene = new ElementsScene();
    int elementsWidth = (int) (width * 0.4);
    elementsScene.initializeElementsScene(elementsWidth, height, this);

    // Adding panes to the root with calculated widths
    root.getChildren().addAll(elementsScene.setUpScreen(), builderScene.getRoot());
    this.scene = new javafx.scene.Scene(root, width, height);
    scene.setFill(Color.WHITE);
  }

  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}