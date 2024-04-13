package oogasalad.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import oogasalad.controller.gameplay.PlayerDataController;

/**
 * Configuration class for the MongoDB database connection.
 */
public class DatabaseConfig {

  private static final String PATH_TO_DATABASE_PROPERTIES = "database/database.properties";
  private static final Properties properties = loadProperties();
  private static final String DEFAULT_DATABASE_NAME = "BabaData";
  private static final MongoClient mongoClient;

  /**
   * Static block to initialize the MongoDB connection.
   */
  static {
    String connectionString = properties.getProperty("mongo.connection.string");
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(new ConnectionString(connectionString))
        .build();
    mongoClient = MongoClients.create(settings);
  }

  /**
   * Get the database instance.
   *
   * @return The database instance.
   */
  public static MongoDatabase getDatabase() {
    String dbName = properties.getProperty("mongo.database.name",
        DEFAULT_DATABASE_NAME);
    return mongoClient.getDatabase(dbName);
  }

  /**
   * Close the MongoDB connection.
   */
  public static void closeConnection() {
    if (mongoClient != null) {
      mongoClient.close();
    }
  }

  private static Properties loadProperties() {
    Properties properties = new Properties();
    try (InputStream input = DatabaseConfig.class.getClassLoader()
        .getResourceAsStream(PATH_TO_DATABASE_PROPERTIES)) {
      if (input == null) {
        throw new IllegalStateException("Failed to load database properties file.");
      }
      properties.load(input);
    } catch (IOException ex) {
      throw new RuntimeException("Error loading database properties", ex);
    }
    return properties;
  }

  /**
   * Temporary main method to test the database connection.
   */
  public static void main(String[] args) {
    MongoDatabase db = getDatabase();
    DataManager manager = new DataManager(db);
    PlayerDataController controller = new PlayerDataController(manager);
    controller.startNewPlayer("Bobby");
    controller.endPlayerSession("Great level!");
    DatabaseConfig.closeConnection();
  }

}
