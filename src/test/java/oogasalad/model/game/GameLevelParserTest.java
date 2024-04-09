package oogasalad.model.game;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import oogasalad.shared.config.JsonManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameLevelParserTest {
  private GameLevelParser parser;
  private Level testLevel;
  private JsonManager jsonManager;

  @BeforeEach
  void setUp() {
    String[][][] initialConfiguration = {
        {{"EmptyVisualBlock", "RockVisualBlock"}, {"EmptyVisualBlock"}},
        {{"EmptyVisualBlock"}, {"EmptyVisualBlock", "BabaVisualBlock"}}
    };
    LevelMetadata metadata = new LevelMetadata("TestLevel", "Easy", "3",
        2, 2, initialConfiguration);
    testLevel = new Level(metadata);

    jsonManager = new JsonManager();
    parser = new GameLevelParser(jsonManager);
  }

  @Test
  void testParseLevel() {
    //GIVEN a level parser for the game player and a level
    JsonObject levelJson = parser.parseLevel(testLevel);
    //WHEN the JsonObject produced by the parser is accessed
    //THEN it should be storing the correct values for that level
    assertEquals("TestLevel", jsonManager.getValue(levelJson, "levelName"));
    assertEquals(2, Integer.parseInt(jsonManager.getValue(jsonManager.getJsonObject
        (levelJson, "gridSize"), "rows")));
    assertEquals(2, Integer.parseInt(jsonManager.getValue(jsonManager.getJsonObject
        (levelJson, "gridSize"), "columns")));

    JsonArray initialConfig = levelJson.getAsJsonArray("initialConfiguration");
    assertNotNull(initialConfig);
    assertEquals(2, initialConfig.size());

    JsonArray firstLayer = initialConfig.get(0).getAsJsonArray();
    JsonArray firstRow = firstLayer.get(0).getAsJsonArray();
    JsonArray firstCell = firstRow.get(0).getAsJsonArray();
    assertEquals("EmptyVisualBlock", firstCell.get(0).getAsString());
    assertEquals("RockVisualBlock", firstCell.get(1).getAsString());

  }
}

