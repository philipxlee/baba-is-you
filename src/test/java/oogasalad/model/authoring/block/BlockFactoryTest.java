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

  @Test
  void loadBlockTypesSuccessfully() {
    assertDoesNotThrow(BlockFactory::getInstance);
  }

  @Test
  void findBlockTypeByNameSuccessfully() throws Exception {
    BlockFactory manager = BlockFactory.getInstance();
    assertNotNull(manager.createBlock("EmptyVisualBlock"));
  }

  @Test
  void findBlockTypeByNameNotFound() {
    BlockFactory manager = assertDoesNotThrow(
        BlockFactory::getInstance);
    Exception exception = assertThrows(Exception.class,
        () -> manager.findBlockTypeByName("NonexistentType"));
    assertTrue(exception.getMessage().contains("Invalid block type"));
  }
}
