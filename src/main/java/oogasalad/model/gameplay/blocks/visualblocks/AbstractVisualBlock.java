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

  public void addBehavior(Strategy behavior) {
    this.behaviors.add(behavior);
  }

  public void removeBehavior(Class<? extends Strategy> behaviorType) {
    behaviors.removeIf(behaviorType::isInstance);
  }

  @Override
  public boolean hasBehavior(Class<? extends Strategy> behaviorType) {
    return behaviors.stream().anyMatch(behaviorType::isInstance);
  }

 @Override
  public boolean matches(String descriptor) {
    // Remove "VisualBlock" from the block's class name and compare it to the descriptor
    String normalizedBlockName = this.name.replace("VisualBlock", "");
    return normalizedBlockName.equalsIgnoreCase(descriptor.replace("TextBlock", ""));
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