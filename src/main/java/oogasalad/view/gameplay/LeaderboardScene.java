package oogasalad.view.gameplay;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.model.gameplay.player.PlayerData;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * Scene that displays the leaderboard.
 */
public class LeaderboardScene implements Scene {

  private final WidgetFactory factory;
  private final SceneController sceneController;
  private javafx.scene.Scene scene;
  private VBox root;

  /**
   * Constructor for LeaderboardScene.
   *
   * @param factory WidgetFactory
   * @param sceneController SceneController
   */
  public LeaderboardScene(WidgetFactory factory, SceneController sceneController) {
    this.factory = factory;
    this.sceneController = sceneController;
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
    populateLeaderboard();
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
  private void populateLeaderboard() {
    Text header = new Text("Leaderboard");
    header.setFont(Font.font(24));

    List<PlayerData> topPlayers = sceneController.getPlayerDataController().getTopPlayers();
    VBox leaderboardList = new VBox(5);
    for (PlayerData player : topPlayers) {
      Text playerInfo = new Text(String.format("%s - %d sec - %s",
          player.getUsername(), player.getTimeSpent() / 1000, player.getComments()));
      leaderboardList.getChildren().add(playerInfo);
    }

    ScrollPane scrollPane = new ScrollPane(leaderboardList);
    scrollPane.setFitToWidth(true);

    Button backButton = factory.makeButton("Back", 200, 40);
    backButton.setOnAction(event -> sceneController.switchToScene(new MainScene(sceneController)));

    root.getChildren().addAll(header, scrollPane, backButton);
  }

}
