package oogasalad.database.gamedata;

import java.util.Date;
import java.util.Properties;
import oogasalad.shared.loader.PropertiesLoader;
import org.bson.Document;

/**
 * Stores and manages leaderboard data for a game session.
 *
 * @author Philip Lee.
 */
public class LeaderboardData extends AbstractGameData {

  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";
  private final Properties properties;
  private final long timeSpent;

  /**
   * Constructor for the LeaderboardData class.
   *
   * @param username  The username of the player.
   * @param levelName The name of the level.
   * @param date      The date the game was played.
   * @param timeSpent The time spent in the game.
   */
  public LeaderboardData(String username, String levelName, Date date, long timeSpent) {
    super(username, levelName, date);
    this.timeSpent = timeSpent;
    this.properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);
  }

  /**
   * Converts the leaderboard data to a Document.
   *
   * @return The leaderboard data as a Document.
   */
  @Override
  public Document toDocument() {
    return new Document(properties.getProperty("field.username"), getUsername())
        .append(properties.getProperty("field.levelName"), getLevelName())
        .append(properties.getProperty("field.date"), getDate())
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
