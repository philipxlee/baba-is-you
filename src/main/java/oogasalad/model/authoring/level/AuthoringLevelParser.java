package oogasalad.model.authoring.level;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import oogasalad.shared.config.JsonManager;

/**
 * A class for parsing Authoring Level metadata into a JsonObject.
 */
public class AuthoringLevelParser {

  private final JsonManager jsonManager;

  /**
   * Constructs an AuthoringLevelParser object with the specified JsonManager.
   *
   * @param jsonManager The JsonManager used for JSON operations.
   */
  public AuthoringLevelParser(JsonManager jsonManager) {
    this.jsonManager = jsonManager;
  }

  /**
   * Converts LevelMetadata into a JsonObject.
   *
   * @param level The Level object to be parsed.
   * @return A JsonObject representing the level's metadata.
   */
  public JsonObject parseLevelMetadata(Level level) {
    LevelMetadata metadata = level.getLevelMetadata();

    JsonObject metadataJson = new JsonObject();

    JsonObject gridSizeObject = new JsonObject();

    JsonObject grid = new JsonObject();

    buildJsonObject(metadataJson, metadata, gridSizeObject);

    JsonArray gridArray = turnGridToJson(level);

    jsonManager.addArrayToJson(grid, "cells", gridArray);

    jsonManager.addArrayToJson(metadataJson, "grid", gridArray);

    return metadataJson;
  }

  /**
   * Converts the 2D grid of the level into a JsonArray.
   *
   * @param level The Level object containing the grid to be converted.
   * @return A JsonArray representing the grid of the level.
   */
  private JsonArray turnGridToJson(Level level) {
    String[][] levelGrid = level.getParsedGrid();

    return convertGridToJsonArray(levelGrid);
  }

  /**
   * Builds a JsonObject representing LevelMetadata along with grid size information.
   *
   * @param metadataJson   The JsonObject to which level metadata will be added.
   * @param metadata       The LevelMetadata object containing level information.
   * @param gridSizeObject The JsonObject representing grid size information.
   */
  private void buildJsonObject(JsonObject metadataJson, LevelMetadata metadata,
      JsonObject gridSizeObject) {

    jsonManager.addProperty(metadataJson, "levelName", metadata.levelName());

    jsonManager.addProperty(gridSizeObject, "rows", String.valueOf(metadata.rows()));
    jsonManager.addProperty(gridSizeObject, "cols", String.valueOf(metadata.cols()));

    jsonManager.addObject(metadataJson, "gridSize", gridSizeObject);
  }

  /**
   * Converts a 2D String array representing a grid into a JsonArray with a similar structure to
   * what is produced for a 3D array, effectively wrapping the 2D grid as a single-layer grid.
   *
   * @param grid The 2D String array representing the level grid in the authoring environment.
   * @return A JsonArray where the first element is a JsonArray representing the single layer of the
   * grid.
   */
  private JsonArray convertGridToJsonArray(String[][] grid) {
    JsonArray singleLayerArray = new JsonArray();

    for (String[] row : grid) {
      JsonArray rowArray = new JsonArray();
      for (String cell : row) {
        rowArray.add(cell);
      }
      singleLayerArray.add(rowArray);
    }

    JsonArray layersArray = new JsonArray();
    layersArray.add(singleLayerArray);

    return layersArray;
  }
}
