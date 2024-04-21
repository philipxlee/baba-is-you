package oogasalad.model.gameplay.strategies;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;

/**
 * Interface defining a strategy or behavior that can be applied to a visual block, including
 * interactions with other strategies.
 */
public interface Strategy {

  void execute(AbstractBlock block, BlockUpdater updater, CellIterator iterator);


}
