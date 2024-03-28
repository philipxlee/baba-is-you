package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.strategies.Pushable;

public class RockVisualBlock extends AbstractVisualBlock {

  public RockVisualBlock(String name, String imagePath) {
    super(name, imagePath);
  }

  @Override
  public void initializeBehaviors() {
    addBehavior(new Pushable());
  }
}
