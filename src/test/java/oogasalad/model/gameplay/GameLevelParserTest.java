package oogasalad.model.gameplay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import oogasalad.model.gameplay.level.GameLevelParser;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import oogasalad.shared.config.JsonManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    LevelMetadata metadata = new LevelMetadata("TestLevel", "Easy", 2,
        2, initialConfiguration);
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

    JsonArray initialConfig = jsonManager.getJsonArray(jsonManager.getJsonObject(levelJson,
        "grid"), "cells");
    assertNotNull(initialConfig);
    assertEquals(2, initialConfig.size());

    JsonArray firstRow = initialConfig.get(0).getAsJsonArray();
    String firstCell = String.valueOf(firstRow.get(0));
    String secondCell = String.valueOf(firstRow.get(1));
    assertEquals("[\"EmptyVisualBlock\",\"RockVisualBlock\"]", firstCell);
    assertEquals("[\"EmptyVisualBlock\"]", secondCell);

  }
}

