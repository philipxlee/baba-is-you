package oogasalad.model.gameplay.blocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;
import oogasalad.model.gameplay.strategies.Controllable;
import oogasalad.model.gameplay.strategies.Strategy;

public class AbstractBlock {

  public boolean isTextBlock() {
    return false;
  }

  public boolean matches(String descriptor) {
    return this.getClass().getSimpleName().equals(descriptor);
  }

  public boolean hasBehavior(Class<? extends Strategy> behaviorType) { return false;}

  public String getBlockName() {
    return this.getClass().getSimpleName();
  }

  public void accept(BlockVisitor visitor) {
    // Do nothing.
    // Only visual blocks will override this to actually accept visitors.
  }

  public void resetAllBehaviors() {
    // Do nothing.
    // Only visual blocks will override this to reset behaviors.
  }
}
