package oogasalad.controller.authoring;

import javafx.stage.Stage;
import oogasalad.view.shared.Scene;

/**
 * ViewController is Controller that manages Scenes and the JavaFX Stage.
 */
public class ViewController {

  private static final int WIDTH = 1500;
  private static final int HEIGHT = 800;
  private final Stage stage;

  /**
   * ViewController constructor. Initialized with a JavaFX stage.
   *
   * @param stage primary stage of JavaFX application
   */
  public ViewController(Stage stage) {
    this.stage = stage;
    stage.setTitle("Baba is Us");
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Initialize Start Scene.
   */
  public void initializeViews() {
    // switch to start scene
  }

  /**
   * Switch to new Scene.
   *
   * @param scene is new Scene to switch to
   */
  public void switchToScene(Scene scene) {
    scene.initializeScene(WIDTH, HEIGHT);
    stage.setScene(scene.getScene());
  }
}
