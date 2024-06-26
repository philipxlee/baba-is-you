package oogasalad.model.gameplay.level;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.shared.config.JsonManager;
import oogasalad.view.gameplay.mainscene.GamePane;

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
   * @param pane
   * @return A JsonObject representing the serialized level.
   */
  public JsonObject parseLevel(Level level, GamePane pane) {
    JsonObject levelJson = new JsonObject();

    addLevelNameAndGridSize(level, levelJson);

    addGridAndMetadata(level, levelJson, pane);

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
  private void addGridAndMetadata(Level level, JsonObject levelJson, GamePane pane) {
    JsonObject grid = new JsonObject();
    JsonArray cellsArray = constructGridArray(pane);
    jsonManager.addArrayToJson(grid, "cells", cellsArray);

    JsonObject metadata = constructMetadata(level);
    jsonManager.addObject(grid, "metadata", metadata);

    jsonManager.addObject(levelJson, "grid", grid);
  }

  /**
   * Constructs a JsonArray representing the cells of the level's grid from the pane's current
   * configuration. This method uses streams to process each row of the grid, which is structured as
   * a 2D array where each element is a list of AbstractBlock objects. Each row is processed using
   * the constructRowArray method to simplify the conversion of each list into a JsonArray.
   *
   * @param pane The GamePane object that holds the grid controller and current game grid
   *             configuration.
   * @return A JsonArray where each element corresponds to a row in the grid, represented as a
   * JsonArray of cells.
   */
  private JsonArray constructGridArray(GamePane pane) {
    JsonArray cellsArray = new JsonArray();
    List<AbstractBlock>[][] grid = pane.getGridController().getGameGrid().getGrid();

    Arrays.stream(grid)
        .map(this::constructRowArray)
        .forEach(cellsArray::add);

    return cellsArray;
  }

  /**
   * Constructs a JsonArray representing a single row of the grid. This simplifies the construction
   * of the grid by handling one row at a time.
   *
   * @param layer A 2D String array representing a single row of the grid.
   * @return A JsonArray of JsonArrays, where each inner array represents a row.
   */
  private JsonArray constructRowArray(List<AbstractBlock>[] layer) {
    JsonArray layerArray = new JsonArray();
    Arrays.stream(layer)
        .map(this::constructCellArray)
        .forEach(layerArray::add);
    return layerArray;
  }

  /**
   * Constructs a JsonArray from an array of cell values (Strings). This method encapsulates the
   * process of converting cell values into a JsonArray, improving readability.
   *
   * @param row An array of Strings, where each String represents a cell's content.
   * @return A JsonArray containing the cell values.
   */
  private JsonArray constructCellArray(List<AbstractBlock> row) {
    return row.stream()
        .map(AbstractBlock::getBlockName)
        .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
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
