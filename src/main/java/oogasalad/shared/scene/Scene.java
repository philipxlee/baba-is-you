package oogasalad.shared.scene;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

/**
 * Scene abstraction represents a scene being displayed.
 */
public interface Scene {

  /**
   * Initialize the scene.
   *
   * @param width  width of scene
   * @param height height of scenes
   */
  void initializeScene(int width, int height);

  /**
   * Get the scene object.
   *
   * @return Java FX Scene object that represents the scene
   */
  javafx.scene.Scene getScene();

  /**
   * Apply a CSS stylesheet to the scene
   * @param resourceFolder resource folder where the stylesheet is
   * @param stylesheet name of the stylesheet path
   */
  default void applyCss(String resourceFolder, String stylesheet) {
    getScene().getStylesheets().add(getClass().getResource(resourceFolder + stylesheet)
        .toExternalForm());
  }
}
