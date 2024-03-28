package oogasalad.model.gameplay.blocks.visualblocks;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.strategies.Strategy;

public abstract class AbstractVisualBlock extends AbstractBlock {

  private final String name;
  private final String imagePath;
  private final List<Strategy> behaviors;

  public AbstractVisualBlock(String name, String imagePath) {
    this.name = name;
    this.imagePath = imagePath;
    this.behaviors = new ArrayList<>();
  }

  public abstract void accept(BlockVisitor visitor);

  public abstract void initializeBehaviors();

  public void addBehavior(Strategy behavior) {
    this.behaviors.add(behavior);
  }

  public void removeBehavior(Class<? extends Strategy> behaviorType) {
    behaviors.removeIf(behaviorType::isInstance);
  }

  public void executeBehaviors() {
    for (Strategy behavior : behaviors) {
      behavior.execute(this);
    }
  }

  public String getBlockName() {
    return name;
  }
}
