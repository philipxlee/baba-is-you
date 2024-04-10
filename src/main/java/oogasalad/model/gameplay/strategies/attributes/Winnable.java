package oogasalad.model.gameplay.strategies.attributes;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;
import oogasalad.model.gameplay.strategies.Strategy;

/**
 * Strategy for blocks that can be won by the player.
 */
public class Winnable implements Strategy {

  /**
   * Constructor for Winnable.
   *
   * @param block    block to control.
   * @param updater  updater to update block.
   * @param iterator iterator to control.
   */
  @Override
  public void execute(AbstractBlock block, BlockUpdater updater, CellIterator iterator) {
    // TODO Auto-generated method stub
  }

}
