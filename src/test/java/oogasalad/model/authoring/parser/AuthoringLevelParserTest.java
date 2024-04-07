package oogasalad.model.authoring.parser;

import com.google.gson.JsonObject;
import java.io.IOException;
import oogasalad.model.authoring.block.BlockTypeManager;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.shared.config.JsonManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthoringLevelParserTest {

  private AuthoringLevelParser parser;
  private Level testLevel;
  private final String validPropertiesFilePath = "/blocktypes/blocktypes.properties";
  private final JsonManager jsonManager = new JsonManager();

  @BeforeEach
  void setUp() throws IOException {
    BlockTypeManager blockTypeManager = new BlockTypeManager(validPropertiesFilePath);
    LevelMetadata metadata = new LevelMetadata("TestLevel",
        "This is a test level", 10, 15);
    testLevel = new Level(metadata, blockTypeManager);

    parser = new AuthoringLevelParser(jsonManager);
  }

  @Test
  void testParseLevelMetadata() {
    //GIVEN the AuthoringLevelParser is working
    //WHEN the parser gets a level
    JsonObject metadataJson = parser.parseLevelMetadata(testLevel);
    //THEN it will turn the information into a corresponding JsonObject
    assertAll("Level metadata should be correctly parsed",
        () -> assertEquals("TestLevel", jsonManager.getValue(metadataJson, "name"),
            "Name should match"),
        () -> assertEquals("10", jsonManager.getValue(metadataJson, "rows"),
            "Rows should match"),
        () -> assertEquals("15", jsonManager.getValue(metadataJson, "cols"),
            "Columns should match")
    );
  }
}
