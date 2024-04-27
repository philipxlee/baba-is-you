package oogasalad.controller.authoring;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
  public String[][][] parseLevel(JsonObject levelJson) {

    return getJsonGrid(levelJson);
  }

  /**
   * Obtains the grid represented as a JsonArray in json.
   *
   * @param levelJson The JsonObject to parse.
   * @return The grid as a 3D array of strings.
   */
  private String[][][] getJsonGrid(JsonObject levelJson) {
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

