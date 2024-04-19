package oogasalad.controller.gameplay;

import com.mongodb.client.MongoDatabase;
import oogasalad.database.dataservice.DataFetcher;
import oogasalad.database.dataservice.DataUploader;
import oogasalad.database.gamedata.GameSession;
import oogasalad.database.gamedata.LeaderboardData;
import java.util.List;
import java.util.Optional;


/**
 * Controller class for managing player data, including starting and ending player sessions and
 * saving player data to a database.
 */
public class DatabaseController {

  private static final int MILLISECOND_OFFSET = 1000;
  private MongoDatabase database;
  private LevelController levelController;
  private DataFetcher dataFetcher;
  private DataUploader dataUploader;
  private Optional<GameSession> gameSession;
  private long startTime;

  public DatabaseController(MongoDatabase database, LevelController levelController) {
    this.database = database;
    this.levelController = levelController;
    initializeDataServices();
  }

  public boolean startNewPlayer(String username) {
    return dataFetcher.isUsernameAvailable(username)
        ? initializeSession(username)
        : false;
  }

  private boolean initializeSession(String username) {
    gameSession = Optional.of(new GameSession(username, levelController.getLevelName()));
    startTime = System.currentTimeMillis() / 1000;  // Assuming MILLISECOND_OFFSET is 1000
    return true;
  }
  /**
   * Checks if a username is available in the database.
   *
   * @param username The username to check.
   * @return True if the username is available, false otherwise.
   */
  public boolean isUsernameAvailable(String username) {
    return dataFetcher.isUsernameAvailable(username);
  }

  /**
   * Retrieves top player statistics for leaderboard display.
   *
   * @return a list of PlayerData objects for the top players.
   */
  public List<LeaderboardData> getTopPlayers() {
    return dataFetcher.getTopPlayers();
  }

  private void initializeDataServices() {
    this.dataFetcher = new DataFetcher(database);
    this.dataUploader = new DataUploader(database);
  }

}
