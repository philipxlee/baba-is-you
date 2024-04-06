package oogasalad.shared.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonManagerTest {
  private JsonObject result;
  private JsonManager manager;
  private File testFile;

  @BeforeEach
  void setUp() throws IOException {
    //GIVEN the JsonManager
    manager = new JsonManager();
    testFile = new File("data/test.json");
    //WHEN a file is loaded in
    result = manager.loadJsonFromFile(testFile);
  }

  @Test
  void testLoadJsonFromFile() {
    //GIVEN the JsonManager
    //WHEN we load in a file with loadFromFile
    //THEN we will have access to the correct file
    assertNotNull(result);
    assertEquals("The Dungeon of Doom", manager.getValue(result, "levelName"));
  }

  @Test
  void testGetJsonObject() {
    //GIVEN the JsonManager
    //WHEN we use getJsonObject for a nested JsonObject
    //THEN we will have access to the correct JSONObject
    assertNotNull(manager.getJsonObject(result, "grid"));
    assertNotNull(manager.getJsonObject(manager.getJsonObject(result, "grid"),
        "metadata"));
  }

}
