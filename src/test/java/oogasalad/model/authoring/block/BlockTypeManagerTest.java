package oogasalad.model.authoring.block;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class BlockTypeManagerTest {

  @TempDir
  Path tempDir;

  String validPropertiesFilePath = "/blocktypes/blocktypes.properties";
  String invalidPropertiesFilePath = "nonexistent.properties";

  @Test
  void loadBlockTypesSuccessfully() {
    assertDoesNotThrow(() -> new BlockTypeManager(validPropertiesFilePath));
  }

  @Test
  void loadBlockTypesWithInvalidPath() {
    Exception exception = assertThrows(RuntimeException.class,
        () -> new BlockTypeManager(invalidPropertiesFilePath));
    assertTrue(exception.getMessage().contains("Properties file not found"));
  }

  @Test
  void findBlockTypeByNameSuccessfully() throws Exception {
    BlockTypeManager manager = new BlockTypeManager(validPropertiesFilePath);
    assertNotNull(manager.findBlockTypeByName("Empty"));
  }

  @Test
  void findBlockTypeByNameNotFound() {
    BlockTypeManager manager = assertDoesNotThrow(
        () -> new BlockTypeManager(validPropertiesFilePath));
    Exception exception = assertThrows(Exception.class,
        () -> manager.findBlockTypeByName("NonexistentType"));
    assertTrue(exception.getMessage().contains("Block name not found"));
  }
}
