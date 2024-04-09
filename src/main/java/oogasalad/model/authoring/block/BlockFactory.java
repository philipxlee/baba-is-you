package oogasalad.model.authoring.block;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * BlockTypeManager loads the block types from a properties file and returns a list of block types.
 */
public class BlockFactory {

  private static final List<BlockType> blockTypes = new ArrayList<>();

  /**
   * BlockTypeManager constructor. Initialized with the Block Type properties filepath.
   *
   * @param propertiesFilePath The file path of block type properties file.
   */
  public BlockFactory(String propertiesFilePath) throws Exception {
    loadBlockTypes(propertiesFilePath);
  }

  /**
   * Load block types from the properties file into list of BlockType.
   *
   * @param propertiesFilePath The file path of block type properties file.
   */
  private void loadBlockTypes(String propertiesFilePath) throws Exception {
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
    } catch (IOException e) {
      throw new Exception("Failed to load block types from properties file");
    }
  }

  /**
   * Factory method to create a new block of block type.
   *
   * @param blockType The block type as a string.
   * @return A new Block object configured with the correct BlockType.
   * @throws Exception Throws exception if the blockType string is invalid.
   */
  public Block createBlock(String blockType) throws Exception {
    BlockType type = findBlockTypeByName(blockType);
    return new Block(type);
  }

  /**
   * Utility method to find BlockType by name.
   *
   * @param blockType The type of the block. Must be in block type properties file.
   * @return The BlockType object corresponding to the block type.
   * @throws Exception Thrown when the name is invalid (not in properties file).
   */
  public BlockType findBlockTypeByName(String blockType) throws Exception {
    return blockTypes.stream()
        .filter(bt -> bt.name().equalsIgnoreCase(blockType))
        .findFirst()
        .orElseThrow(() -> new Exception("Invalid block type: " + blockType));
  }
}
