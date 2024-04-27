package oogasalad.database.dataservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.lang.reflect.Field;
import oogasalad.database.dataservice.DataUploader;
import oogasalad.database.gamedata.GameSession;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class DataUploaderTest {

  private DataUploader dataUploader;
  private MongoDatabase mockDatabase;
  private MongoCollection<Document> mockCollection;
  private GameSession gameSession;

  @BeforeEach
  void setUp() throws NoSuchFieldException, IllegalAccessException {
    Field instanceField = DataUploader.class.getDeclaredField("instance");
    instanceField.setAccessible(true);
    instanceField.set(null, null); // Set the static field to null

    mockDatabase = mock(MongoDatabase.class);
    mockCollection = mock(MongoCollection.class);
    when(mockDatabase.getCollection(anyString())).thenReturn(mockCollection);

    gameSession = new GameSession("testUser", "testLevel");
    dataUploader = DataUploader.getInstance(mockDatabase);
    dataUploader.setGameSession(gameSession);
  }

  @Test
  void testSavePlayerLeaderboardData() {
    long startTime = System.currentTimeMillis() - 1000;
    long endTime = System.currentTimeMillis();
    dataUploader.savePlayerLeaderboardData(startTime, endTime);
    verify(mockCollection, times(1)).insertOne(any(Document.class));
  }

  @Test
  void testAddReplyToUserComment() {
    String commenterUsername = "testUser";
    String playerUsername = "replyUser";
    String replyText = "Nice game!";

    dataUploader.addReplyToUserComment(commenterUsername, playerUsername, replyText);

    verify(mockCollection).updateOne(any(Document.class), any(Document.class));
    ArgumentCaptor<Document> filterCaptor = ArgumentCaptor.forClass(Document.class);
    ArgumentCaptor<Document> updateCaptor = ArgumentCaptor.forClass(Document.class);
    verify(mockCollection).updateOne(filterCaptor.capture(), updateCaptor.capture());
    Document filterDoc = filterCaptor.getValue();
    assertEquals(commenterUsername, filterDoc.getString("username"));
  }
}
