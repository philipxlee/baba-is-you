package oogasalad.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import oogasalad.shared.util.PropertiesLoader;

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
    Properties properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);
    String connectionString = properties.getProperty("mongo.connection.string");
    databaseName = properties.getProperty("mongo.database.name");
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(new ConnectionString(connectionString))
        .build();
    mongoClient = MongoClients.create(settings);
  }


}
