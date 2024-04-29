package oogasalad.view.authoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import oogasalad.view.authoring.blockDisplay.BlockData;
import oogasalad.view.authoring.blockDisplay.BlockNameManager;
import org.junit.jupiter.api.Test;
import java.util.List;

public class BlockNameManagerTest {

  @Test
  public void testExtractBlocks() {
    // Create an instance of BlockNameManager
    BlockNameManager manager = new BlockNameManager();

    // Manually create a simulated JSON object as it would be read from a file
    JsonObject jsonObject = new JsonObject();
    JsonArray categories = new JsonArray();
    categories.add(new JsonPrimitive("All"));
    JsonObject blockDetails = new JsonObject();
    blockDetails.addProperty("imagePath", "path/to/image.png");
    blockDetails.add("category", categories);
    jsonObject.add("blockName", blockDetails);

    // Directly call the extractBlocks method with the simulated JSON data
    List<BlockData> blocks = manager.extractBlocks(jsonObject, "All");

    // Assertions to check if the blocks have been extracted correctly
    assertEquals(1, blocks.size(), "There should be exactly one block data extracted.");
    BlockData extractedBlock = blocks.get(0);
    assertEquals("blockName", extractedBlock.getName(), "The block name should match.");
    assertEquals("path/to/image.png", extractedBlock.getImagePath(),
        "The image path should match.");
  }

  @Test
  public void testCategoryMatch() {
    // Create an instance of BlockNameManager
    BlockNameManager manager = new BlockNameManager();

    // Create a category JSON array
    JsonArray categories = new JsonArray();
    categories.add(new JsonPrimitive("All"));
    categories.add(new JsonPrimitive("Terrain"));

    // Test various scenarios
    assertTrue(manager.isCategoryMatch(categories, "All"), "Should match 'All' category");
    assertTrue(manager.isCategoryMatch(categories, "Terrain"), "Should match 'Terrain' category");
    assertTrue(manager.isCategoryMatch(categories, ""),
        "Should match any category when filter is empty");

  }
}