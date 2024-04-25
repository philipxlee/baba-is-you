package oogasalad.controller.gameplay;

import com.mongodb.client.MongoDatabase;
import java.util.Iterator;
import oogasalad.database.dataservice.DataFetcher;
import oogasalad.database.dataservice.DataUploader;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.GameSession;
import oogasalad.database.gamedata.LeaderboardData;

/**
 * This class is responsible for controlling the database interactions for game play, such as
 * managing players, comments, and leaderboard data.
 *
 * @author Philip Lee.
 */
public class DatabaseController {

  private static final int MILLISECOND_OFFSET = 1000;
  private final MongoDatabase database;
  private final LevelController levelController;
  private GameSession gameSession;
  private String username;
  private long startTime;

  /**
   * Constructor for the DatabaseController class.
   *
   * @param database        the database connection to be used.
   * @param levelController the controller handling level-specific data.
   */
  public DatabaseController(MongoDatabase database, LevelController levelController) {
    this.database = database;
    this.levelController = levelController;
  }

  /**
   * Initializes a new player session if the username is available.
   *
   * @param username the username to start a new session.
   * @return true if the session is started, false if the username is unavailable.
   */
  public boolean startNewPlayer(String username) {
    if (isUsernameAvailable(username)) {
      initializeGameSession(username);
      DataUploader.getInstance(database).setGameSession(gameSession);
      return true;
    }
    return false;
  }


  /**
   * Checks if the provided username is available.
   *
   * @param username the username to check.
   * @return true if the username is available, false otherwise.
   */
  public boolean isUsernameAvailable(String username) {
    return DataFetcher.getInstance(database).isUsernameAvailable(username);
  }

  /**
   * Retrieves an iterator over the leaderboard data for a specified level.
   *
   * @param levelName the level name to get leaderboard data for.
   * @return an iterator over LeaderboardData.
   */
  public Iterator<LeaderboardData> getTopPlayersIterator(String levelName) {
    return DataFetcher.getInstance(database).getTopPlayersIterator(levelName);
  }

  /**
   * Retrieves an iterator over the comments for a specified level.
   *
   * @param levelName the level name to get comments for.
   * @return an iterator of CommentData.
   */
  public Iterator<CommentData> getLevelCommentsIterator(String levelName) {
    return DataFetcher.getInstance(database).getLevelCommentsIterator(levelName);
  }

  /**
   * Saves a comment for the current level to the database.
   *
   * @param comment the comment to save.
   */
  public void saveLevelCommentData(String comment) {
    DataUploader.getInstance(database).saveLevelComment(comment);
  }

  /**
   * Saves the session data for the current player to the database.
   *
   * @param endTime the end time of the session.
   */
  public void savePlayerData(long endTime) {
    DataUploader.getInstance(database).savePlayerLeaderboardData(startTime, endTime);
  }

  /**
   * Adds a reply to a comment for the current level.
   *
   * @param commenterUsername the username of the commenter.
   * @param playerUsername    the username of the player replying.
   * @param reply             the reply content.
   */
  public void addReply(String commenterUsername, String playerUsername, String reply) {
    DataUploader.getInstance(database)
        .addReplyToUserComment(commenterUsername, playerUsername, reply);
  }

  /**
   * Gets the username of the player.
   *
   * @return the username of the player.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Initializes a new game session for the player.
   *
   * @param username the username of the player.
   */
  private void initializeGameSession(String username) {
    this.username = username;
    this.gameSession = new GameSession(username, levelController.getLevelName());
    this.startTime = System.currentTimeMillis() / MILLISECOND_OFFSET; // Convert to seconds
  }

}
