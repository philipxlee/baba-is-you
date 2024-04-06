package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;

public class Pushable implements Strategy {

  @Override
  public void execute(AbstractVisualBlock block) {

  }
  @Override
  public String toString(){
    return "Pushable";
  }

  @Override
  public boolean interactWith(AbstractVisualBlock targetBlock, Strategy initiatingBlockStrategy) {
    return false;
  }
}
