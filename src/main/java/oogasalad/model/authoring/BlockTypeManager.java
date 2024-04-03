package oogasalad.model.authoring;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class BlockTypeManager {

  private List<String> blockTypes = new ArrayList<>();

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
      String types = properties.getProperty("block.types");
      if (types != null && !types.isEmpty()) {
        Collections.addAll(blockTypes, types.split(","));
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to load block types from properties file");
    }
  }

  public List<String> getBlockTypes() {
    return new ArrayList<>(blockTypes);
  }
}
