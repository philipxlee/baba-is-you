package oogasalad.database.gamedata;

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

  public Document getDocument(long startTime, long endTime) {
    Document doc = new Document();
    doc.append("username", username);
    doc.append("levelName", levelName);
    doc.append("date", date);
    doc.append("timeSpent", endTime - startTime);
    return doc;
  }

}
