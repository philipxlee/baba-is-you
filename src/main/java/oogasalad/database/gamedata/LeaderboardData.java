package oogasalad.database.gamedata;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bson.Document;

/**
 * This class is responsible for storing the leaderboard data.
 */
public class LeaderboardData {

  private final String username;
  private final String levelName;
  private Date date;
  private long timeSpent;

  /**
   * Constructor for the LeaderboardData class.
   *
   * @param username  username
   * @param levelName level name
   * @param date      date
   * @param timeSpent time spent
   */
  public LeaderboardData(String username, String levelName, Date date, long timeSpent) {
    this.username = username;
    this.levelName = levelName;
    this.date = date;
    this.timeSpent = timeSpent;
  }

  /**
   * Constructor for the LeaderboardData class.
   *
   * @param startTime start time
   * @param endTime   end time
   * @return document
   */
  public Document getLeaderboardDocument(long startTime, long endTime) {
    Document doc = new Document();
    doc.append("username", username);
    doc.append("levelName", levelName);
    doc.append("date", date);
    doc.append("timeSpent", endTime - startTime);
    return doc;
  }

  /**
   * Gets the date.
   *
   * @return date
   */
  public String getDate() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, dd yyyy");
    return dateFormat.format(date);
  }

  /**
   * Gets the username.
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the level name.
   *
   * @return level name
   */
  public String getLevelName() {
    return levelName;
  }

  /**
   * Gets the time spent.
   *
   * @return time spent
   */
  public long getTimeSpent() {
    return timeSpent;
  }
}
