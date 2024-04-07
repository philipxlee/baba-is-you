package oogasalad.model.authoring.level;

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

    buildJsonObject(metadataJson, metadata, gridSizeObject);

    return metadataJson;
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
}
