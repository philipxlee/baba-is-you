package oogasalad.model.authoring.level;


public class LevelMetadata {

  private final String levelName;
  private final String description;
  private int rows;
  private int cols;
  private final String difficulty;
  private final String authorName;
  private final String hint;

  /**
   * LevelMetadata record encapsulates the metadata for a Level.
   *
   * @param levelName   The name of the level.
   * @param description The description of the level.
   * @param authorName  The name of the author who created the level.
   * @param rows        Number of rows in level.
   * @param cols        Number of columns in level.
   */
  public LevelMetadata(String levelName, String description, int rows, int cols,
      String difficulty, String authorName, String hint) {

    this.levelName = levelName;
    this.description = description;
    this.rows = rows;
    this.cols = cols;
    this.difficulty = difficulty;
    this.authorName = authorName;
    this.hint = hint;
  }

  public void setRows(int newRows) {
    this.rows = newRows;
  }

  public void setCols(int newCols) {
    this.cols = newCols;
  }

  public int getRows() {
    return this.rows;
  }

  public int getCols() {
    return this.cols;
  }

  public String getLevelName() {
    return this.levelName;
  }

  public String getAuthorName() {
    return this.authorName;
  }

  public String getDescription() {
    return this.description;
  }

  public String getDifficulty() {
    return this.difficulty;
  }

  public String getHint() {
    return this.hint;
  }
}