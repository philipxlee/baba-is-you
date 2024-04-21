package oogasalad.database.gamedata;

import java.util.ArrayList;
import java.util.Date;

/**
 * Manages the session state for a game, including comments and leaderboard data.
 *
 * @author Philip Lee.
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
   * @param username  The username of the player.
   * @param levelName The name of the level.
   */
  public GameSession(String username, String levelName) {
    this.leaderboardData = new LeaderboardData(username, levelName, DEFAULT_DATE, DEFAULT_TIME);
    this.commentData = new CommentData(username, levelName, DEFAULT_DATE, DEFAULT_COMMENT,
        DEFAULT_REPLIES);
  }

  /**
   * Retrieves the comment data associated with this session.
   *
   * @return The comment data for this game session.
   */
  public CommentData getCommentData() {
    return this.commentData;
  }

  /**
   * Retrieves the leaderboard data associated with this session.
   *
   * @return The leaderboard data for this game session.
   */
  public LeaderboardData getLeaderboardData() {
    return this.leaderboardData;
  }
}
