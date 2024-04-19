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
 * Fetches data from the MongoDB database.
 */
public class DataFetcher {

  private final MongoDatabase database;

  public DataFetcher(MongoDatabase database) {
    this.database = database;
  }

  public boolean isUsernameAvailable(String username) {
    MongoCollection<Document> collection = database.getCollection("data");
    long count = collection.countDocuments(Filters.eq("username", username));
    return count == 0;
  }

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
    return replyDocs == null ? new ArrayList<>() : replyDocs.stream()
        .map(doc -> new ReplySchema(
            doc.getString("username"),
            null,  // Assume level name is not applicable to individual replies
            doc.getDate("date"),
            doc.getString("reply")))
        .collect(Collectors.toList());
  }
}
