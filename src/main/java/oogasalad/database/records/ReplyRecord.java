package oogasalad.database.records;

import java.util.Date;

/**
 * Represents a reply within a comment in the game data.
 *
 * @param username  username
 * @param levelName level name
 * @param date      date
 * @param replyText reply text
 * @author Philip Lee.
 */
public record ReplyRecord(String username, String levelName, Date date, String replyText) {

  /**
   * Gets the username of the reply.
   *
   * @return The username of the reply.
   */
  public String getReplyUsername() {
    return username;
  }

  /**
   * Gets the level name of the reply.
   *
   * @return The level name of the reply.
   */
  public String getReplyLevelName() {
    return levelName;
  }

  /**
   * Gets the date of the reply.
   *
   * @return The date of the reply.
   */
  public Date getReplyDate() {
    return date;
  }

  /**
   * Gets the reply text.
   *
   * @return The reply text.
   */
  public String getReplyText() {
    return replyText;
  }

}
