package oogasalad.database;

import java.util.Date;
import org.bson.Document;

/**
 * Represents data about a player in the game, including time spent and comments on levels.
 */
public class PlayerData {
  private String username;
  private String comments;
  private long timeSpent;
  private Date date;

  /**
   * Constructs a new PlayerData object with the given username.
   *
   * @param username The username of the player.
   */
  public PlayerData(String username, long timeSpent, String comments, Date date) {
    this.username = username;
    this.timeSpent = timeSpent;
    this.comments = comments;
    this.date = date;
  }

  /**
   * Constructs a new PlayerData object with the given username, time spent, and comments.
   *
   * @return A new PlayerData object.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of this PlayerData.
   *
   * @param username The new username.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns the time spent by the player.
   *
   * @return The time spent by the player.
   */
  public long getTimeSpent() {
    return timeSpent;
  }

  /**
   * Sets the time spent by the player.
   *
   * @param timeSpent The new time spent by the player.
   */
  public void setTimeSpent(long timeSpent) {
    this.timeSpent = timeSpent;
  }

  /**
   * Returns the comments made by the player.
   *
   * @return The comments made by the player.
   */
  public String getComments() {
    return comments;
  }

  /**
   * Sets the comments made by the player.
   *
   * @param comments The new comments made by the player.
   */
  public void setComments(String comments) {
    this.comments = comments;
  }

  /**
   * Converts this PlayerData into a MongoDB Document.
   *
   * @return A Document representing this PlayerData.
   */
  public Document getDocument() {
    return new Document("username", username)
        .append("timeSpent", timeSpent)
        .append("comments", comments)
        .append("date", date);
  }
}
