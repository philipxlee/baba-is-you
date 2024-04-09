package oogasalad.model.gameplay.level;

/**
 * LevelMetadata record encapsulates the metadata for a Level.
 *
 * @param levelName   The name of the level.
 * @param difficulty  The difficulty of rows in level.
 * @param health      How much health user has left.
 * @param rows        number of rows in the grid.
 * @param columns     number of columns in the grid.
 * @param initialConfiguration  starting state of the grid.
 */
public record LevelMetadata(String levelName, String difficulty, String health, int rows, int columns, String[][][] initialConfiguration) {
    //all private final by default
}
