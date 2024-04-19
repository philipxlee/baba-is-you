package oogasalad.database.gamedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

/**
 * Stores and manages comment data for a game session, including replies.
 */
public class CommentData extends AbstractGameData {
  private String comment;
  private List<ReplySchema> replies;

  /**
   * Constructor for the CommentData class.
   *
   * @param username  the username of the person who made the comment
   * @param levelName the level at which the comment was made
   * @param date      the date the comment was made
   * @param comment   the text of the comment
   * @param replies   a list of replies to the comment
   */
  public CommentData(String username, String levelName, Date date, String comment, List<ReplySchema> replies) {
    super(username, levelName, date);
    this.comment = comment;
    this.replies = new ArrayList<>(replies);  // Ensure a new list is created to avoid external modifications
  }

  @Override
  public Document toDocument() {
    List<Document> replyDocs = new ArrayList<>();
    for (ReplySchema reply : replies) {
      replyDocs.add(reply.toDocument());
    }
    return new Document("username", username)
        .append("levelName", levelName)
        .append("date", date)
        .append("comment", comment)
        .append("replies", replyDocs);
  }

  /**
   * Gets the text of the comment.
   *
   * @return the comment text
   */
  public String getComment() {
    return comment;
  }

  /**
   * Gets the list of replies to this comment.
   *
   * @return a list of ReplySchema objects representing replies to this comment
   */
  public List<ReplySchema> getReplies() {
    return new ArrayList<>(replies);  // Return a copy to preserve encapsulation
  }

  /**
   * Adds a reply to the comment.
   *
   * @param reply a ReplySchema object containing the reply to add
   */
  public void addReply(ReplySchema reply) {
    this.replies.add(reply);
  }
}
