package oogasalad.database.gamedata;

import java.util.Date;
import java.util.Properties;
import oogasalad.shared.util.PropertiesLoader;
import org.bson.Document;

/**
 * Stores and manages leaderboard data for a game session.
 */
public class LeaderboardData extends AbstractGameData {

  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";
  private final Properties properties;
  private final long timeSpent;

  public LeaderboardData(String username, String levelName, Date date, long timeSpent) {
    super(username, levelName, date);
    this.timeSpent = timeSpent;
    this.properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);
  }

  @Override
  public Document toDocument() {
    return new Document(properties.getProperty("field.username"), username)
        .append(properties.getProperty("field.levelName"), levelName)
        .append(properties.getProperty("field.date"), date)
        .append(properties.getProperty("field.timeSpent"), timeSpent);
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
