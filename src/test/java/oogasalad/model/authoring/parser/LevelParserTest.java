package oogasalad.model.authoring.parser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import oogasalad.controller.authoring.LevelParser;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.shared.config.JsonManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LevelParserTest {

  private final String validPropertiesFilePath = "/blocktypes/blocktypes.json";
  private final JsonManager jsonManager = new JsonManager();
  private LevelParser parser;
  private Level testLevel;

  @BeforeEach
  void setUp() throws Exception {

    LevelMetadata metadata = new LevelMetadata("TestLevel",
        "This is a test level", 3, 3, "Easy", "BabaIsUs",
        "Try harder");
    testLevel = new Level(metadata);

    parser = new LevelParser();
  }

  @Test
  void testParseLevelMetadata() {
    //GIVEN the AuthoringLevelParser is working
    //WHEN the parser gets a level
    JsonObject metadataJson = parser.parseLevelToJSON(testLevel);
    //THEN it will turn the information into a corresponding JsonObject
    assertAll("Level metadata should be correctly parsed",
        () -> assertEquals("TestLevel", jsonManager.getValue(metadataJson,
            "levelName"), "Name should match"),
        () -> assertNotNull(jsonManager.getJsonObject(metadataJson, "gridSize")),
        () -> assertEquals("5", jsonManager.getValue(jsonManager.getJsonObject
            (metadataJson, "gridSize"), "rows"), "Rows should match"),
        () -> assertEquals("5", jsonManager.getValue(jsonManager.getJsonObject
                (metadataJson, "gridSize"), "columns"),
            "Columns should match")
    );

    JsonObject gridObject = jsonManager.getJsonObject(metadataJson, "grid");
    JsonObject metadataObject = jsonManager.getJsonObject(gridObject, "metadata");

    assertEquals("Try harder", jsonManager.getValue(metadataObject, "hint"));
    assertEquals("Easy", jsonManager.getValue(metadataObject, "difficulty"));
    assertEquals("BabaIsUs", jsonManager.getValue(metadataObject, "author"));
    JsonArray jsonArray = jsonManager.getJsonArray(gridObject, "cells");
    JsonArray firstLayer = jsonArray.get(0).getAsJsonArray();
    JsonArray firstRow = firstLayer.get(0).getAsJsonArray();
    String firstCell = firstRow.get(0).getAsString();

    assertEquals("EmptyVisualBlock", firstCell, "The first cell is not an"
        + " EmptyVisualBlock");
  }
}
