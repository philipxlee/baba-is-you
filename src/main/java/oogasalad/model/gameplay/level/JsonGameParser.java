package oogasalad.model.gameplay.level;

import com.google.gson.JsonObject;
import oogasalad.shared.config.GridParser;
import oogasalad.shared.config.JsonManager;

/**
 * Parses JSON representations of game levels into Level objects.
 */
public class JsonGameParser implements GridParser {

  private final JsonManager jsonManager = new JsonManager();

  /**
   * Parses a JsonObject representing a level into a Level object.
   *
   * @param levelJson The JsonObject to parse.
   * @return The Level object populated with data from the JSON.
   */
  public Level parseLevel(JsonObject levelJson) {

    LevelMetadata metadata = parseLevelMetadata(levelJson);

    return new Level(metadata);
  }

  /**
   * Extracts level metadata from a JsonObject and constructs a LevelMetadata object.
   *
   * @param levelJson The JsonObject containing the level metadata.
   * @return A LevelMetadata object containing the extracted metadata from the JSON.
   */
  private LevelMetadata parseLevelMetadata(JsonObject levelJson) {
    String levelName = getValue(levelJson, "levelName");
    JsonObject gridSize = getObject(levelJson, "gridSize");
    int rows = Integer.parseInt(getValue(gridSize, "rows"));
    int columns = Integer.parseInt(getValue(gridSize, "columns"));
    JsonObject gridObject = getObject(levelJson, "grid");
    JsonObject metadataJson = getObject(gridObject, "metadata");
    String difficulty = getValue(metadataJson, "difficulty");
    String[][][] grid = parseGrid(jsonManager.getJsonArray(gridObject, "cells"));
    return new LevelMetadata(levelName, difficulty, rows, columns, grid);
  }

  /**
   * Gets the value for a specified key from a JSON object as a string.
   *
   * @param jsonObject The JSON object from which the value is to be retrieved.
   * @param string     The key corresponding to the desired value.
   * @return The string value associated with the specified key in the JSON object.
   */
  private String getValue(JsonObject jsonObject, String string) {
    return jsonManager.getValue(jsonObject, string);
  }

  /**
   * Gets a JSON object associated with the specified key from a parent JSON object.
   *
   * @param jsonObject The parent JSON object from which the nested JSON object is to be retrieved.
   * @param string     The key corresponding to the nested JSON object.
   * @return The JSON object associated with the specified key.
   */
  private JsonObject getObject(JsonObject jsonObject, String string) {
    return jsonManager.getJsonObject(jsonObject, string);
  }
}

