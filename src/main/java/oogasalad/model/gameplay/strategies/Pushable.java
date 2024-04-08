package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.Grid;

public class Pushable implements Strategy {

  @Override
  public void execute(Grid grid, BlockUpdater updater, int i, int j, int k) {

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
