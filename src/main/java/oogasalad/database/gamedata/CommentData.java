package oogasalad.database.gamedata;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import oogasalad.database.records.CommentRecord;
import oogasalad.shared.loader.PropertiesLoader;
import org.bson.Document;


/**
 * Stores and manages comment data for a game session, including replies.
 *
 * @author Philip Lee.
 */
public class CommentData extends AbstractGameData {

  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";
  private static final String FIELD_USERNAME = "field.username";
  private static final String FIELD_LEVEL_NAME = "field.levelName";
  private static final String FIELD_DATE = "field.date";
  private static final String FIELD_COMMENT = "field.comment";
  private static final String FIELD_REPLIES = "field.replies";

  private final Properties properties;
  private final String comment;
  private final List<ReplySchema> replies;

  /**
   * Constructor for the CommentData class.
   *
   * @param commentRecord the record of the comment data.
   */
  public CommentData(CommentRecord commentRecord) {
    super(commentRecord.getCommentUsername(), commentRecord.getCommentLevelName(),
        commentRecord.getCommentDate());
    this.comment = commentRecord.getCommentComments();
    this.replies = getRepliesFromCommentRecord(commentRecord);
    this.properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);
  }

  /**
   * Converts the comment data to a Document.
   *
   * @return The comment data as a Document.
   */
  @Override
  public Document toDocument() {
    List<Document> replyDocs = replies.stream()
        .map(ReplySchema::toDocument)
        .collect(Collectors.toList());

    return new Document(properties.getProperty(FIELD_USERNAME), getUsername())
        .append(properties.getProperty(FIELD_LEVEL_NAME), getLevelName())
        .append(properties.getProperty(FIELD_DATE), getDate())
        .append(properties.getProperty(FIELD_COMMENT), comment)
        .append(properties.getProperty(FIELD_REPLIES), replyDocs);
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
   * Provides an iterator over the replies to this comment.
   *
   * @return an iterator of ReplySchema objects representing replies to this comment
   */
  public Iterator<ReplySchema> getRepliesIterator() {
    return replies.iterator();
  }


  /**
   * Gets the replies from the comment record's iterator of ReplySchema objects.
   *
   * @param commentRecord the record of the comment data
   * @return a list of ReplySchema objects representing the replies to the comment
   */
  private List<ReplySchema> getRepliesFromCommentRecord(CommentRecord commentRecord) {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(commentRecord
            .getCommentReplies(), Spliterator.ORDERED), false)
        .collect(Collectors.toList());
  }

}
