package oogasalad.database.records;

import java.util.Date;

public record LeaderboardRecord(String username, String levelName, Date date, long timeSpent) {

  public String getLeaderboardUsername() {
    return username;
  }

  public String getLeaderboardLevelName() {
    return levelName;
  }

  public Date getLeaderboardDate() {
    return date;
  }

  public long getLeaderboardTimeSpent() {
    return timeSpent;
  }
}


