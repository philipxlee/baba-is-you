package oogasalad.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import static org.mockito.Mockito.*;

public class DatabaseConfigTest {

  @Mock
  private MongoClient mockMongoClient;

  @Mock
  private MongoDatabase mockMongoDatabase;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void cleanup() {
    reset(mockMongoClient);
  }


  @Test
  public void testClose() throws Exception {
    // Create the DatabaseConfig instance
    DatabaseConfig databaseConfig = new DatabaseConfig();
    setPrivateField(databaseConfig, "mongoClient", mockMongoClient);
    databaseConfig.close();
    verify(mockMongoClient).close();
  }

  private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
    Field field = object.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(object, value);
  }
}