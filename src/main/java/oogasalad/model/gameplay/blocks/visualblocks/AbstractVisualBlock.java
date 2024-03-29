package oogasalad.model.gameplay.blocks.visualblocks;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.strategies.Strategy;

public abstract class AbstractVisualBlock extends AbstractBlock {

  private final String name;
  private final List<Strategy> behaviors;

  public AbstractVisualBlock(String name) {
    this.name = name;
    this.behaviors = new ArrayList<>();
  }

  public abstract void accept(BlockVisitor visitor);

  @Override
  public boolean hasBehavior(Class<? extends Strategy> behaviorType) {
    return behaviors.stream().anyMatch(behaviorType::isInstance);
  }

 @Override
  public boolean matches(String descriptor) {
    String normalizedBlockName = this.name.replace("VisualBlock", "");
    return normalizedBlockName.equalsIgnoreCase(descriptor.replace("TextBlock", ""));
  }

  public void addBehavior(Strategy behavior) {
    this.behaviors.add(behavior);
  }

  public void resetAllBehaviors() {
    behaviors.clear();
  }

  public String getBlockName() {
    return name;
  }
}
