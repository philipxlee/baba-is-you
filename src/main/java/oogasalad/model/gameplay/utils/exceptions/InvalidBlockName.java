package oogasalad.model.gameplay.utils.exceptions;

/**
 * Exception thrown when the block name is invalid.
 */
public class InvalidBlockName extends Exception {

  /**
   * Throws an exception when the block name is invalid.
   *
   * @param blockName the name of the block.
   */
  public InvalidBlockName(String blockName) {
    super("Invalid Block Name " + blockName + ". Check the block name in the JSON file.");
  }

}
