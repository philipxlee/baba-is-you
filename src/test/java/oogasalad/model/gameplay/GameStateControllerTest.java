package oogasalad.model.gameplay;

import javafx.stage.Stage;
import oogasalad.controller.gameplay.GameStateController;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.PlayerDataController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.database.DataManager;
import oogasalad.database.DatabaseConfig;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import org.junit.jupiter.api.BeforeEach;

public class GameStateControllerTest {

  private GameStateController gameStateController;
  private SceneController sceneController;
  private Stage stage;
  private final DatabaseConfig databaseConfig = new DatabaseConfig();
  private final DataManager dataManager = new DataManager(databaseConfig.getDatabase());
  private final PlayerDataController playerDataController = new PlayerDataController(dataManager);
  String[][][] initialConfiguration = {
      {{"EmptyVisualBlock", "RockVisualBlock"}, {"EmptyVisualBlock"}},
      {{"EmptyVisualBlock"}, {"EmptyVisualBlock", "BabaVisualBlock"}}
  };
  private final LevelMetadata metadata = new LevelMetadata("TestLevel", "Easy", "3",
      2, 2, initialConfiguration);

  @BeforeEach
  void setUp() {
    stage = new Stage();
    sceneController = new SceneController(stage, playerDataController, new LevelController
        (new Level(metadata)));
    gameStateController = new GameStateController(sceneController);
  }

}
