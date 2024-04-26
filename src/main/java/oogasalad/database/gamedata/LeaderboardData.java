package oogasalad.database.gamedata;

import java.util.Date;
import java.util.Properties;
import oogasalad.database.records.LeaderboardRecord;
import org.bson.Document;

/**
 * Stores and manages leaderboard data for a game session.
 *
 * @author Philip Lee.
 */
public class LeaderboardData extends AbstractGameData {

  private static final String FIELD_USERNAME = "field.username";
  private static final String FIELD_LEVEL_NAME = "field.levelName";
  private static final String FIELD_TIME_SPENT = "field.timeSpent";
  private static final String FIELD_DATE = "field.date";
  private final long timeSpent;

  /**
   * Constructor for the LeaderboardData class.
   *
   * @param leaderboardRecord the record of the leaderboard data
   */
  public LeaderboardData(LeaderboardRecord leaderboardRecord) {
    super(leaderboardRecord.getLeaderboardUsername(), leaderboardRecord.getLeaderboardLevelName(), leaderboardRecord.getLeaderboardDate());
    this.timeSpent = leaderboardRecord.getLeaderboardTimeSpent();
  }


  /**
   * Converts the leaderboard data to a Document.
   *
   * @return The leaderboard data as a Document.
   */
  @Override
  public Document toDocument() {
    Properties properties = getDatabaseProperties();
    return new Document(properties.getProperty(FIELD_USERNAME), getUsername())
        .append(properties.getProperty(FIELD_LEVEL_NAME), getLevelName())
        .append(properties.getProperty(FIELD_DATE), new Date())
        .append(properties.getProperty(FIELD_TIME_SPENT), timeSpent);
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
