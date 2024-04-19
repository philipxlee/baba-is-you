package oogasalad.controller.gameplay;

import com.mongodb.client.MongoDatabase;
import java.util.List;
import oogasalad.database.dataservice.DataFetcher;
import oogasalad.database.dataservice.DataUploader;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.GameSession;
import oogasalad.database.gamedata.LeaderboardData;

/**
 * This class is responsible for handling the database interactions for the game.
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
   * @param database        database
   * @param levelController level controller
   */
  public DatabaseController(MongoDatabase database, LevelController levelController) {
    this.database = database;
    this.levelController = levelController;
    this.dataFetcher = new DataFetcher(database);
  }

  /**
   * Starts a new player session and initializes data services.
   *
   * @param username username
   * @return true if the session was started and necessary services were initialized
   */
  public boolean startNewPlayer(String username) {
    if (!isUsernameAvailable(username)) {
      return false;
    }
    this.username = username;
    if (!initializeSession(username)) {
      return false;
    }
    initializeDataUploader();  // Initialize after the session is successfully created
    return true;
  }

  /**
   * Checks if the username is available.
   *
   * @param username username
   * @return true if the username is available, false otherwise
   */
  public boolean isUsernameAvailable(String username) {
    return dataFetcher.isUsernameAvailable(username);
  }

  /**
   * Retrieves the top players for the current level.
   *
   * @return A list of leaderboard data.
   */
  public List<LeaderboardData> getTopPlayers() {
    return dataFetcher.getTopPlayers(levelController.getLevelName());
  }

  /**
   * Retrieves comments for the current level.
   *
   * @return A list of comment data.
   */
  public List<CommentData> getLevelComments() {
    return dataFetcher.getLevelComments(levelController.getLevelName());
  }

  /**
   * Saves a comment made on the current level.
   *
   * @param comment The comment text to save.
   */
  public void saveLevelCommentData(String comment) {
    dataUploader.saveLevelComment(comment);
  }

  /**
   * Saves the data of a player at the end of their game session.
   *
   * @param endTime The end time of the player's session.
   */
  public void savePlayerData(long endTime) {
    dataUploader.savePlayerLeaderboardData(startTime, endTime);
  }

  /**
   * Adds a reply to a user comment.
   *
   * @param commenterUsername The username of the person who originally commented.
   * @param playerUsername The username of the person replying.
   * @param reply The reply text.
   */
  public void addReply(String commenterUsername, String playerUsername, String reply) {
    dataUploader.addReplyToUserComment(commenterUsername, playerUsername, reply);
  }

  /**
   * Returns the current session's username.
   *
   * @return The current username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Initializes a new game session for a given username.
   *
   * @param username The username for whom to initialize the session.
   * @return true if the session is successfully initialized
   */
  private boolean initializeSession(String username) {
    this.gameSession = new GameSession(username, levelController.getLevelName());
    this.startTime = System.currentTimeMillis() / MILLISECOND_OFFSET; // Convert to seconds
    return this.gameSession != null; // Ensure gameSession is properly initialized
  }

  /**
   * Initializes DataUploader only after a valid game session is available.
   */
  private void initializeDataUploader() {
    if (gameSession != null && dataUploader == null) {
      dataUploader = new DataUploader(database, gameSession);
    }
  }
}
