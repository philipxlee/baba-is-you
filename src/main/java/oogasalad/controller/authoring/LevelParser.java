package oogasalad.controller.authoring;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import oogasalad.model.authoring.level.Grid;
import oogasalad.model.authoring.level.Level;
import oogasalad.model.authoring.level.LevelMetadata;
import oogasalad.shared.config.JsonManager;

/**
 * A class for parsing Authoring Level metadata into a JsonObject.
 */
public class LevelParser {

  private final JsonManager jsonManager;
  private Grid grid;
  /**
   * Constructs an AuthoringLevelParser object with the specified JsonManager.
   */
  public LevelParser(Grid grid) {
    jsonManager = new JsonManager();
    this.grid = grid;
  }

  /**
   * Converts LevelMetadata into a JsonObject.
   *
   * @param level The Level object to be parsed.
   * @return A JsonObject representing the level's metadata.
   */
  public JsonObject parseLevelToJSON(Level level) {
    LevelMetadata metadata = level.getLevelMetadata();
    JsonObject metadataJson = new JsonObject();
    JsonObject gridSizeObject = new JsonObject();
    JsonObject grid = new JsonObject();
    JsonObject metadataObject = new JsonObject();
    jsonManager.addProperty(metadataObject, "author", metadata.authorName());
    jsonManager.addProperty(metadataObject, "difficulty", metadata.difficulty());
    jsonManager.addProperty(metadataObject, "hint", metadata.hint());
    JsonArray gridArray = turnGridToJson(level);
    buildJsonObject(metadataJson, metadata, gridSizeObject);
    jsonManager.addArrayToJson(grid, "cells", gridArray);
    jsonManager.addObject(metadataJson, "grid", grid);
    jsonManager.addObject(grid, "metadata", metadataObject);
    return metadataJson;
  }

  /**
   * Converts the 2D grid of the level into a JsonArray.
   *
   * @param level The Level object containing the grid to be converted.
   * @return A JsonArray representing the grid of the level.
   */
  private JsonArray turnGridToJson(Level level) {
    List<List<List<String>>> levelGrid = level.getParsedGrid();
    System.out.println("PRINITNG PARSED GRID");
    printParsedGrid(levelGrid);
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
    jsonManager.addProperty(metadataJson, "description", metadata.description());
    jsonManager.addProperty(gridSizeObject, "rows", String.valueOf(grid.isLoading() ? metadata.rows() :metadata.rows() + 2));
    jsonManager.addProperty(gridSizeObject, "columns", String.valueOf( grid.isLoading() ? metadata.cols() : metadata.cols() + 2));

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
  private JsonArray convertGridToJsonArray(List<List<List<String>>> grid) {
    JsonArray layersArray = new JsonArray();

    // Iterate over each layer
    for (List<List<String>> layer : grid) {
      JsonArray singleLayerArray = new JsonArray();

      // Iterate over each row in the layer
      for (List<String> row : layer) {
        JsonArray rowArray = new JsonArray();

        // Iterate over each cell in the row
        for (String cell : row) {
          if (cell.startsWith("BabaVisualBlock") || cell.matches("BabaVisualBlock.*")) {
            rowArray.add("BabaVisualBlock");
            System.out.println(cell);
          } else {
            rowArray.add(cell);
          }
        }
        singleLayerArray.add(rowArray);
      }

      layersArray.add(singleLayerArray);
    }

    return layersArray;
  }

  /**
   * Method to allow LevelParser to have file save functionality.
   *
   * @param jsonObject The jsonObject to save to file.
   * @param fileName   The template name of the file.
   * @throws IOException Throws IOException if error with file saving.
   */
  public File saveJSON(JsonObject jsonObject, String fileName) throws IOException {
    return jsonManager.saveToFile(jsonObject, fileName);
  }

  public void printParsedGrid(List<List<List<String>>> levelGrid) {
    int rowNum = 0;
    for (List<List<String>> row : levelGrid) {
      int colNum = 0;
      System.out.println("Row " + rowNum + ":");
      for (List<String> cell : row) {
        System.out.print("    Column " + colNum + ": [");
        for (String blockType : cell) {
          System.out.print(blockType + ", ");
        }
        System.out.println("]");
        colNum++;
      }
      rowNum++;
    }
  }

}
