package oogasalad.view.gameplay.socialcenter;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.Iterator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.database.gamedata.LeaderboardData;
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
  private final LevelController levelController;
  private final String language;
  private int width;
  private int height;

  /**
   * Constructor for LeaderboardScene.
   *
   * @param factory         WidgetFactory
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
    this.root = new VBox(20);
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
    root.getChildren().add(header);

    Iterator<LeaderboardData> topPlayersIterator = sceneController.getDatabaseController()
        .getTopPlayersIterator(sceneController.getLevelController().getLevelName());

    VBox leaderboardList = new VBox(5);
    leaderboardList.setAlignment(Pos.CENTER);

    int num = 1;
    while (topPlayersIterator.hasNext()) {
      LeaderboardData player = topPlayersIterator.next();
      HBox row = createLeaderboardRow(num++, player);
      row.setAlignment(Pos.CENTER);
      leaderboardList.getChildren().add(row);
    }

    Button backButton = factory.makeButton(
        new WidgetConfiguration(200, 50, "Back", "button", language));
    backButton.setOnAction(
        event -> sceneController.switchToScene(new MainScene(sceneController, levelController)));

    root.getChildren().addAll(leaderboardList, backButton);
  }

  private HBox createLeaderboardRow(int number, LeaderboardData player) {
    WidgetConfiguration configuration = new WidgetConfiguration(300, 50, "row-cell", language);
    Button username = factory.makeButton(configuration, number + ". " + player.getUsername());
    Button timeSpent = factory.makeButton(configuration, player.getTimeSpent() + " seconds");
    Button date = factory.makeButton(configuration, player.getDate());
    username.setDisable(true);
    timeSpent.setDisable(true);
    date.setDisable(true);
    return new HBox(username, timeSpent, date);
  }

}
