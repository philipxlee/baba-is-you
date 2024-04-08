package oogasalad.model.gameplay.level;

/**
 * LevelMetadata record encapsulates the metadata for a Level.
 *
 * @param levelName   The name of the level.
 * @param Difficulty  The difficulty of rows in level.
 * @param Health      How much health user has left.
 */
public record LevelMetadata(String levelName, String Difficulty, String Health, int rows, int columns, String[][][] initialConfiguration) {
    //all private final by default
}
