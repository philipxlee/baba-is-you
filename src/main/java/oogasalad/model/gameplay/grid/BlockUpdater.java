package oogasalad.model.gameplay.grid;

import java.util.List;
import java.util.stream.Collectors;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;

public class BlockUpdater {

  private final Grid grid;
  private final BlockFactory factory;

  public BlockUpdater(Grid grid, BlockFactory factory) {
    this.grid = grid;
    this.factory = factory;
  }

  public void updateBlock(int i, int j, int k, String newBlockType) {
    List<AbstractBlock>[][] gameGrid = grid.getGrid();

    boolean containsTextBlock = gameGrid[i][j].stream()
        .anyMatch(AbstractBlock::isTextBlock);
    boolean containsVisualBlock = gameGrid[i][j].stream()
        .anyMatch(block -> !block.isTextBlock() && !block.getBlockName().equals("EmptyVisualBlock"));

    if (!containsTextBlock && !containsVisualBlock) {
      gameGrid[i][j].set(k, factory.createBlock(newBlockType));
    }

  }

}
