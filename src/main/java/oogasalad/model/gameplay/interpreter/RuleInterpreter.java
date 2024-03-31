package oogasalad.model.gameplay.interpreter;

import java.util.List;
import oogasalad.model.gameplay.utils.exceptions.VisitorReflectionException;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

/**
 * The RuleInterpreter class is responsible for interpreting and applying rules based on the game's
 * current state represented as a grid of blocks.
 */
public class RuleInterpreter {

  private static final String VISITOR_PACKAGE = "oogasalad.model.gameplay.blocks.blockvisitor.";
  private static final String TEXT_BLOCK_SUFFIX = "TextBlock";
  private static final String VISITOR_SUFFIX = "Visitor";

  /**
   * Interprets and applies rules across the entire grid based on the detected text block patterns.
   *
   * @param grid The game grid to be interpreted.
   */
  public void interpretRules(List<AbstractBlock>[][] grid) throws VisitorReflectionException {
    for (List<AbstractBlock>[] row : grid) {
      for (int col = 0; col <= row.length - 3; col++) {
        // Iterate over every combination of blocks in the three adjacent positions
        for (AbstractBlock block1 : row[col]) {
          for (AbstractBlock block2 : row[col + 1]) {
            for (AbstractBlock block3 : row[col + 2]) {
              checkAndProcessRuleAt(block1, block2, block3, grid);
            }
          }
        }
      }
    }
  }


  /**
   * Processes a potential rule if the given blocks form a valid rule structure.
   *
   * @param firstBlock  The first block of the potential rule.
   * @param secondBlock The second block (usually "IS") of the potential rule.
   * @param thirdBlock  The third block defining the rule effect.
   * @param grid        The game grid.
   */
  private void checkAndProcessRuleAt(AbstractBlock firstBlock, AbstractBlock secondBlock,
      AbstractBlock thirdBlock, List<AbstractBlock>[][] grid) throws VisitorReflectionException {
    if (isValidRule(firstBlock, secondBlock, thirdBlock)) {
      BlockVisitor visitor = determineVisitor(thirdBlock.getBlockName());
      applyVisitorToMatchingBlocks(visitor, firstBlock.getBlockName(), grid);
    }
  }

  /**
   * Validates if the combination of blocks can form a rule.
   *
   * @param first  The first block in the rule.
   * @param second The second block in the rule (typically representing "IS").
   * @param third  The third block in the rule, determining the effect.
   * @return true if the blocks form a valid rule; false otherwise.
   */
  private boolean isValidRule(AbstractBlock first, AbstractBlock second, AbstractBlock third) {
    return first.isTextBlock() && second.isTextBlock() && third.isTextBlock();
  }

  /**
   * Applies a visitor to all blocks in the grid that match the given block name.
   *
   * @param visitor   The visitor to apply.
   * @param blockName The name of the blocks to which the visitor should be applied.
   * @param grid      The game grid.
   */
  /**
   * Applies a visitor to all blocks in the grid that match the given block name.
   *
   * @param visitor   The visitor to apply.
   * @param blockName The name of the blocks to which the visitor should be applied.
   * @param grid      The game grid, where each cell contains a list of AbstractBlocks.
   */
  private void applyVisitorToMatchingBlocks(BlockVisitor visitor, String blockName, List<AbstractBlock>[][] grid) {
    for (List<AbstractBlock>[] row : grid) {
      for (List<AbstractBlock> cell : row) {
        for (AbstractBlock block : cell) {
          // Apply the visitor only to non-text blocks that match the block name.
          if (!block.isTextBlock() && block.matches(blockName)) {
            System.out.println("Applying visitor to block: " + block.getBlockName());
            block.accept(visitor);
          }
        }
      }
    }
  }

  /**
   * Determines the visitor to instantiate based on the block name, using reflections.
   *
   * @param blockName The name of the block for which to determine the visitor.
   * @return The corresponding BlockVisitor instance or null if none found.
   */
  private BlockVisitor determineVisitor(String blockName) {
    String className = VISITOR_PACKAGE + blockName.replace(TEXT_BLOCK_SUFFIX, "") + VISITOR_SUFFIX;
    try {
      Class<?> visitorClass = Class.forName(className);
      return (BlockVisitor) visitorClass.getDeclaredConstructor().newInstance();
    } catch (ReflectiveOperationException e) {
      throw new VisitorReflectionException(blockName, e);
    }
  }
}
