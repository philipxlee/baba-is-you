package oogasalad.view.gameplay;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.database.PlayerData;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * Scene that displays the leaderboard.
 */
public class CommentScene implements Scene {

  private final static String LEVEL_NAME = "Default Level";
  private final WidgetFactory factory;
  private final SceneController sceneController;
  private javafx.scene.Scene scene;
  private VBox root;
  private LevelController levelController;

  /**
   * Constructor for LeaderboardScene.
   *
   * @param factory WidgetFactory
   * @param sceneController SceneController
   */
  public CommentScene(WidgetFactory factory, SceneController sceneController) {
    this.factory = factory;
    this.sceneController = sceneController;
    this.levelController = sceneController.getLevelController();
  }

  /**
   * Initialize the scene.
   *
   * @param width  width of scene
   * @param height height of scenes
   */
  @Override
  public void initializeScene(int width, int height) {
    this.root = new VBox(10);
    this.root.setAlignment(Pos.CENTER);
    this.scene = new javafx.scene.Scene(root, width, height);
    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
        .toExternalForm());
    populateComments();
  }

  /**
   * Get the scene object.
   *
   * @return Java FX Scene object that represents the scene
   */
  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }

  /**
   * Populate the leaderboard.
   */
  private void populateComments() {
    Text header = factory.generateHeader(new WidgetConfiguration("Comments"));
    List<PlayerData> comments = sceneController.getPlayerDataController().getCommentsByLevel(LEVEL_NAME);  // Assuming this method exists

    VBox commentsList = new VBox(5);
    commentsList.setAlignment(Pos.CENTER);

    for (PlayerData comment : comments) {
      String commentText = String.format("%s: %s", comment.getUsername(), comment.getComments());
      Button commentButton = factory.makeButton(new WidgetConfiguration(200, 50, "white-button"), commentText);

      commentsList.getChildren().add(commentButton);
    }

    Button backButton = factory.makeButton(new WidgetConfiguration(200, 50, "Back", "button"));
    backButton.setOnAction(event -> sceneController.switchToScene(new MainScene(sceneController, levelController)));

    root.getChildren().addAll(header, commentsList, backButton);
  }



}
