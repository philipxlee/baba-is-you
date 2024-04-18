package oogasalad.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameSession {
  private PlayerData playerData;
  private List<CommentData> comments;
  private LeaderboardData leaderboardData;

  public GameSession(String username, String levelName) {
    this.playerData = new PlayerData(username);
    this.comments = new ArrayList<>();
    this.leaderboardData = new LeaderboardData(username, new Date(), levelName, 0);
  }

  public void addComment(String comment, String reply) {

  }

  public void endSession(long timeSpent) {

  }

}
