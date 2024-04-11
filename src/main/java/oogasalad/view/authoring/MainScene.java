package oogasalad.view.authoring;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import javafx.scene.control.SplitPane;
import javafx.scene.paint.Color;
import oogasalad.shared.scene.Scene;

public class MainScene implements Scene {

  private javafx.scene.Scene scene;

  @Override
  public void initializeScene(int width, int height) {

    SplitPane root = new SplitPane();

    // Initialize builder scene with 60% of width
    BuilderScene builderScene = new BuilderScene();

    // Initialize elements scene with 40% of width
    ElementsScene elementsScene = new ElementsScene(builderScene);

    // Set up left and right sides of SplitPane
    root.getItems().addAll(elementsScene.getLayout(), builderScene.getRoot());
    root.setDividerPositions(0.4);

    this.scene = new javafx.scene.Scene(root, width, height);

    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
        .toExternalForm());
  }

  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}