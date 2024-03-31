package oogasalad.model.gameplay.utils.exceptions;

/**
 * Exception thrown when the block name is invalid.
 */
public class InvalidBlockName extends RuntimeException {

  /**
   * Throws an exception when the block name is invalid.
   *
   * @param blockName the name of the block.
   */
  public InvalidBlockName(String blockName) {
    super(blockName);
  }

}
