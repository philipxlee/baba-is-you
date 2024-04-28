package oogasalad.model.gameplay.level;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import oogasalad.shared.config.JsonManager;

/**
 * Parses Level objects into JSON representations. This parser handles the conversion of level data,
 * including metadata and grid configurations, into a structured JsonObject.
 */
public class GameLevelParser {

  private final JsonManager jsonManager;

  /**
   * Constructs a GameLevelParser with a specified JsonManager.
   *
   * @param jsonManager The JsonManager to use for JSON operations.
   */
  public GameLevelParser(JsonManager jsonManager) {
    this.jsonManager = jsonManager;
  }

  /**
   * Parses a Level object into a JsonObject. This method serializes the level's name, grid size,
   * grid cells, and metadata into JSON format.
   *
   * @param level The Level object to parse.
   * @return A JsonObject representing the serialized level.
   */
  public JsonObject parseLevel(Level level) {
    JsonObject levelJson = new JsonObject();

    addLevelNameAndGridSize(level, levelJson);

    addGridAndMetadata(level, levelJson);

    return levelJson;
  }

  /**
   * Adds the level name and grid size to the JSON object.
   *
   * @param level     The Level object containing the metadata.
   * @param levelJson The JsonObject to which the name and grid size are added.
   */
  private void addLevelNameAndGridSize(Level level, JsonObject levelJson) {
    jsonManager.addProperty(levelJson, "levelName", level.getLevelMetadata().levelName());
    JsonObject gridSize = new JsonObject();
    jsonManager.addProperty(gridSize, "rows", String.valueOf(level.getLevelMetadata().rows()));
    jsonManager.addProperty(gridSize, "columns", String.valueOf(level.getLevelMetadata()
        .columns()));
    jsonManager.addObject(levelJson, "gridSize", gridSize);
  }

  /**
   * Adds the grid cells and metadata to the JSON object.
   *
   * @param level     The Level object containing the grid and metadata.
   * @param levelJson The JsonObject to which the grid and metadata are added.
   */
  private void addGridAndMetadata(Level level, JsonObject levelJson) {
    JsonObject grid = new JsonObject();
    JsonArray cellsArray = constructGridArray(level);
    jsonManager.addArrayToJson(grid, "cells", cellsArray);

    JsonObject metadata = constructMetadata(level);
    jsonManager.addObject(grid, "metadata", metadata);

    jsonManager.addObject(levelJson, "grid", grid);
  }

  /**
   * Constructs the cells array from the level's initial configuration. This method delegates the
   * construction of each row to a helper method to reduce complexity.
   *
   * @param level The Level object containing the initial configuration.
   * @return A JsonArray representing the cells of the level's grid.
   */
  private JsonArray constructGridArray(Level level) {
    JsonArray cellsArray = new JsonArray();
    for (String[][] layer : level.getLevelMetadata().initialConfiguration()) {
      JsonArray layerArray = constructRowArray(layer);
      cellsArray.add(layerArray);
    }
    return cellsArray;
  }

  /**
   * Constructs a JsonArray representing a single row of the grid. This simplifies the construction
   * of the grid by handling one row at a time.
   *
   * @param layer A 2D String array representing a single row of the grid.
   * @return A JsonArray of JsonArrays, where each inner array represents a row.
   */
  private JsonArray constructRowArray(String[][] layer) {
    JsonArray layerArray = new JsonArray();
    for (String[] row : layer) {
      JsonArray rowArray = constructCellArray(row);
      layerArray.add(rowArray);
    }
    return layerArray;
  }

  /**
   * Constructs a JsonArray from an array of cell values (Strings). This method encapsulates the
   * process of converting cell values into a JsonArray, improving readability.
   *
   * @param row An array of Strings, where each String represents a cell's content.
   * @return A JsonArray containing the cell values.
   */
  private JsonArray constructCellArray(String[] row) {
    JsonArray rowArray = new JsonArray();
    for (String cell : row) {
      rowArray.add(cell);
    }
    return rowArray;
  }

  /**
   * Constructs the metadata JsonObject from the level's metadata.
   *
   * @param level The Level object containing the metadata.
   * @return A JsonObject representing the level's metadata.
   */
  private JsonObject constructMetadata(Level level) {
    JsonObject metadata = new JsonObject();
    jsonManager.addProperty(metadata, "difficulty", level.getLevelMetadata().difficulty());
    jsonManager.addProperty(metadata, "hint", level.getLevelMetadata().hint());
    jsonManager.addProperty(metadata, "author", level.getLevelMetadata().authorName());
    return metadata;
  }
}
