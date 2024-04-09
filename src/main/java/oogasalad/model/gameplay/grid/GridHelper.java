package oogasalad.model.gameplay.grid;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.strategies.attributes.Controllable;

public class GridHelper {

  public GridHelper() {

  }

  public List<int[]> findControllableBlock(Grid grid) {
    List<int[]> AllControllableBlocks = new ArrayList<>();
    for (int i = 0; i < grid.gridWidth(); i++) {
      for (int j = 0; j < grid.gridHeight(); j++) {
        for (int k = 0; k < grid.cellSize(i, j); k++) {
          AbstractBlock block = grid.getBlock(i, j, k);
          if (block != null && block.hasBehavior(Controllable.class)) {
            int[] a = {i, j, k};
            AllControllableBlocks.add(a);
          }
        }
      }
    }
    return AllControllableBlocks;
  }
}
