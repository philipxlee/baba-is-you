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
import oogasalad.shared.loader.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

/**
 * Fetches data from the MongoDB database.
 *
 * @author Philip Lee.
 */
public class DataFetcher {

  private static final Logger logger = LogManager.getLogger(DataFetcher.class);
  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";
  private static final String COLLECTION_DATA = "collection.data";
  private static final String COLLECTION_COMMENTS = "collection.comments";
  private static final String FIELD_USERNAME = "field.username";
  private static final String FIELD_LEVEL_NAME = "field.levelName";
  private static final String FIELD_TIME_SPENT = "field.timeSpent";
  private static final String FIELD_DATE = "field.date";
  private static final String FIELD_COMMENT = "field.comment";
  private static final String FIELD_REPLIES = "field.replies";
  private static final String FIELD_REPLY = "field.reply";
  private static final String UNKNOWN_NAME = "Unknown";
  private static final int DISPLAY_LIMIT = 10;
  private static final int NONE = 0;
  private static DataFetcher instance;

  private final MongoDatabase database;
  private final Properties properties;

  /**
   * Provides the global access point to the singleton instance of the DataFetcher. This uses the
   * singleton pattern to ensure that only one instance of the DataFetcher is created.
   *
   * @param database the database instance required to create the DataFetcher
   * @return the singleton instance of the DataFetcher
   */
  public static DataFetcher getInstance(MongoDatabase database) {
    if (instance == null) {
      instance = new DataFetcher(database);
    }
    return instance;
  }

  /**
   * Checks if the username is available.
   *
   * @param username the username to check
   * @return true if the username is available, false otherwise
   */
  public boolean isUsernameAvailable(String username) {
    MongoCollection<Document> collection = database
        .getCollection(properties.getProperty(COLLECTION_DATA));
    long count = collection
        .countDocuments(Filters.eq(properties.getProperty(FIELD_USERNAME), username));
    return count == NONE;
  }

  /**
   * Returns an iterator over the top players for a given level.
   *
   * @param currentLevelName the name of the current level
   * @return an iterator over the leaderboard data
   */
  public Iterator<LeaderboardData> getTopPlayersIterator(String currentLevelName) {
    MongoCollection<Document> collection = database
        .getCollection(properties.getProperty(COLLECTION_DATA));

    Iterable<Document> documents = collection
        .find(Filters.eq(properties.getProperty(FIELD_LEVEL_NAME), currentLevelName))
        .sort(Sorts.ascending(properties.getProperty(FIELD_TIME_SPENT)))
        .limit(DISPLAY_LIMIT);

    logger.info("Retrieved top players for level: " + currentLevelName);

    return StreamSupport.stream(documents.spliterator(), false)
        .map(document -> new LeaderboardData(
            document.getString(properties.getProperty(FIELD_USERNAME)),
            document.getString(properties.getProperty(FIELD_LEVEL_NAME)),
            document.getDate(properties.getProperty(FIELD_DATE)),
            document.getLong(properties.getProperty(FIELD_TIME_SPENT))
        )).iterator();
  }

  /**
   * Gets the comments for the current level.
   *
   * @param currentLevelName the name of the current level
   * @return a list of the comments
   */
  public Iterator<CommentData> getLevelCommentsIterator(String currentLevelName) {
    MongoCollection<Document> collection = database
        .getCollection(properties.getProperty(COLLECTION_COMMENTS));

    Iterable<Document> documents = collection
        .find(Filters.eq(properties.getProperty(FIELD_LEVEL_NAME), currentLevelName))
        .limit(DISPLAY_LIMIT);

    logger.info("Retrieved comments for level: " + currentLevelName);

    return StreamSupport.stream(documents.spliterator(), false)
        .map(this::documentToCommentData)
        .iterator();
  }



  /**
   * Converts a document to a CommentData object.
   *
   * @param document the document to convert
   * @return the CommentData object
   */
  private CommentData documentToCommentData(Document document) {
    String username = document.getString(properties.getProperty(FIELD_USERNAME));
    String levelName = document.getString(properties.getProperty(FIELD_LEVEL_NAME));
    String comment = document.getString(properties.getProperty(FIELD_COMMENT));
    Date date = document.getDate(properties.getProperty(FIELD_DATE));
    List<ReplySchema> replies = extractReplies(document);
    return new CommentData(username, levelName, date, comment, replies);
  }

  /**
   * Extracts the replies from a document.
   *
   * @param document the document
   * @return the list of replies
   */
  private List<ReplySchema> extractReplies(Document document) {
    List<Document> replyDocs = document.getList(properties.getProperty(FIELD_REPLIES),
        Document.class);
    return replyDocs == null ? new ArrayList<>() : replyDocs.stream()
        .map(doc -> new ReplySchema(
            doc.getString(properties.getProperty(FIELD_USERNAME)), UNKNOWN_NAME,
            doc.getDate(properties.getProperty(FIELD_DATE)),
            doc.getString(properties.getProperty(FIELD_REPLY))))
        .collect(Collectors.toList());
  }

  /**
   * Private constructor for the DataFetcher class.
   *
   * @param database the database
   */
  private DataFetcher(MongoDatabase database) {
    this.database = database;
    this.properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);
  }

}
