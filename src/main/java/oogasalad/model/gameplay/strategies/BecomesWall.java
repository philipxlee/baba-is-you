package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.Grid;

public class BecomesWall implements Strategy {

  @Override
  public void execute(Grid grid, BlockUpdater updater, int i, int j, int k) {
    updater.updateBlock(i, j, k, "WallVisualBlock");
  }

  @Override
  public boolean interactWith(AbstractVisualBlock targetBlock, Strategy initiatingBlockStrategy) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String toString(){
    return "BecomesWall";
  }
}
