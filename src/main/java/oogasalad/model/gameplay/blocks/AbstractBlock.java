package oogasalad.model.gameplay.blocks;

import oogasalad.model.gameplay.blocks.blockvisitor.BlockVisitor;

public class AbstractBlock {

  public boolean isTextBlock() {
    return false;
  }

  public boolean matches(String descriptor) {
    return this.getClass().getSimpleName().equals(descriptor);
  }

  public void accept(BlockVisitor visitor) {
    // Default implementation does nothing.
    // Only visual blocks will override this to actually accept visitors.
  }

  public String getBlockName() {
    return this.getClass().getSimpleName();
  }

}
