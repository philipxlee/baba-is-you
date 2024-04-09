package oogasalad.model.authoring.block;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class BlockFactoryTest {

  @TempDir
  Path tempDir;

  String validPropertiesFilePath = "/blocktypes/blocktypes.properties";
  String invalidPropertiesFilePath = "nonexistent.properties";

  @Test
  void loadBlockTypesSuccessfully() {
    assertDoesNotThrow(() -> new BlockFactory(validPropertiesFilePath));
  }

  @Test
  void loadBlockTypesWithInvalidPath() {
    Exception exception = assertThrows(RuntimeException.class,
        () -> new BlockFactory(invalidPropertiesFilePath));
    assertTrue(exception.getMessage().contains("Properties file not found"));
  }

  @Test
  void findBlockTypeByNameSuccessfully() throws Exception {
    BlockFactory manager = new BlockFactory(validPropertiesFilePath);
    assertNotNull(manager.findBlockTypeByName("Empty"));
  }

  @Test
  void findBlockTypeByNameNotFound() {
    BlockFactory manager = assertDoesNotThrow(
        () -> new BlockFactory(validPropertiesFilePath));
    Exception exception = assertThrows(Exception.class,
        () -> manager.findBlockTypeByName("NonexistentType"));
    assertTrue(exception.getMessage().contains("Invalid block type"));
  }
}
