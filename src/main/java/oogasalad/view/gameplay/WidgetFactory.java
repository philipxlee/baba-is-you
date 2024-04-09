package oogasalad.view.gameplay;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import oogasalad.shared.scene.Scene;

public class WidgetFactory {

  public static final String STYLESHEET = "gameplay.css";
  public static final String DEFAULT_RESOURCE_PACKAGE = "resources.";
  public static final String DEFAULT_RESOURCE_FOLDER =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private oogasalad.shared.scene.Scene scene;

  public WidgetFactory() {
  }

  public void addScene(Scene scene) {
    this.scene = scene;
    scene.getScene().getStylesheets().add(getClass().getResource
        (DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
  }

  public Rectangle interactionPanel(int width, int height) {
    Rectangle panel = new Rectangle(0, 0, width, height);
    panel.getStyleClass().add("interaction-background");
    return panel;
  }

}
