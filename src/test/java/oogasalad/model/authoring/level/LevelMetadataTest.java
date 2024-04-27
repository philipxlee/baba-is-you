package oogasalad.model.authoring.level;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class LevelMetadataTest {

  @Test
  public void testLevelMetadataCreation() {
    LevelMetadata levelMetadata = new LevelMetadata("TestLevel",
        "This is a test level", 10, 15, "Easy", "BabaIsUs",
    "Try harder");
    assertEquals("TestLevel", levelMetadata.levelName());
    assertEquals("This is a test level", levelMetadata.description());
    assertEquals(10, levelMetadata.rows());
    assertEquals(15, levelMetadata.cols());
    assertEquals("Easy", levelMetadata.difficulty());
    assertEquals("BabaIsUs", levelMetadata.authorName());
    assertEquals("Try harder", levelMetadata.hint());
  }
}
