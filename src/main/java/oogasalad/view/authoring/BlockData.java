package oogasalad.view.authoring;

/**
 * Data structure to hold block information.
 */
public class BlockData {

  private final String name;
  private final String imagePath;
  private final String category;

  /**
   * Constructs a new BlockData instance.
   *
   * @param name      The name of the block.
   * @param imagePath The path to the block's image file.
   * @param category  The category of the block.
   */
  public BlockData(String name, String imagePath, String category) {
    this.name = name;
    this.imagePath = imagePath;
    this.category = category;
  }

  /**
   * Gets the name of the block.
   *
   * @return The name of the block.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the path to the block's image file.
   *
   * @return The path to the image file.
   */
  public String getImagePath() {
    return imagePath;
  }

}
