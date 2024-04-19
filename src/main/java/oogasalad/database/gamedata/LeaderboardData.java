package oogasalad.database.gamedata;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bson.Document;

public class LeaderboardData {

  private final String username;
  private final String levelName;
  private Date date;
  private long timeSpent;

  public LeaderboardData(String username, String levelName, Date date, long timeSpent) {
    this.username = username;
    this.levelName = levelName;
    this.date = date;
    this.timeSpent = timeSpent;
  }

  public Document getLeaderboardDocument(long startTime, long endTime) {
    Document doc = new Document();
    doc.append("username", username);
    doc.append("levelName", levelName);
    doc.append("date", date);
    doc.append("timeSpent", endTime - startTime);
    return doc;
  }

  public String getUsername() {
    return username;
  }

  public String getLevelName() {
    return levelName;
  }

  public String getDate() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, dd yyyy");
    return dateFormat.format(date);
  }

  public long getTimeSpent() {
    return timeSpent;
  }
}
