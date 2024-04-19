package oogasalad.database.gamedata;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class is responsible for storing the game session data.
 */
public class GameSession {

  private static final Date DEFAULT_DATE = new Date();
  private static final String DEFAULT_COMMENT = "";
  private static final ArrayList<ReplySchema> DEFAULT_REPLIES = new ArrayList<>();
  private static final int DEFAULT_TIME = 0;

  private final CommentData commentData;
  private final LeaderboardData leaderboardData;

  /**
   * Constructor for the GameSession class.
   *
   * @param username  username
   * @param levelName level name
   */
  public GameSession(String username, String levelName) {
    this.commentData = new CommentData(username, levelName, DEFAULT_DATE, DEFAULT_COMMENT, DEFAULT_REPLIES);
    this.leaderboardData = new LeaderboardData(username, levelName, DEFAULT_DATE, DEFAULT_TIME);
  }

  /**
   * Adds a comment to the game session.
   */
  public CommentData getLevelCommentData() {
    return this.commentData;
  }

  /**
   * Ends the game session.
   *
   * @return leaderboard data
   */
  public LeaderboardData getLeaderboardData() {
    return this.leaderboardData;
  }

}
