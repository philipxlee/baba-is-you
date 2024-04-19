package oogasalad.database.gamedata;

public class GameSession {

  private final CommentData commentData;
  private final LeaderboardData leaderboardData;

  public GameSession(String username, String levelName) {
    this.commentData = new CommentData();
    this.leaderboardData = new LeaderboardData();
  }

}
