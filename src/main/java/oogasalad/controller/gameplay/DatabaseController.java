package oogasalad.controller.gameplay;

import com.mongodb.client.MongoDatabase;
import oogasalad.database.dataservice.DataFetcher;
import oogasalad.database.dataservice.DataUploader;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.GameSession;
import oogasalad.database.gamedata.LeaderboardData;
import java.util.List;

public class DatabaseController {

  private static final int MILLISECOND_OFFSET = 1000;
  private final MongoDatabase database;
  private final LevelController levelController;
  private DataFetcher dataFetcher;
  private DataUploader dataUploader;
  private GameSession gameSession;
  private long startTime;
  private String username;

  public DatabaseController(MongoDatabase database, LevelController levelController) {
    this.database = database;
    this.levelController = levelController;
  }

  public boolean startNewPlayer(String username) {
    if (!isUsernameAvailable(username)) {
      return false;
    }
    this.username = username;
    initializeSession(username);
    return true;
  }

  public boolean isUsernameAvailable(String username) {
    initializeDataFetcher();
    return dataFetcher.isUsernameAvailable(username);
  }

  public List<LeaderboardData> getTopPlayers() {
    initializeDataFetcher();
    return dataFetcher.getTopPlayers(levelController.getLevelName());
  }

  public List<CommentData> getLevelComments() {
    initializeDataFetcher();
    return dataFetcher.getLevelComments(levelController.getLevelName());
  }

  public void saveLevelCommentData(String comment) {
    initializeDataUploader();
    dataUploader.saveLevelComment(comment);
  }

  public void savePlayerData(long endTime) {
    initializeDataUploader();
    dataUploader.savePlayerLeaderboardData(startTime, endTime);
  }

  public void addReply(String commenterUsername, String playerUsername, String reply) {
    initializeDataUploader();
    dataUploader.addReplyToUserComment(commenterUsername, playerUsername, reply);
  }

  public String getUsername() {
    return username;
  }

  private void initializeDataFetcher() {
    if (dataFetcher == null) {
      dataFetcher = new DataFetcher(database, gameSession);
    }
  }

  private void initializeDataUploader() {
    if (dataUploader == null) {
      dataUploader = new DataUploader(database, gameSession);
    }
  }

  private void initializeSession(String username) {
    this.gameSession = new GameSession(username, levelController.getLevelName());
    this.startTime = System.currentTimeMillis() / MILLISECOND_OFFSET; // Convert to seconds
    initializeDataServices();
  }

  private void initializeDataServices() {
    initializeDataFetcher();
    initializeDataUploader();
  }
}
