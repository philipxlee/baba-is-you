package oogasalad.model.gameplay.strategies.attributes;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * A strategy that makes a block stoppable, meaning it will not move when pushed.
 */
public class Stoppable extends Equal implements Strategy {

  /**
   * Executes the stoppable strategy.
   *
   * @param block    block to control.
   * @param updater  updater to update block.
   * @param iterator iterator to control.
   */
  @Override
  public void execute(AbstractBlock block, BlockUpdater updater, CellIterator iterator) {

  }

}
