package oogasalad.model.gameplay.strategies.attributes;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * This class is a strategy that is used to control the behavior of a block. It is used to implement
 * the Strategy design pattern.
 */
public class Controllable implements Strategy {

  /**
   * Constructor for Controllable.
   *
   * @param block    block to control.
   * @param updater  updater to update block.
   * @param iterator iterator to control.
   */
  @Override
  public void execute(AbstractBlock block, BlockUpdater updater, CellIterator iterator) {

  }

}
