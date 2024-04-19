package oogasalad.database.gamedata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

public class CommentData {

  private final String username;
  private final String levelName;
  private final Date date;
  private final String comment;
  private final List<ReplySchema> replies;  // Change to use Reply objects

  public CommentData(String username, String levelName, Date date, String comment,
      ArrayList<ReplySchema> replies) {
    this.username = username;
    this.levelName = levelName;
    this.date = date;
    this.comment = comment;
    this.replies = new ArrayList<>(replies);
  }

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

  public String getUsername() {
    return username;
  }

  public String getLevelName() {
    return levelName;
  }

  public String getDate() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    return dateFormat.format(date);
  }

  public String getComment() {
    return comment;
  }

  public String getReplies() {
    List<String> formattedReplies = new ArrayList<>();
    for (ReplySchema reply : replies) {
      formattedReplies.add(reply.getUsername() + ": " + reply.getReplyText() + " (" + reply.getFormattedReplyDate() + ")");
    }
    return formattedReplies.toString();
  }
}
