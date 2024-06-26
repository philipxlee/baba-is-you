package oogasalad.model.authoring.level;

/**
 * LevelMetadata record encapsulates the metadata for a Level.
 *
 * @param levelName   The name of the level.
 * @param description The description of the level.
 * @param authorName  The name of the author who created the level.
 * @param rows        Number of rows in level.
 * @param cols        Number of columns in level.
 */
public record LevelMetadata(String levelName, String description, int rows, int cols,
                            String difficulty, String authorName, String hint) {

}