package oogasalad.model.authoring.block;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * BlockTypeManager loads the block types from a properties file and returns a list of block types.
 */
public class BlockTypeManager {

  private static final List<BlockType> blockTypes = new ArrayList<>();

  /**
   * BlockTypeManager constructor. Initialized with the Block Type properties filepath.
   *
   * @param propertiesFilePath The file path of block type properties file.
   * @throws IOException Exception thrown if file path is invalid.
   */
  public BlockTypeManager(String propertiesFilePath) throws IOException {
    loadBlockTypes(propertiesFilePath);
  }

  /**
   * Load block types from the properties file into list of BlockType.
   *
   * @param propertiesFilePath The file path of block type properties file.
   */
  private boolean loadBlockTypes(String propertiesFilePath) {
    try (InputStream input = getClass().getResourceAsStream(propertiesFilePath)) {
      if (input == null) {
        throw new IllegalArgumentException("Properties file not found: " + propertiesFilePath);
      }
      Properties properties = new Properties();
      properties.load(input);
      String[] types = properties.getProperty("block.types", "").split(",");
      for (String typeName : types) {
        blockTypes.add(new BlockType(typeName.trim()));
      }
      return true;
    } catch (IOException e) {
      throw new RuntimeException("Failed to load block types from properties file");
    }
  }

  /**
   * Utility method to find BlockType by name.
   *
   * @param name The name of the block. Must be in block type properties file.
   * @return The BlockType object corresponding to the name.
   * @throws Exception Thrown when the name is invalid (not in properties file).
   */
  public BlockType findBlockTypeByName(String name) throws Exception {
    return blockTypes.stream()
        .filter(bt -> bt.name().equalsIgnoreCase(name))
        .findFirst()
        .orElseThrow(() -> new Exception("Invalid block type: " + name));
  }
}
