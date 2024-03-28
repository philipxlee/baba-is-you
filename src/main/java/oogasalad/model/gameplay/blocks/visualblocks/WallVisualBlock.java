package oogasalad.model.gameplay.blocks.visualblocks;

import oogasalad.model.gameplay.strategies.Stoppable;

public class WallVisualBlock extends AbstractVisualBlock {

  public WallVisualBlock(String name, String imagePath) {
    super(name, imagePath);
  }

  @Override
  public void initializeBehaviors() {
    addBehavior(new Stoppable());
  }

}
