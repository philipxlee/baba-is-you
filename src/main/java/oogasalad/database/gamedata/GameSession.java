package oogasalad.database.gamedata;

import java.util.ArrayList;
import java.util.Date;

public class GameSession {

  private static final Date DEFAULT_DATE = new Date();
  private static final String DEFAULT_COMMENT = "";
  private static final ArrayList<ReplySchema> DEFAULT_REPLIES = new ArrayList<>();
  private static final int DEFAULT_TIME = 0;

  private final CommentData commentData;
  private final LeaderboardData leaderboardData;

  public GameSession(String username, String levelName) {
    this.commentData = new CommentData(username, levelName, DEFAULT_DATE, DEFAULT_COMMENT, DEFAULT_REPLIES);
    this.leaderboardData = new LeaderboardData(username, levelName, DEFAULT_DATE, DEFAULT_TIME);
  }

  public CommentData getLevelCommentData() {
    return this.commentData;
  }

  public LeaderboardData getLeaderboardData() {
    return this.leaderboardData;
  }

}
