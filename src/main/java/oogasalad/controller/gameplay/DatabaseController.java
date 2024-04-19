package oogasalad.controller.gameplay;

import com.mongodb.client.MongoDatabase;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import oogasalad.database.dataservice.DataFetcher;
import oogasalad.database.dataservice.DataUploader;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.GameSession;
import oogasalad.database.gamedata.LeaderboardData;

/**
 * This class is responsible for controlling the database.
 */
public class DatabaseController {

  private static final int MILLISECOND_OFFSET = 1000;
  private final MongoDatabase database;
  private final LevelController levelController;
  private DataFetcher dataFetcher;
  private DataUploader dataUploader;
  private GameSession gameSession;
  private String username;
  private long startTime;

  /**
   * Constructor for the DatabaseController class.
   *
   * @param database       database
   * @param levelController level controller
   */
  public DatabaseController(MongoDatabase database, LevelController levelController) {
    this.database = database;
    this.levelController = levelController;
  }

  /**
   * Starts a new player.
   *
   * @param username username
   * @return true if the player is started, false otherwise
   */
  public boolean startNewPlayer(String username) {
    if (!isUsernameAvailable(username)) {
      return false;
    }
    this.username = username;
    initializeSession(username);
    return true;
  }

  /**
   * Checks if the username is available.
   *
   * @param username username
   * @return true if the username is available, false otherwise
   */
  public boolean isUsernameAvailable(String username) {
    initializeDataFetcher();
    return dataFetcher.isUsernameAvailable(username);
  }

  /**
   * Gets the top players.
   *
   * @return list of top players
   */
  public Iterator<LeaderboardData> getTopPlayersIterator(String levelName) {
    initializeDataFetcher();
    return dataFetcher.getTopPlayersIterator(levelName);
  }


  /**
   * Gets the level comments.
   *
   * @return list of level comments
   */
  public List<CommentData> getLevelComments() {
    initializeDataFetcher();
    return dataFetcher.getLevelComments(levelController.getLevelName());
  }

  /**
   * Saves the level comment data.
   *
   * @param comment comment
   */
  public void saveLevelCommentData(String comment) {
    initializeDataUploader();
    dataUploader.saveLevelComment(comment);
  }

  /**
   * Saves the player data.
   *
   * @param endTime end time
   */
  public void savePlayerData(long endTime) {
    initializeDataUploader();
    dataUploader.savePlayerLeaderboardData(startTime, endTime);
  }

  /**
   * Adds a reply to a user comment.
   *
   * @param commenterUsername comment username
   * @param playerUsername   player username
   * @param reply           reply
   */
  public void addReply(String commenterUsername, String playerUsername, String reply) {
    initializeDataUploader();
    dataUploader.addReplyToUserComment(commenterUsername, playerUsername, reply);
  }

  /**
   * Gets the username.
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  private void initializeDataFetcher() {
    if (dataFetcher == null) {
      dataFetcher = new DataFetcher(database);
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
