package oogasalad.model.gameplay.factory;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;

/**
 * Factory class to create block instances based on block names using reflection.
 */
public class BlockFactory {

  private static final String PACKAGE_PREFIX = "oogasalad.model.gameplay.blocks.";

  /**
   * Creates a new BlockFactory.
   */
  public BlockFactory() {
  }

  private static void validateBlockName(String blockName, Class<?> blockClass) {
    if (!AbstractBlock.class.isAssignableFrom(blockClass)) {
      throw new InvalidBlockName(
          "Block name does not correspond to a valid block type: " + blockName);
    }
  }

  /**
   * Creates a block instance based on the provided block name using reflection.
   *
   * @param blockName The name of the block to create.
   * @return An instance of AbstractBlock corresponding to the blockName.
   * @throws InvalidBlockName If the block name does not correspond to a valid block class.
   */
  public AbstractBlock createBlock(String blockName) throws InvalidBlockName {
    try {
      String className = determineClassName(blockName);
      Class<?> blockClass = Class.forName(className);
      validateBlockName(blockName, blockClass);
      return (AbstractBlock) blockClass.getDeclaredConstructor(String.class).newInstance(blockName);
    } catch (ReflectiveOperationException e) {
      throw new InvalidBlockName("Error creating block instance for: " + blockName);
    }
  }

  private String determineClassName(String blockName) {
    String packageSuffix = blockName.contains("Visual") ? "visualblocks." :
        blockName.contains("Text") ? "textblocks." :
            "";

    // Construct the full class name.
    return PACKAGE_PREFIX + packageSuffix + blockName;
  }
}
