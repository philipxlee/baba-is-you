package oogasalad.database;

import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;

/**
 * Represents data about a player in the game, including time spent and comments on levels.
 */
public class PlayerData {
  private String username;
  private String comments;
  private Date date;
  private String levelPlayed;
  private String reply;
  private long timeSpent;

  /**
   * Constructs a new PlayerData object with the given username.
   *
   * @param username The username of the player.
   */
  public PlayerData(String username, String comments, Date date, String levelPlayed, String reply, long timeSpent) {
    this.username = username;
    this.timeSpent = timeSpent;
    this.comments = comments;
    this.levelPlayed = levelPlayed;
    this.reply = reply;
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
   * Returns the date of this PlayerData.
   *
   * @return The date of this PlayerData.
   */
  public String getLevelPlayed() {
    return levelPlayed;
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

  public Document getLevelComments() {
   return new Document("username", username)
        .append("comment", comments)
        .append("date", new Date())
        .append("replies", new ArrayList<>());
  }


  public Document getReply() {
    return new Document("username", username)
        .append("reply", reply)
        .append("date", new Date());
  }

}
