package oogasalad.model.gameplay.level;

/**
 * LevelMetadata record encapsulates the metadata for a Level.
 *
 * @param levelName            The name of the level.
 * @param difficulty           The difficulty of rows in level.
 * @param rows                 number of rows in the grid.
 * @param columns              number of columns in the grid.
 * @param initialConfiguration starting state of the grid.
 */
public record LevelMetadata(String levelName, String difficulty, int rows,
                            int columns, String[][][] initialConfiguration, String authorName,
                            String hint) {
  //all private final by default
}
