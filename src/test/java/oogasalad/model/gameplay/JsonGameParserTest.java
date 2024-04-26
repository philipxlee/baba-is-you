package oogasalad.model.gameplay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import oogasalad.model.gameplay.level.JsonGameParser;
import oogasalad.model.gameplay.level.Level;
import oogasalad.shared.config.JsonManager;
import org.junit.jupiter.api.Test;

public class JsonGameParserTest {

  private final JsonGameParser jsonGameParser = new JsonGameParser();
  private final JsonManager jsonManager = new JsonManager();
  private final File defaultJson = new File("data/test2.json");

  @Test
  public void testParseLevel() throws IOException {

    //GIVEN a parsed level with the JsonGameParser
    Level parsedLevel = jsonGameParser.parseLevel(jsonManager.loadJsonFromFile(defaultJson));

    //WHEN the information is accessed
    //THEN it should have the correct values
    assertNotNull(parsedLevel);
    assertEquals("TestLevel", parsedLevel.getLevelMetadata().levelName());
    assertEquals("Easy", parsedLevel.getLevelMetadata().difficulty());
    assertEquals("3", parsedLevel.getLevelMetadata().health());
    assertEquals(2, parsedLevel.getLevelMetadata().rows());
    assertEquals(2, parsedLevel.getLevelMetadata().columns());
    assertTrue(parsedLevel.getLevelMetadata().initialConfiguration().getClass().isArray());
  }
}

