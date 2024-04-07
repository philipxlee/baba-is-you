package oogasalad.model.gameplay.grid;

import oogasalad.model.gameplay.factory.BlockFactory;

public class BlockUpdater {

  private final Grid grid;
  private final BlockFactory factory;

  public BlockUpdater(Grid grid, BlockFactory factory) {
    this.grid = grid;
    this.factory = factory;
  }

  public void updateBlock(int i, int j, int k, String newBlockType) {
    grid.getGrid()[i][j].set(k, factory.createBlock(newBlockType));
  }

}
