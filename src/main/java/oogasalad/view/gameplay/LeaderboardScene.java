package oogasalad.view.gameplay;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.database.LeaderboardPlayerData;
import oogasalad.database.PlayerData;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import oogasalad.view.gameplay.mainscene.MainScene;

/**
 * Scene that displays the leaderboard.
 */
public class LeaderboardScene implements Scene {

  private final WidgetFactory factory;
  private final SceneController sceneController;
  private javafx.scene.Scene scene;
  private VBox root;
  private LevelController levelController;
  private String language;
  private int width;
  private int height;
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
    this.language = sceneController.getLanguage();
  }

  /**
   * Initialize the scene.
   *
   * @param width  width of scene
   * @param height height of scenes
   */
  @Override
  public void initializeScene(int width, int height) {
    this.width = width;
    this.height = height;
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
    Text header = factory.generateHeader(new WidgetConfiguration("LeaderBoard", language));

    List<LeaderboardPlayerData> topPlayers = sceneController.getPlayerDataController().getTopPlayers();
    VBox leaderboardList = new VBox(5);
    leaderboardList.setAlignment(Pos.CENTER);

    FlowPane flowPane = factory.createFlowPane(new WidgetConfiguration(
        width - 400, height - 200, "flowpane-gradient", language)); // Set a maximum size for the flow pane

    int num = 1;
    for (LeaderboardPlayerData player : topPlayers) {
      String playerInfoText = String.format("%s - %d sec - %s",
          player.getUsername(), player.getTimeSpent(), player.getDate(), player.getLevelName());
      playerInfoText = "" + num + ". " + playerInfoText;

      Text playerInfo = factory.generateSubHeader(playerInfoText);
      leaderboardList.getChildren().add(playerInfo);

      num++;
    }
    flowPane.getChildren().add(leaderboardList);
    ScrollPane scrollPane = factory.makeScrollPane(flowPane, width-400);

    Button backButton = factory.makeButton(new WidgetConfiguration(200, 50,
        "Back", "button", language));
    backButton.setOnAction(event -> sceneController.switchToScene(new MainScene(sceneController,
        levelController)));

    root.getChildren().addAll(header, scrollPane, backButton);
  }

}
