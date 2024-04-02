package oogasalad.controller.gameplay;

import javafx.stage.Stage;
import oogasalad.shared.scene.Scene;
import oogasalad.view.gameplay.MainScene;
import oogasalad.view.gameplay.InteractionScene;
import oogasalad.view.gameplay.GameScene;

/**
 * SceneController is Controller that manages Scenes and the JavaFX Stage.
 */
public class SceneController {

  private static final int WIDTH = 1500;
  private static final int HEIGHT = 800;
  private final Stage stage;

  /**
   * ViewController constructor. Initialized with a JavaFX stage.
   *
   * @param stage primary stage of JavaFX application
   */
  public SceneController(Stage stage) {
    this.stage = stage;
    stage.setTitle("Baba is Us: Game Player");
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Initialize Main Scene.
   */
  public void initializeViews() {
    switchToScene(new MainScene());
  }

  /**
   * Switch to new Scene.
   *
   * @param scene is new Scene to switch to.
   */
  public void switchToScene(Scene scene) {
    scene.initializeScene(WIDTH, HEIGHT);
    stage.setScene(scene.getScene());
  }
}