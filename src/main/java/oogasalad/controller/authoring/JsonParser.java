package oogasalad.controller.authoring;

import com.google.gson.JsonObject;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.shared.config.GridParser;
import oogasalad.shared.config.JsonManager;

public class JsonParser implements GridParser {

  private final JsonManager jsonManager = new JsonManager();

  /**
   * Parses a JsonObject representing a level into a Level object.
   *
   * @param levelJson The JsonObject to parse.
   * @return The Level object populated with data from the JSON.
   */
  public Level parseLevel(JsonObject levelJson) {
    LevelMetadata levelMetadata = parseMetadata(levelJson);

    return new Level(levelMetadata);
  }

  /**
   * Parses metadata from a given JSON object representing a level. This metadata includes level
   * name, description, grid dimensions, difficulty, author, and a hint.
   *
   * @param levelJson The JSON object containing the level information.
   * @return LevelMetadata object containing all parsed details from the JSON.
   */
  private LevelMetadata parseMetadata(JsonObject levelJson) {
    String levelName = jsonManager.getValue(levelJson, "levelName");
    String description = jsonManager.getValue(levelJson, "description");
    String[][][] grid = getJsonGrid(levelJson);
    int rows = grid[0].length;
    int columns = grid.length;
    JsonObject gridObject = getObject(levelJson, "grid");
    JsonObject metadataObject = jsonManager.getJsonObject(gridObject, "metadata");
    String difficulty = jsonManager.getValue(metadataObject, "difficulty");
    String authorName = jsonManager.getValue(metadataObject, "author");
    String hint = jsonManager.getValue(metadataObject, "hint");

    return new LevelMetadata(levelName, description, rows, columns, difficulty, authorName, hint);
  }

  /**
   * Obtains the grid represented as a JsonArray in json.
   *
   * @param levelJson The JsonObject to parse.
   * @return The grid as a 3D array of strings.
   */
  public String[][][] getJsonGrid(JsonObject levelJson) {
    JsonObject gridObject = getObject(levelJson, "grid");
    String[][][] grid = parseGrid(jsonManager.getJsonArray(gridObject, "cells"));
    return grid;
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

