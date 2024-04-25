package oogasalad.database.records;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import oogasalad.database.gamedata.ReplySchema;

/**
 * Represents a comment within the game data.
 *
 * @param username  username
 * @param levelName level name
 * @param date      date
 * @param comment   comment
 * @param replies   replies
 * @author Philip Lee.
 */
public record CommentRecord(String username, String levelName, Date date, String comment,
                            List<ReplySchema> replies) {

  /**
   * Gets the username of the comment.
   *
   * @return The username of the comment.
   */
  public String getCommentUsername() {
    return username;
  }

  /**
   * Gets the level name of the comment.
   *
   * @return The level name of the comment.
   */
  public String getCommentLevelName() {
    return levelName;
  }

  /**
   * Gets the date of the comment.
   *
   * @return The date of the comment.
   */
  public Date getCommentDate() {
    return date;
  }

  /**
   * Gets the comment text.
   *
   * @return The comment text.
   */
  public String getCommentComments() {
    return comment;
  }

  /**
   * Gets the replies to the comment.
   *
   * @return The replies to the comment.
   */
  public Iterator<ReplySchema> getCommentReplies() {
    return replies.iterator();
  }
}

