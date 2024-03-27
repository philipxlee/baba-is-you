package oogasalad.model.gameplay.blocks;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * This class represents a block in the game. A block is a unit of the game that can have multiple
 * strategies applied to it. The block can be of different types, and each type can have different
 * strategies applied to it.
 */
public abstract class Block {

  private final String name;
  private final String imagePath;
  private final List<Strategy> strategies;

  /**
   * Constructor for the block class.
   *
   * @param name      The name of the block
   * @param imagePath The path to the image of the block
   */
  public Block(String name, String imagePath) {
    this.name = name;
    this.imagePath = imagePath;
    this.strategies = new ArrayList<>();
  }

  /**
   * Adds a strategy to the block.
   *
   * @param strategy The strategy to be added to the block
   */
  public void addStrategy(Strategy strategy) {
    strategies.add(strategy);
  }

  /**
   * Removes a strategy from the block.
   *
   * @param strategyType The type of the strategy to be removed
   */
  public void removeStrategy(Class<? extends Strategy> strategyType) {
    strategies.removeIf(strategyType::isInstance);
  }

  /**
   * Applies the rules of the strategies to the block.
   *
   * @param behavior The behavior to be applied to the block
   */
  public void applyRules(String behavior) {
    for (Strategy strategy : strategies) {
      strategy.applyRule(behavior);
    }
  }

  /**
   * Interacts the block with another block.
   *
   * @return The block that the block interacts with
   */
  public String getBlockName() {
    return this.name;
  }

}
