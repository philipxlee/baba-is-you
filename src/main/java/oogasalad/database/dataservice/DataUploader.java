package oogasalad.database.dataservice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import java.util.Properties;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.GameSession;
import oogasalad.database.gamedata.LeaderboardData;
import oogasalad.shared.util.PropertiesLoader;
import org.bson.Document;

public class DataUploader {

  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";
  private static final String GUEST_USERNAME = "Anonymous";

  private final MongoDatabase database;
  private final GameSession gameSession;
  private final Properties properties;

  /**
   * Constructor for the DataUploader class.
   *
   * @param database    the database interface
   * @param gameSession the active game session
   */
  public DataUploader(MongoDatabase database, GameSession gameSession) {
    this.database = database;
    this.gameSession = gameSession;
    this.properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);  // Load properties
  }

  /**
   * Saves the player leaderboard data to the database.
   */
  public void savePlayerLeaderboardData(long startTime, long endTime) {
    MongoCollection<Document> collection = database.getCollection(
        properties.getProperty("collection.data"));
    LeaderboardData leaderboardData = gameSession.getLeaderboardData();
    Document leaderboardDocument = leaderboardData.toDocument();
    leaderboardDocument.append(properties.getProperty("field.timeSpent"),
        endTime - startTime);
    collection.insertOne(leaderboardDocument);
    System.out.println("Player data saved successfully.");
  }

  /**
   * Saves the level comment to the database.
   */
  public void saveLevelComment(String comment) {
    MongoCollection<Document> collection = database.getCollection(
        properties.getProperty("collection.comments"));
    CommentData commentData = gameSession.getCommentData();
    Document commentDocument = commentData.toDocument();
    commentDocument.append(properties.getProperty("field.comment"),
        comment);
    collection.insertOne(commentDocument);
    System.out.println("Level comments saved successfully.");
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
    System.out.println("Reply added successfully.");
  }

  private MongoCollection<Document> getCommentsCollection() {
    return database.getCollection(properties.getProperty("collection.comments"));
  }

  private Document createReplyDocument(String playerUsername, String reply) {
    return new Document(properties.getProperty("field.username"), validateUsername(playerUsername))
        .append(properties.getProperty("field.reply"), reply)
        .append(properties.getProperty("field.date"), new Date());
  }

  private void updateCommentWithReply(MongoCollection<Document> collection,
      String commenterUsername, Document replyDocument) {
    Document updateOperation = new Document("$push",
        new Document(properties.getProperty("field.replies"), replyDocument));
    Document filter = new Document(properties.getProperty("field.username"), commenterUsername);
    collection.updateOne(filter, updateOperation);
  }

  private String validateUsername(String username) {
    return username == null ? GUEST_USERNAME : username;
  }
}
