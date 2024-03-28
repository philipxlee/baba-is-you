package oogasalad.model.gameplay.blocks.VisualBlocks;

import oogasalad.model.gameplay.strategies.Controllable;
import oogasalad.model.gameplay.strategies.Pushable;

public class BabaVisualBlock extends AbstractVisualBlock {

  public BabaVisualBlock(String name, String imagePath) {
    super(name, imagePath);
  }

  @Override
  public void initializeBehaviors() {
    addBehavior(new Controllable());
    addBehavior(new Pushable());
  }

}
