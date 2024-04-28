//package oogasalad.database;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoDatabase;
//import java.lang.reflect.Field;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class DatabaseConfigTest {
//
//  @Mock
//  private MongoClient mockMongoClient;
//  @Mock
//  private MongoDatabase mockMongoDatabase;
//
//  private DatabaseConfig databaseConfig;
//
//  @BeforeEach
//  public void setup() {
//    MockitoAnnotations.initMocks(this);
//    try {
//      databaseConfig = new DatabaseConfig();  // Assuming constructor doesn't throw an exception
//      setPrivateField(databaseConfig, "mongoClient", mockMongoClient);
//      when(mockMongoClient.getDatabase(anyString())).thenReturn(mockMongoDatabase);
//    } catch (Exception e) {
//      fail("Failed to set up the DatabaseConfigTest due to: " + e.getMessage());
//    }
//  }
//
//  @AfterEach
//  public void cleanup() {
//    if (databaseConfig != null) {
//      databaseConfig.close();
//    }
//    reset(mockMongoClient);
//  }
//
//  @Test
//  public void testGetDatabase() {
//    assertNotNull(databaseConfig, "DatabaseConfig instance should not be null");
//    MongoDatabase db = databaseConfig.getDatabase();
//    verify(mockMongoClient).getDatabase(anyString());
//    assertSame(mockMongoDatabase, db);
//  }
//
//  @Test
//  public void testClose() {
//    assertNotNull(databaseConfig, "DatabaseConfig instance should not be null");
//    databaseConfig.close();
//    verify(mockMongoClient).close();
//  }
//
//  private void setPrivateField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
//    Field field = object.getClass().getDeclaredField(fieldName);
//    field.setAccessible(true);
//    field.set(object, value);
//  }
//}
