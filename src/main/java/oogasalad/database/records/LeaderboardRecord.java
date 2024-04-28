package oogasalad.database.records;

import java.util.Date;

/**
 * Represents a record in the leaderboard.
 *
 * @param username  the username of the player
 * @param levelName the name of the level
 * @param date      the date of the record
 * @param timeSpent the time spent on the level
 * @author Philip Lee.
 */
public record LeaderboardRecord(String username, String levelName, Date date, long timeSpent) {

  /**
   * Gets the username of the leaderboard record.
   *
   * @return The username of the leaderboard record.
   */
  public String getLeaderboardUsername() {
    return username;
  }

  /**
   * Gets the level name of the leaderboard record.
   *
   * @return The level name of the leaderboard record.
   */
  public String getLeaderboardLevelName() {
    return levelName;
  }

  /**
   * Gets the date of the leaderboard record.
   *
   * @return The date of the leaderboard record.
   */
  public Date getLeaderboardDate() {
    return date;
  }

  /**
   * Gets the time spent on the level.
   *
   * @return The time spent on the level.
   */
  public long getLeaderboardTimeSpent() {
    return timeSpent;
  }
}


