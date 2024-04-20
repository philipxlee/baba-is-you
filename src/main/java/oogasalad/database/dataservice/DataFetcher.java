package oogasalad.database.dataservice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.LeaderboardData;
import oogasalad.database.gamedata.ReplySchema;
import oogasalad.shared.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

/**
 * Fetches data from the MongoDB database.
 */
public class DataFetcher {

  private static final Logger logger = LogManager.getLogger(DataFetcher.class);
  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";
  private static final int DISPLAY_LIMIT = 10;
  private static final int NONE = 0;

  private final MongoDatabase database;
  private final Properties properties;

  /**
   * Constructor for the DataFetcher class.
   *
   * @param database the database
   */
  public DataFetcher(MongoDatabase database) {
    this.database = database;
    this.properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);
  }

  /**
   * Checks if the username is available.
   *
   * @param username the username to check
   * @return true if the username is available, false otherwise
   */
  public boolean isUsernameAvailable(String username) {
    MongoCollection<Document> collection = database.getCollection(
        properties.getProperty("collection.data"));
    long count = collection.countDocuments(
        Filters.eq(properties.getProperty("field.username"), username));
    return count == NONE;
  }

  /**
   * Returns an iterator over the top players for a given level.
   * @param currentLevelName the name of the current level
   * @return an iterator over the leaderboard data
   */
  public Iterator<LeaderboardData> getTopPlayersIterator(String currentLevelName) {
    MongoCollection<Document> collection = database.getCollection(properties.getProperty("collection.data"));
    Iterable<Document> documents = collection
        .find(Filters.eq(properties.getProperty("field.levelName"), currentLevelName))
        .sort(Sorts.ascending(properties.getProperty("field.timeSpent")))
        .limit(DISPLAY_LIMIT);

    logger.info("Retrieved top players for level: " + currentLevelName);

    return StreamSupport.stream(documents.spliterator(), false)
        .map(document -> new LeaderboardData(
            document.getString(properties.getProperty("field.username")),
            document.getString(properties.getProperty("field.levelName")),
            document.getDate(properties.getProperty("field.date")),
            document.getLong(properties.getProperty("field.timeSpent"))
        )).iterator();
  }

  /**
   * Gets the comments for the current level.
   *
   * @param currentLevelName the name of the current level
   * @return a list of the comments
   */
  public Iterator<CommentData> getLevelCommentsIterator(String currentLevelName) {
    MongoCollection<Document> collection = database.getCollection(properties.getProperty("collection.comments"));
    Iterable<Document> documents = collection
        .find(Filters.eq(properties.getProperty("field.levelName"), currentLevelName))
        .limit(DISPLAY_LIMIT);

    logger.info("Retrieved comments for level: " + currentLevelName);

    return StreamSupport.stream(documents.spliterator(), false)
        .map(this::documentToCommentData)
        .iterator();
  }

  private CommentData documentToCommentData(Document document) {
    String username = document.getString(properties.getProperty("field.username"));
    String levelName = document.getString(properties.getProperty("field.levelName"));
    Date date = document.getDate(properties.getProperty("field.date"));
    String comment = document.getString(properties.getProperty("field.comment"));
    List<ReplySchema> replies = extractReplies(document);
    return new CommentData(username, levelName, date, comment, replies);
  }

  private List<ReplySchema> extractReplies(Document document) {
    List<Document> replyDocs = document.getList(properties.getProperty("field.replies"),
        Document.class);
    return replyDocs == null ? new ArrayList<>() : replyDocs.stream()
        .map(doc -> new ReplySchema(
            doc.getString(properties.getProperty("field.username")), null,
            doc.getDate(properties.getProperty("field.date")),
            doc.getString(properties.getProperty("field.reply"))))
        .collect(Collectors.toList());
  }
}
