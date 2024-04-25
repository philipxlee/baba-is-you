package oogasalad.database.dataservice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import java.util.Properties;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.GameSession;
import oogasalad.database.gamedata.LeaderboardData;
import oogasalad.shared.loader.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

/**
 * Uploads data to the MongoDB database.
 *
 * @author Philip Lee.
 */
public class DataUploader {

  private static final Logger logger = LogManager.getLogger(DataUploader.class);
  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";
  private static final String GUEST_USERNAME = "Anonymous";
  private static final String COLLECTION_DATA = "collection.data";
  private static final String COLLECTION_COMMENTS = "collection.comments";
  private static final String FIELD_USERNAME = "field.username";
  private static final String FIELD_TIME_SPENT = "field.timeSpent";
  private static final String FIELD_COMMENT = "field.comment";
  private static final String FIELD_DATE = "field.date";
  private static final String FIELD_REPLIES = "field.replies";
  private static final String FIELD_REPLY = "field.reply";
  private static DataUploader instance;
  private final MongoDatabase database;
  private final Properties properties;
  private GameSession gameSession;

  /**
   * Private constructor for the DataUploader class.
   *
   * @param database the database interface
   */
  private DataUploader(MongoDatabase database) {
    this.database = database;
    this.properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);  // Load properties
  }

  /**
   * Provides the global access point to the singleton instance of the DataUploader. This uses the
   * singleton pattern to ensure that only one instance of the DataUploader is created.
   *
   * @param database the database instance required to create the DataUploader
   * @return the singleton instance of the DataUploader
   */
  public static DataUploader getInstance(MongoDatabase database) {
    if (instance == null) {
      instance = new DataUploader(database);
    }
    return instance;
  }

  public void setGameSession(GameSession gameSession) {
    this.gameSession = gameSession;
  }

  /**
   * Saves the player leaderboard data to the database.
   */
  public void savePlayerLeaderboardData(long startTime, long endTime) {
    MongoCollection<Document> collection = database
        .getCollection(properties.getProperty(COLLECTION_DATA));
    Document leaderboardDocument = buildLeaderboardDocument(startTime, endTime);
    collection.insertOne(leaderboardDocument);
    logger.info("Player data saved successfully.");
  }

  /**
   * Saves the level comment to the database.
   */
  public void saveLevelComment(String comment) {
    MongoCollection<Document> collection = database.getCollection(
        properties.getProperty(COLLECTION_COMMENTS)
    );
    CommentData commentData = gameSession.getCommentData();
    Document commentDocument = commentData.toDocument();
    commentDocument.append(properties.getProperty(FIELD_COMMENT), comment);
    collection.insertOne(commentDocument);
    logger.info("Level comments saved successfully.");
  }

  /**
   * Adds a reply to a user comment.
   *
   * @param commenterUsername username of the commenter
   * @param playerUsername    username of the player replying
   * @param reply             the reply text
   */
  public void addReplyToUserComment(String commenterUsername, String playerUsername, String reply) {
    MongoCollection<Document> commentsCollection = getCommentsCollection();
    Document replyDocument = createReplyDocument(playerUsername, reply);
    updateCommentWithReply(commentsCollection, commenterUsername, replyDocument);
    logger.info("Reply added successfully.");
  }

  /**
   * Builds the leaderboard document.
   *
   * @param startTime start time
   * @param endTime  end time
   * @return the leaderboard document
   */
  private Document buildLeaderboardDocument(long startTime, long endTime) {
    LeaderboardData leaderboardData = gameSession.getLeaderboardData();
    Document leaderboardDocument = leaderboardData.toDocument();
    leaderboardDocument.append(properties.getProperty(FIELD_TIME_SPENT), endTime - startTime);
    return leaderboardDocument;
  }

  /**
   * Creates a reply document.
   *
   * @param playerUsername player username
   * @param reply          reply text
   * @return the reply document
   */
  private Document createReplyDocument(String playerUsername, String reply) {
    return new Document(properties.getProperty(FIELD_USERNAME), validateUsername(playerUsername))
        .append(properties.getProperty(FIELD_REPLY), reply)
        .append(properties.getProperty(FIELD_DATE), new Date());
  }

  /**
   * Updates the comment with a reply.
   *
   * @param collection        collection to update
   * @param commenterUsername username of the commenter
   * @param replyDocument     the reply document
   */
  private void updateCommentWithReply(MongoCollection<Document> collection,
      String commenterUsername, Document replyDocument) {
    Document updateOperation = new Document("$push",
        new Document(properties.getProperty(FIELD_REPLIES), replyDocument));
    Document filter = new Document(properties.getProperty(FIELD_USERNAME), commenterUsername);
    collection.updateOne(filter, updateOperation);
  }

  /**
   * Gets the comments collection.
   *
   * @return the comments collection
   */
  private MongoCollection<Document> getCommentsCollection() {
    return database.getCollection(properties.getProperty(COLLECTION_COMMENTS));
  }

  /**
   * Validates the username.
   *
   * @param username the username to validate
   * @return the validated username
   */
  private String validateUsername(String username) {
    return username == null ? GUEST_USERNAME : username;
  }
}
