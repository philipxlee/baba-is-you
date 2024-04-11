package oogasalad.model.authoring.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import oogasalad.model.authoring.block.BlockFactory;
import oogasalad.controller.authoring.LevelParser;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.shared.config.JsonManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelParserTest {

  private LevelParser parser;
  private Level testLevel;
  private final String validPropertiesFilePath = "/blocktypes/blocktypes.json";
  private final JsonManager jsonManager = new JsonManager();

  @BeforeEach
  void setUp() throws Exception {

    BlockFactory blockTypeManager = new BlockFactory(validPropertiesFilePath);
    LevelMetadata metadata = new LevelMetadata("TestLevel",
        "This is a test level", 3, 3);
    testLevel = new Level(metadata, blockTypeManager);

    parser = new LevelParser(jsonManager);
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
        () -> assertEquals("3", jsonManager.getValue(jsonManager.getJsonObject
            (metadataJson, "gridSize"), "rows"), "Rows should match"),
        () -> assertEquals("3", jsonManager.getValue(jsonManager.getJsonObject
                (metadataJson, "gridSize"), "cols"),
            "Columns should match")
    );

    JsonArray jsonArray = jsonManager.getJsonArray(metadataJson, "grid");
    JsonArray firstLayer = jsonArray.get(0).getAsJsonArray();
    JsonArray firstRow = firstLayer.get(0).getAsJsonArray();

    String firstCell = firstRow.get(0).getAsString();
    String secondCell = firstRow.get(1).getAsString();

    assertEquals("EmptyVisualBlock", firstCell, "The first cell is not an"
        + " EmptyVisualBlock");
    assertEquals("EmptyVisualBlock", secondCell, "The second cell is not an"
        + " EmptyVisualBlock");
  }
}
