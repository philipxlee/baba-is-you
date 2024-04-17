package oogasalad.view.gameplay;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.database.PlayerData;
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
  private LevelController levelController;

  /**
   * Constructor for LeaderboardScene.
   *
   * @param factory WidgetFactory
   * @param sceneController SceneController
   */
  public LeaderboardScene(WidgetFactory factory, SceneController sceneController) {
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
    header.setFont(Font.font(50));
    header.setStyle("-fx-fill: WHITE");

    List<PlayerData> topPlayers = sceneController.getPlayerDataController().getTopPlayers();
    VBox leaderboardList = new VBox(5);
    leaderboardList.setAlignment(Pos.CENTER);

    for (PlayerData player : topPlayers) {
      String playerInfoText = String.format("%s - %d sec - %s",
          player.getUsername(), player.getTimeSpent(), player.getComments());

      // Create a button with the player information, make it unpressable
      Button playerInfoButton = factory.makeAuthoringButton(playerInfoText, 300, 20);
      playerInfoButton.setDisable(true);  // Make the button unpressable
      playerInfoButton.setMaxWidth(500);  // Extend the button width to the full width of its container

      // Add the styled button to the leaderboard list
      leaderboardList.getChildren().add(playerInfoButton);
    }

    Button backButton = factory.makeButton("Back", 200, 40);
    backButton.setOnAction(event -> sceneController.switchToScene(new MainScene(sceneController,
        levelController)));

    root.getChildren().addAll(header, leaderboardList, backButton);
  }


}
