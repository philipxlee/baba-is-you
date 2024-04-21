package oogasalad.model.gameplay.factory;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.textblocks.TextBlock;
import oogasalad.model.gameplay.exceptions.InvalidBlockName;

/**
 * Factory class for creating block instances. This class supports the creation of both text and
 * visual block types through a unified interface. It uses reflection for visual blocks and direct
 * instantiation for text blocks, abstracting the block creation process and enhancing
 * extensibility.
 */
public class BlockFactory {

  private static final String PACKAGE_PREFIX = "oogasalad.model.gameplay.blocks.";
  private static final String TEXT_BLOCK_SUFFIX = "TextBlock";
  private static final String VISUAL_BLOCK_LOCATION_SUFFIX = "visualblocks.";
  private static final String TEXT_BLOCK_LOCATION_SUFFIX = "textblocks.";
  private static final String VISUAL = "Visual";
  private static final String TEXT = "Text";
  private static final String REPLACEMENT = "";

  /**
   * Creates a block instance based on the provided block name. If the block name ends with
   * 'TextBlock', it creates a TextBlock instance. Otherwise, it uses reflection to create an
   * instance of the visual block.
   *
   * @param blockName The name of the block to create, which should match a class name.
   * @return An instance of AbstractBlock corresponding to the block name.
   * @throws InvalidBlockName If no valid block class corresponds to the block name.
   */
  public AbstractBlock createBlock(String blockName, int row, int col) throws InvalidBlockName {
    try {
      if (blockName.endsWith(TEXT_BLOCK_SUFFIX)) {
        return new TextBlock(blockName.replace(TEXT_BLOCK_SUFFIX, REPLACEMENT));
      } else {
        String className = PACKAGE_PREFIX + determinePackageSuffix(blockName) + blockName;
        Class<?> blockClass = Class.forName(className);
        validateBlockName(blockClass);
        return (AbstractBlock) blockClass
            .getDeclaredConstructor(String.class, int.class, int.class)
            .newInstance(blockName, row, col);
      }
    } catch (ReflectiveOperationException e) {
      throw new InvalidBlockName("Invalid Block Name");
    }
  }

  /**
   * Validates that the identified block class extends AbstractBlock.
   *
   * @param blockClass The class object corresponding to the block name.
   * @throws InvalidBlockName If the class does not extend AbstractBlock.
   */
  private void validateBlockName(Class<?> blockClass) throws InvalidBlockName {
    if (!AbstractBlock.class.isAssignableFrom(blockClass)) {
      throw new InvalidBlockName("Invalid block name");
    }
  }

  /**
   * Determines the package suffix based on the block type.
   *
   * @param blockName The name of the block for which the package suffix is needed.
   * @return The package suffix corresponding to the block type.
   */
  private String determinePackageSuffix(String blockName) {
    return blockName.contains(VISUAL) ? VISUAL_BLOCK_LOCATION_SUFFIX
        : blockName.contains(TEXT) ? TEXT_BLOCK_LOCATION_SUFFIX : REPLACEMENT;
  }
}
