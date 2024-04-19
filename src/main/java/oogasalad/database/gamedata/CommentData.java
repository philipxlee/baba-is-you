package oogasalad.database.gamedata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

/**
 * This class is responsible for storing the comment data.
 */
public class CommentData {

  private final String username;
  private final String levelName;
  private final Date date;
  private final String comment;
  private final List<ReplySchema> replies;  // Change to use Reply objects

  /**
   * Constructor for the CommentData class.
   *
   * @param username  username
   * @param levelName level name
   * @param date      date
   * @param comment   comment
   * @param replies   replies
   */
  public CommentData(String username, String levelName, Date date, String comment,
      List<ReplySchema> replies) {
    this.username = username;
    this.levelName = levelName;
    this.date = date;
    this.comment = comment;
    this.replies = new ArrayList<>(replies);
  }

  /**
   * Constructor for the CommentData class.
   *
   * @param newComment new comment
   * @return document
   */
  public Document getCommentDocument(String newComment) {
    List<Document> replyDocs = new ArrayList<>();
    for (ReplySchema reply : replies) {
      replyDocs.add(reply.toDocument());
    }
    return new Document("username", username)
        .append("levelName", levelName)
        .append("date", date)
        .append("comment", newComment)
        .append("replies", replyDocs);
  }

  /**
   * Gets the replies.
   *
   * @return replies
   */
  public String getReplies() {
    List<String> formattedReplies = new ArrayList<>();
    for (ReplySchema reply : replies) {
      formattedReplies.add(
          reply.getUsername() + ": " + reply.getReplyText() + " (" + reply.getFormattedReplyDate()
              + ")");
    }
    return formattedReplies.toString();
  }

  /**
   * Adds a reply to the comment.
   *
   * @return comment
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
   * Gets the date.
   *
   * @return date
   */
  public String getDate() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    return dateFormat.format(date);
  }

  /**
   * Gets the comment.
   *
   * @return comment
   */
  public String getComment() {
    return comment;
  }
}
