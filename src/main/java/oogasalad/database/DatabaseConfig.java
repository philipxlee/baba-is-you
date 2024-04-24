package oogasalad.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.Properties;
import oogasalad.shared.loader.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DatabaseConfig is a class that manages the connection to the MongoDB database.
 *
 * @author Philip Lee.
 */
public class DatabaseConfig {

  private static final Logger logger = LogManager.getLogger(DatabaseConfig.class);
  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";
  private static final String CONNECTION_STRING = "mongo.connection.string";
  private static final String DATABASE_NAME = "mongo.database.name";
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
    Properties properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);
    String connectionString = properties.getProperty(CONNECTION_STRING);
    databaseName = properties.getProperty(DATABASE_NAME);
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(new ConnectionString(connectionString))
        .build();
    mongoClient = MongoClients.create(settings);
    logger.info("Connected to MongoDB database.");
  }

}
