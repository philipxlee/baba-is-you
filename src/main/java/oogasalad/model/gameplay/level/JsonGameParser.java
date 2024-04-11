package oogasalad.model.gameplay.level;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import oogasalad.shared.config.JsonManager;

/**
 * Parses JSON representations of game levels into Level objects.
 */
public class JsonGameParser {

  private JsonManager jsonManager = new JsonManager();

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
    String levelName = jsonManager.getValue(levelJson, "levelName");
    JsonObject gridSize = jsonManager.getJsonObject(levelJson, "gridSize");
    int rows = Integer.parseInt(jsonManager.getValue(gridSize, "rows"));
    int columns = Integer.parseInt(jsonManager.getValue(gridSize, "columns"));
    JsonObject gridObject = jsonManager.getJsonObject(levelJson, "grid");
    JsonObject metadataJson = jsonManager.getJsonObject(gridObject, "metadata");
    String difficulty = jsonManager.getValue(metadataJson, "difficulty");
    String health = jsonManager.getValue(metadataJson, "health");

    String[][][] grid = parseGrid(jsonManager.getJsonObject(levelJson, "grid"));
    return new LevelMetadata(levelName, difficulty, health, rows, columns, grid);
  }


  /**
   * Converts the grid data from a JsonArray into a 3D array of Strings.
   *
   * @param gridJson The JsonObject containing the grid data.
   * @return A 3D String array representing the cells in the level's grid.
   */
  private String[][][] parseGrid(JsonObject gridJson) {
    JsonArray cellsArray = gridJson.getAsJsonArray("cells");
    String[][][] cells = new String[cellsArray.size()][][];

    for (int i = 0; i < cellsArray.size(); i++) {
      JsonArray layer = cellsArray.get(i).getAsJsonArray();
      cells[i] = new String[layer.size()][];

      for (int j = 0; j < layer.size(); j++) {
        JsonArray row = layer.get(j).getAsJsonArray();
        cells[i][j] = new String[row.size()];

        for (int k = 0; k < row.size(); k++) {
          cells[i][j][k] = row.get(k).getAsString();
        }
      }
    }

    return cells;
  }
}

