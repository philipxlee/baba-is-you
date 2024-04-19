package oogasalad.database.dataservice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.LeaderboardData;
import oogasalad.database.gamedata.ReplySchema;
import org.bson.Document;

/**
 * This class is responsible for fetching data from the database.
 */
public class DataFetcher {

  private final MongoDatabase database;

  /**
   * Constructor for the DataFetcher class.
   *
   * @param database database
   */
  public DataFetcher(MongoDatabase database) {
    this.database = database;
  }

  /**
   * Checks if the username is available.
   *
   * @param username username
   * @return true if the username is available, false otherwise
   */
  public boolean isUsernameAvailable(String username) {
    MongoCollection<Document> collection = database.getCollection("data");
    long count = collection.countDocuments(Filters.eq("username", username));
    return count == 0;
  }

  /**
   * Gets the top players for the current level.
   *
   * @param currentLevelName current level name
   * @return list of top players
   */
  public List<LeaderboardData> getTopPlayers(String currentLevelName) {
    MongoCollection<Document> collection = database.getCollection("data");
    return StreamSupport.stream(collection
            .find(Filters.eq("levelName", currentLevelName))
            .sort(Sorts.ascending("timeSpent"))
            .limit(10)
            .spliterator(), false)
        .map(document -> new LeaderboardData(
            document.getString("username"),
            document.getString("levelName"),
            document.getDate("date"),
            document.getLong("timeSpent")))
        .collect(Collectors.toList());
  }

  /**
   * Gets the comments for the current level.
   *
   * @param currentLevelName current level name
   * @return list of comments
   */
  public List<CommentData> getLevelComments(String currentLevelName) {
    MongoCollection<Document> collection = database.getCollection("comment");
    return StreamSupport.stream(collection
            .find(Filters.eq("levelName", currentLevelName))
            .spliterator(), false)
        .map(this::documentToCommentData)
        .collect(Collectors.toList());
  }

  private CommentData documentToCommentData(Document document) {
    String username = document.getString("username");
    String levelName = document.getString("levelName");
    Date date = document.getDate("date");
    String comment = document.getString("comment");
    List<ReplySchema> replies = extractReplies(document);

    return new CommentData(username, levelName, date, comment, replies);
  }

  private List<ReplySchema> extractReplies(Document document) {
    List<Document> replyDocs = document.getList("replies", Document.class);
    if (replyDocs == null) {
      return new ArrayList<>();
    }
    return replyDocs.stream()
        .map(this::documentToReplySchema)
        .collect(Collectors.toList());
  }

  private ReplySchema documentToReplySchema(Document replyDoc) {
    String replyUsername = replyDoc.getString("username");
    String replyText = replyDoc.getString("reply");
    Date replyDate = replyDoc.getDate("date");
    return new ReplySchema(replyUsername, replyText, replyDate);
  }

}
