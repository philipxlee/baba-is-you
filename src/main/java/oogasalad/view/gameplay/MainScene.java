package oogasalad.view.gameplay;

import oogasalad.shared.Scene;

/**
 * MainScene is the main scene implementation for Game Player.
 */
public class MainScene implements Scene {

  private javafx.scene.Scene scene;

  /**
   * Initialize the Main Scene. Displays Game.
   *
   * @param width:  width of scene.
   * @param height: height of scene.
   */
  @Override
  public void initializeScene(int width, int height) {

  }

  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}
