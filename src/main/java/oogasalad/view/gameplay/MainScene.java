package oogasalad.view.gameplay;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetFactory;

public class MainScene implements Scene {

  private final int GAMEPLAY_WIDTH = 1000;
  private final int INTERACTION_WIDTH = 500;
  private final int HEIGHT = 800;
  private final SceneController sceneController;
  private javafx.scene.Scene scene;
  private HBox root;
  private GamePane gameScene;
  private InteractionPane interactionScene;

  public MainScene(SceneController sceneController) {
    this.sceneController = sceneController;
  }

  @Override
  public void initializeScene(int width, int height) {
    this.root = new HBox();
    this.root.setAlignment(Pos.TOP_LEFT);
    this.scene = new javafx.scene.Scene(root, width, height);

    //Initialize widget factor
    WidgetFactory factory = new WidgetFactory();

    //Initialize game grid
    this.gameScene = new GamePane();
    gameScene.initializeGameGrid(GAMEPLAY_WIDTH, HEIGHT, this, sceneController);
    gameScene.getGrid().setLayoutX(1000);

    //Initialize interaction pane1
    this.interactionScene = new InteractionPane();
    interactionScene.initializeInteractionPane(INTERACTION_WIDTH, HEIGHT, this,
        factory);
    interactionScene.getPane().setLayoutX(0);

    root.getChildren().addAll(interactionScene.getPane(), gameScene.setUpScreen());
    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
        .toExternalForm());
    //factory.addScene(this);
  }

  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}
