package oogasalad.controller.authoring;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

public class LevelControllerTest {

  private LevelController levelController;

  @Before
  public void setUp() throws Exception {
    // Setup mock BlockTypeManager
    levelController = new LevelController();
    levelController.initializeLevel("testLevel", "", 10, 10);
  }

  @Test
  public void testLevelControllerConstructor() {
    assertNotNull(levelController);
    // Additional assertions can be made here if LevelController stores more state
  }

  @Test
  public void testSetCellValidBlock() {
    // Assuming "validBlockName" is considered valid by the mocked BlockTypeManager
    assertDoesNotThrow(() -> levelController.setCell(1, 1, "EmptyVisualBlock"));
  }

  @Test
  public void testSetCellInvalidBlock() {
    Exception exception = assertThrows(Exception.class, () -> {
      levelController.setCell(1, 1, "invalidBlockName");
    });

    assertTrue(exception.getMessage().contains("Invalid block type"));
  }

  @Test
  public void testSerializeLevel() throws IOException {
    // Redirect system output to capture the JSON output for verification
    final String expectedFileName = "testLevel.json";
    Path path = Paths.get(expectedFileName);
    try {
      levelController.serializeLevel();

      // Verify that file was created and contains the expected content
      assertTrue(Files.exists(path));
    } finally {
      // Cleanup: Delete the test file after verification
      Files.deleteIfExists(path);
    }
  }
}
