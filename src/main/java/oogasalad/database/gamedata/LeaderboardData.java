package oogasalad.database.gamedata;

import java.util.Date;
import org.bson.Document;

/**
 * Stores and manages leaderboard data for a game session.
 */
public class LeaderboardData extends AbstractGameData {
  private long timeSpent;

  public LeaderboardData(String username, String levelName, Date date, long timeSpent) {
    super(username, levelName, date);
    this.timeSpent = timeSpent;
  }

  @Override
  public Document toDocument() {
    return new Document("username", username)
        .append("levelName", levelName)
        .append("date", date)
        .append("timeSpent", timeSpent);
  }

  /**
   * Gets the time spent.
   *
   * @return Time spent in the game.
   */
  public long getTimeSpent() {
    return timeSpent;
  }
}
