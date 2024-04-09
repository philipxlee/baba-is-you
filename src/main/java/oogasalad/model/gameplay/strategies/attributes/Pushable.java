package oogasalad.model.gameplay.strategies.attributes;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.strategies.Strategy;

public class Pushable implements Strategy {

  @Override
  public void execute(Grid grid, BlockUpdater updater, int i, int j, int k) {

  }

  @Override
  public boolean interactWith(AbstractVisualBlock targetBlock, Strategy initiatingBlockStrategy) {
    return false;
  }
}
