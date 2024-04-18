package oogasalad.database;

import java.util.Date;
import java.text.SimpleDateFormat;

public class LeaderboardData {
  private String username;
  private String levelPlayed;
  private Date date;
  private long timeSpent;

  public LeaderboardData(String username, Date date, String levelPlayed, long timeSpent) {
    this.username = username;
    this.timeSpent = timeSpent;
    this.levelPlayed = levelPlayed;
    this.date = date;
  }

  public String getUsername() {
    return username;
  }

  public String getLevelName() {
    return levelPlayed;
  }

  public String getDate() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, dd yyyy");
    return dateFormat.format(date);
  }

  public long getTimeSpent() {
    return timeSpent;
  }

}
