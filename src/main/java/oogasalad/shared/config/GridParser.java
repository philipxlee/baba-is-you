package oogasalad.shared.config;

import com.google.gson.JsonArray;

public interface GridParser {

  /**
   * Converts the grid data from a JsonArray into a 3D array of Strings.
   *
   * @param gridJson The JsonObject containing the grid data.
   * @return A 3D String array representing the cells in the level's grid.
   */
  default String[][][] parseGrid(JsonArray gridJson) {
    String[][][] cells = new String[gridJson.size()][][];

    for (int i = 0; i < gridJson.size(); i++) {
      JsonArray layer = gridJson.get(i).getAsJsonArray();
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
