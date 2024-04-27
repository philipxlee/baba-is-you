package oogasalad.database.dataservices;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.CursorType;
import com.mongodb.ExplainVerbosity;
import com.mongodb.Function;
import com.mongodb.ServerAddress;
import com.mongodb.ServerCursor;
import com.mongodb.client.model.Collation;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import oogasalad.database.dataservice.DataFetcher;
import oogasalad.database.gamedata.LeaderboardData;
import org.bson.BsonValue;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.*;

class DataFetcherTest {

  private DataFetcher dataFetcher;
  private MongoDatabase mockDatabase;
  private MongoCollection<Document> mockCollection;
  private Properties properties;

  @BeforeEach
  void setUp() throws NoSuchFieldException, IllegalAccessException {
    // Reset the DataFetcher singleton instance using reflection
    Field instanceField = DataFetcher.class.getDeclaredField("instance");
    instanceField.setAccessible(true);
    instanceField.set(null, null); // Set the static field to null

    mockDatabase = mock(MongoDatabase.class);
    mockCollection = mock(MongoCollection.class);
    when(mockDatabase.getCollection(anyString())).thenReturn(mockCollection);
    dataFetcher = DataFetcher.getInstance(mockDatabase);
    properties = new Properties();
    setupProperties(properties);
  }

  private void setupProperties(Properties properties) {
    properties.setProperty("collection.data", "data");
    properties.setProperty("collection.comments", "comments");
    properties.setProperty("field.username", "username");
    properties.setProperty("field.levelName", "levelName");
    properties.setProperty("field.timeSpent", "timeSpent");
    properties.setProperty("field.date", "date");
    properties.setProperty("field.comment", "comment");
    properties.setProperty("field.replies", "replies");
    properties.setProperty("field.reply", "reply");
  }

  @Test
  void testIsUsernameAvailable() {
    when(mockCollection.countDocuments((Bson) any())).thenReturn(0L);
    assertTrue(dataFetcher.isUsernameAvailable("newUser"));
    verify(mockCollection).countDocuments((Bson) any());
  }

}

