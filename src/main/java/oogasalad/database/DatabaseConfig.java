package oogasalad.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * DatabaseConfig is a class that manages the connection to the MongoDB database.
 */
public class DatabaseConfig {

  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";
  private MongoClient mongoClient;
  private String databaseName;

  /**
   * Constructs a new DatabaseConfig object.
   */
  public DatabaseConfig() {
    createDatabaseConnection();
  }

  /**
   * Returns the DataManager object for this DatabaseConfig.
   *
   * @return The DataManager object for this DatabaseConfig.
   */
  public MongoDatabase getDatabase() {
    return mongoClient.getDatabase(databaseName);
  }

  /**
   * Returns the DataManager object for this DatabaseConfig.
   */
  public void close() {
    if (mongoClient != null) {
      mongoClient.close();
    }
  }

  private void createDatabaseConnection() {
    Properties properties = loadProperties();
    String connectionString = properties.getProperty("mongo.connection.string");
    this.databaseName = properties.getProperty("mongo.database.name");
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(new ConnectionString(connectionString))
        .build();
    this.mongoClient = MongoClients.create(settings);
  }

  private Properties loadProperties() {
    Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader()
        .getResourceAsStream(DATABASE_PROPERTIES_PATH)) {
      if (input == null) {
        throw new IllegalStateException("Failed to load database properties file.");
      }
      properties.load(input);
    } catch (IOException ex) {
      throw new RuntimeException("Error loading database properties", ex);
    }
    return properties;
  }
}
