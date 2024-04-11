package oogasalad.model.authoring.block;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * BlockTypeManager loads the block types from a properties file and returns a list of block types.
 */
public class BlockFactory {

  private static final String BLOCKTYPES_CONFIG_PATH = "/blocktypes/blocktypes.json";
  private static BlockFactory instance;
  private List<BlockType> blockTypes;

  /**
   * BlockTypeManager constructor.
   */
  private BlockFactory() throws Exception {
    loadBlockTypes(BLOCKTYPES_CONFIG_PATH);
  }

  /**
   * Singleton instance accessor. Initializes the instance if not already done.
   *
   * @return Static instance of BlockFactory.
   * @throws Exception Throws exception if blocktypes properties file path is invalid.
   */
  public static BlockFactory getInstance() throws Exception {
    if (instance == null) {
      instance = new BlockFactory();
    }
    return instance;
  }

  /**
   * Load block types from the properties file into list of BlockType.
   *
   * @param propertiesFilePath The file path of block type properties file.
   */
  private void loadBlockTypes(String propertiesFilePath) throws Exception {
    blockTypes = new ArrayList<>();
    try (InputStream input = getClass().getResourceAsStream(propertiesFilePath)) {
      if (input == null) {
        throw new IllegalArgumentException("JSON file not found: " + propertiesFilePath);
      }

      // Parse the JSON file
      Gson gson = new Gson();
      Type type = new TypeToken<Map<String, JsonObject>>() {
      }.getType();
      Map<String, JsonObject> blockTypesMap = gson.fromJson(new InputStreamReader(input), type);

      // Iterate over the map and create BlockType objects
      blockTypesMap.forEach((name, blockTypeJson) -> {
        blockTypes.add(new BlockType(name.trim()));
      });
    } catch (IOException e) {
      throw new Exception("Failed to load block types from JSON file", e);
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
