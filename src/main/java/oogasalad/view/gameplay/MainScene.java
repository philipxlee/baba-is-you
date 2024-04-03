package oogasalad.view.gameplay;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import oogasalad.shared.scene.Scene;

public class MainScene implements Scene {

  private final int GAMEPLAY_WIDTH = 1000;
  private final int INTERACTION_WIDTH = 500;
  private final int HEIGHT = 800;
  private javafx.scene.Scene scene;
  private HBox root;
  private GameScene gameScene;
  private InteractionScene interactionScene;

  @Override
  public void initializeScene(int width, int height) {
    this.root = new HBox();
    this.scene = new javafx.scene.Scene(root, width, height);
    scene.getRoot().setStyle("-fx-background-color: #191A20;");

    //Initialize game grid
    this.gameScene = new GameScene();
    gameScene.initializeGameGrid(GAMEPLAY_WIDTH, HEIGHT, this);
    gameScene.getGrid().setLayoutX(1000);

    //Initialize interaction pane
    this.interactionScene = new InteractionScene();
    interactionScene.initializeInteractionPane(INTERACTION_WIDTH, HEIGHT, this);
    interactionScene.getPane().setLayoutX(0);

    root.getChildren().addAll(interactionScene.getPane(), gameScene.setUpScreen());

  }

  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}
