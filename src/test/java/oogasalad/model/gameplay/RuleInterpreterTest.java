package oogasalad.model.gameplay;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.textblocks.TextBlock;
import oogasalad.model.gameplay.interpreter.RuleInterpreter;
import oogasalad.model.gameplay.strategies.attributes.Controllable;
import oogasalad.model.gameplay.strategies.attributes.Pushable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RuleInterpreterTest {

  private RuleInterpreter ruleInterpreter;
  private List<AbstractBlock>[][] grid;

  @BeforeEach
  void setUp() {
    // Initialization
    grid = new List[3][3];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = new ArrayList<>();
      }
    }

    // Horizontal rules
    grid[0][0].add(new TextBlock("Baba"));
    grid[0][1].add(new TextBlock("Is"));
    grid[0][2].add(new TextBlock("You"));

    // Vertical rules
    grid[1][0].add(new TextBlock("Rock"));
    grid[1][1].add(new TextBlock("Is"));
    grid[1][2].add(new TextBlock("Push"));

    // Add visual blocks for testing
    grid[2][0].add(new BabaVisualBlock("BabaVisualBlock", 2, 0));
    grid[2][0].add(new RockVisualBlock("RockVisualBlock", 2, 0));

    ruleInterpreter = new RuleInterpreter();
  }

  @Test
  void testHorizontalInterpretRules() throws Exception {
    ruleInterpreter.interpretRules(grid);
    assertTrue(grid[2][0].get(0) instanceof BabaVisualBlock, "BabaVisualBlock should recognize the 'Baba Is You' rule.");
    assertTrue(grid[2][0].get(0).hasBehavior(Controllable.class), "BabaVisualBlock should have the Controllable behavior.");
  }

  @Test
  void testVerticalInterpretRules() throws Exception {
    ruleInterpreter.interpretRules(grid);
    assertTrue(grid[2][0].get(1) instanceof RockVisualBlock, "RockVisualBlock should recognize the 'Rock Is Push' rule.");
    assertTrue(grid[2][0].get(1).hasBehavior(Pushable.class), "RockVisualBlock should have the Pushable behavior.");
  }
}
