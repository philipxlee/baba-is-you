package oogasalad.model.gameplay.interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.AttributeVisitor;
import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.blocks.blockvisitor.TransformationVisitor;
import oogasalad.model.gameplay.exceptions.VisitorReflectionException;
import oogasalad.shared.loader.PropertiesLoader;

/**
 * The RuleInterpreter class is responsible for interpreting and applying rules based on the game's
 * current state represented as a grid of blocks.
 *
 * @author Philip Lee.
 */
public class RuleInterpreter {

  private static final String BLOCK_BEHAVIOR_PATH = "blockbehaviors/behaviors.properties";
  private static final String TEXT_BLOCK_SUFFIX = "TextBlock";
  private static final String REPLACEMENT = "";
  private static final String REGEX_SPLIT = ",";
  private static final String NOUN = "NOUN";
  private static final String VERB = "VERB";
  private static final String PROPERTY = "PROPERTY";
  private static final String TRANSFORM = "Transform";
  private static final String ATTRIBUTE = "Attribute";
  private static final String ATTRIBUTE_VISITS = "attributeVisits";
  private static final String BECOMES_VISITS = "becomesVisits";

  private final Properties properties;
  private final Map<String, String> behaviorMap;

  /**
   * Constructor for the RuleInterpreter class.
   */
  public RuleInterpreter() {
    properties = PropertiesLoader.loadProperties(BLOCK_BEHAVIOR_PATH);
    behaviorMap = new HashMap<>();
    loadBehaviorMappings();
  }

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
      String property = thirdBlock.getBlockName().replace(TEXT_BLOCK_SUFFIX, REPLACEMENT);
      Optional<BlockVisitor> optionalVisitor = determineVisitor(property);
      optionalVisitor.ifPresent(visitor -> applyVisitorToMatchingBlocks(visitor,
          firstBlock.getBlockName().replace(TEXT_BLOCK_SUFFIX, REPLACEMENT), grid));
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
    List<String> firstGrammarList = iteratorToList(first.getBlockGrammarIterator());
    List<String> secondGrammarList = iteratorToList(second.getBlockGrammarIterator());
    List<String> thirdGrammarList = iteratorToList(third.getBlockGrammarIterator());
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
  private void applyVisitorToMatchingBlocks(BlockVisitor visitor, String blockName,
      List<AbstractBlock>[][] grid) {
    for (List<AbstractBlock>[] row : grid) {
      for (List<AbstractBlock> cell : row) {
        cell.stream()
            .filter(block -> !block.isTextBlock() && block.matches(blockName))
            .forEach(block -> block.accept(visitor));
      }
    }
  }

  /**
   * Determines the visitor to apply based on the property name.
   *
   * @param propertyName The name of the property.
   * @return The visitor to apply.
   */
  private Optional<BlockVisitor> determineVisitor(String propertyName) {
    String behaviorType = behaviorMap.get(propertyName + TEXT_BLOCK_SUFFIX);
    if (ATTRIBUTE.equals(behaviorType)) {
      return Optional.of(new AttributeVisitor(propertyName));
    } else if (TRANSFORM.equals(behaviorType)) {
      return Optional.of(new TransformationVisitor(propertyName));
    }
    return Optional.empty();
  }

  /**
   * Loads the behavior mappings from the properties file.
   */
  private void loadBehaviorMappings() {
    buildAttributeBlockMapping();
    buildBecomesBlockMapping();
  }

  /**
   * Builds the mapping of attribute blocks to their respective behaviors.
   * For example, a "YouTextBlock" maps to an "Attribute" behavior.
   */
  private void buildAttributeBlockMapping() {
    String attributes = properties.getProperty(ATTRIBUTE_VISITS);
    Arrays.stream(attributes.split(REGEX_SPLIT))
        .forEach(a -> behaviorMap.put(a, ATTRIBUTE));
  }

  /**
   * Builds the mapping of "becomes" blocks to their respective behaviors.
   * For example, a "TransformTextBlock" maps to a "Transform" behavior.
   */
  private void buildBecomesBlockMapping() {
    String transforms = properties.getProperty(BECOMES_VISITS);
    Arrays.stream(transforms.split(REGEX_SPLIT))
        .forEach(t -> behaviorMap.put(t, TRANSFORM));
  }

  /**
   * Converts an iterator to a list.
   *
   * @param iterator The iterator to convert.
   * @return The list of elements from the iterator.
   */
  private List<String> iteratorToList(Iterator<String> iterator) {
    List<String> list = new ArrayList<>();
    iterator.forEachRemaining(list::add);
    return list;
  }

}
