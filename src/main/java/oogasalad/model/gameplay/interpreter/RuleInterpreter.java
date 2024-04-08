package oogasalad.model.gameplay.interpreter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.utils.exceptions.VisitorReflectionException;

/**
 * The RuleInterpreter class is responsible for interpreting and applying rules based on the game's
 * current state represented as a grid of blocks.
 */
public class RuleInterpreter {

  private static final String VISITOR_PACKAGE = "oogasalad.model.gameplay.blocks.blockvisitor.";
  private static final String TEXT_BLOCK_SUFFIX = "TextBlock";
  private static final String VISITOR_SUFFIX = "Visitor";
  private static final String REPLACEMENT = "";
  private static final String NOUN = "NOUN";
  private static final String VERB = "VERB";
  private static final String PROPERTY = "PROPERTY";

  /**
   * Interprets and applies rules across the entire grid based on the detected text block patterns.
   *
   * @param grid The game grid to be interpreted.
   */
  public void interpretRules(List<AbstractBlock>[][] grid) throws VisitorReflectionException {
    checkHorizontalRules(grid);
    checkVerticalRules(grid);
  }

  /**
   * Checks for horizontal rules in the grid.
   *
   * @param grid The game grid to be interpreted.
   */
  private void checkHorizontalRules(List<AbstractBlock>[][] grid) {
    Arrays.stream(grid).forEach(row -> IntStream.rangeClosed(0, row.length - 3)
        .forEach(i -> row[i].
            forEach(block1 -> row[i + 1]
                .forEach(block2 -> row[i + 2]
                    .forEach(block3 -> checkAndProcessRuleAt(block1, block2, block3, grid))))));
  }

  /**
   * Checks for vertical rules in the grid.
   *
   * @param grid The game grid to be interpreted.
   */
  private void checkVerticalRules(List<AbstractBlock>[][] grid) {
    IntStream.range(0, grid[0].length)
        .forEach(i -> IntStream.rangeClosed(0, grid.length - 3)
            .forEach(j -> grid[j][i]
                .forEach(block1 -> grid[j + 1][i]
                    .forEach(block2 -> grid[j + 2][i]
                        .forEach(block3 -> checkAndProcessRuleAt(block1, block2, block3, grid))))));
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
    List<String> firstGrammarList = first.getBlockGrammar();
    List<String> secondGrammarList = second.getBlockGrammar();
    List<String> thirdGrammarList = third.getBlockGrammar();
    return Stream.of(first, second, third).allMatch(AbstractBlock::isTextBlock)
        && firstGrammarList.contains(NOUN)
        && secondGrammarList.contains(VERB)
        && thirdGrammarList.contains(PROPERTY);
  }

  /**
   * Applies a visitor to all matching blocks within the grid.
   *
   * @param visitor   The visitor to apply.
   * @param blockName The name of blocks the visitor is applied to.
   * @param grid      The game grid, a two-dimensional array of lists of AbstractBlocks.
   */
  private void applyVisitorToMatchingBlocks(BlockVisitor visitor, String blockName, List<AbstractBlock>[][] grid) {
    //System.out.printf("Applying visitor to visual block of: %s%n", blockName);
    for (List<AbstractBlock>[] row : grid) {
      for (List<AbstractBlock> cell : row) {
        cell.stream()
            .filter(block -> !block.isTextBlock() && block.matches(blockName))
            .forEach(block -> block.accept(visitor));
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
    String className = VISITOR_PACKAGE
        + blockName.replace(TEXT_BLOCK_SUFFIX, REPLACEMENT)
        + VISITOR_SUFFIX;
    try {
      Class<?> visitorClass = Class.forName(className);
      return (BlockVisitor) visitorClass.getDeclaredConstructor().newInstance();
    } catch (ReflectiveOperationException e) {
      throw new VisitorReflectionException(blockName, e);
    }
  }
}
