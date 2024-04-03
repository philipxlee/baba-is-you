package oogasalad.model.authoring;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * BlockTypeManager loads the block types from a properties file and returns a list of block types
 * as strings.
 */
public class BlockTypeManager {

  private List<BlockType> blockTypes = new ArrayList<>();

  public BlockTypeManager(String propertiesFilePath) throws IOException {
    loadBlockTypes(propertiesFilePath);
  }

  private void loadBlockTypes(String propertiesFilePath) {
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
      throw new RuntimeException("Failed to load block types from properties file");
    }
  }

  public BlockType findBlockTypeByName(String name) {
    for (BlockType blockType : blockTypes) {
      if (blockType.name().equalsIgnoreCase(name)) {
        return blockType;
      }
    }
    throw new RuntimeException("Block name not found: " + name);
  }
}
