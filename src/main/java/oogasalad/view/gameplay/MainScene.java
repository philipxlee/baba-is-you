package oogasalad.view.gameplay;

import javafx.scene.Group;
import oogasalad.shared.scene.Scene;

public class MainScene implements Scene {
  private javafx.scene.Scene scene;
  private Group root;
  private GameScene gameScene;
  private InteractionScene interactionScene;
  private final int GAMEPLAY_WIDTH = 1000;
  private final int INTERACTION_WIDTH = 500;
  private final int HEIGHT = 800;

  @Override
  public void initializeScene(int width, int height) {
    this.root = new Group();
    this.scene = new javafx.scene.Scene(root, width, height);

    //Initialize game grid
    this.gameScene = new GameScene();
    gameScene.initializeGameGrid(GAMEPLAY_WIDTH, HEIGHT, this);
    gameScene.getGrid().setLayoutX(1000);

    //Initialize interaction pane
    this.interactionScene = new InteractionScene();
    interactionScene.initializeInteractionPane(INTERACTION_WIDTH, HEIGHT, this);
    interactionScene.getPane().setLayoutX(0);

    root.getChildren().addAll(gameScene.getGrid(), interactionScene.getPane());

  }

  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}
