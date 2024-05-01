package oogasalad.view.gameplay.mainscene;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.database.DatabaseConfig;
import oogasalad.model.gameplay.level.Level;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main Scene of the GamePlay, composed of the GamePane and InteractionPane.
 */
public class MainScene implements Scene {

  private final int GAMEPLAY_WIDTH = 1000;
  private final int INTERACTION_WIDTH = 500;
  private final int HEIGHT = 800;
  private final SceneController sceneController;
  private javafx.scene.Scene scene;
  private HBox root;
  private GamePane gameScene;
  private InteractionPane interactionScene;
  private final Level level;
  private final LevelController levelController;
  private static final Logger logger = LogManager.getLogger(MainScene.class);

  /**
   * Creates the main scene.
   *
   * @param sceneController SceneController object for switching between scenes
   * @param levelController LevelController object for the current level of the scene
   */
  public MainScene(SceneController sceneController, LevelController levelController) {
    this.sceneController = sceneController;
    this.levelController = levelController;
    this.level = levelController.getLevel();
  }

  /**
   * Initializes the scene and its widgets.
   *
   * @param width  width of scene
   * @param height height of scenes
   */
  @Override
  public void initializeScene(int width, int height) {
    this.root = new HBox();
    this.root.setAlignment(Pos.TOP_LEFT);
    this.scene = new javafx.scene.Scene(root, width, height);

    //Initialize widget factor
    WidgetFactory factory = new WidgetFactory();

    //Initialize game grid
    this.gameScene = new GamePane();
    gameScene.initializeGameGrid(GAMEPLAY_WIDTH, HEIGHT, this, sceneController, level,
        levelController);
    gameScene.getGrid().setLayoutX(1000);

    //Initialize interaction pane1
    this.interactionScene = new InteractionPane();
    interactionScene.initializeInteractionPane(INTERACTION_WIDTH, HEIGHT, this,
        factory, sceneController, levelController);
    interactionScene.getPane().setLayoutX(0);

    root.getChildren().addAll(interactionScene.getPane(), gameScene.setUpScreen());
    applyCss(DEFAULT_RESOURCE_FOLDER, STYLESHEET);
    logger.info("Initialized the gameplay main scene.");

  }

  /**
   * Resets the game.
   */
  public void resetGame() {
    gameScene.setGameOverStatus(false);
    sceneController.beginGame(sceneController.isGuestSession());
  }

  /**
   * Returns the InteractionPane for this scene.
   *
   * @return
   */
  public InteractionPane getInteractionPane() {
    // Assuming you have a reference to InteractionPane in MainScene
    return this.interactionScene;
  }

  /**
   * Returns the actual javafx scene object for this scene.
   *
   * @return javafx scene obj
   */
  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }

}
