package oogasalad.model.gameplay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.TransformationVisitor;
import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.LavaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WaterVisualBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.grid.BlockUpdater;
import oogasalad.model.gameplay.grid.CellIterator;
import oogasalad.model.gameplay.grid.Grid;
import org.junit.jupiter.api.Test;

public class TransformationVisitorTest {

  private Grid setUpGrid(String blockType) {
    String[][][] initialConfig = new String[1][1][1];
    initialConfig[0][0][0] = blockType;
    return new Grid(1, 1, initialConfig);
  }

  private void performTransformationTest(String initialBlockType, String targetType,
      Class<?> expectedClass) {
    Grid grid = setUpGrid(initialBlockType);
    TransformationVisitor visitor = new TransformationVisitor(targetType);
    AbstractBlock block = grid.getGrid()[0][0].get(0);
    BlockUpdater blockUpdater = new BlockUpdater(grid, new BlockFactory());
    CellIterator cellIterator = new CellIterator(grid.getGrid(), 0, 0);
    assertEquals(initialBlockType, block.getBlockName(),
        "Initial block should be " + initialBlockType);

    // Cast and visit based on block type
    if (block instanceof BabaVisualBlock) {
      visitor.visit((BabaVisualBlock) block);
    } else if (block instanceof WallVisualBlock) {
      visitor.visit((WallVisualBlock) block);
    } else if (block instanceof RockVisualBlock) {
      visitor.visit((RockVisualBlock) block);
    } else if (block instanceof FlagVisualBlock) {
      visitor.visit((FlagVisualBlock) block);
    } else if (block instanceof EmptyVisualBlock) {
      visitor.visit((EmptyVisualBlock) block);
    } else if (block instanceof LavaVisualBlock) {
      visitor.visit((LavaVisualBlock) block);
    } else if (block instanceof WaterVisualBlock) {
      visitor.visit((WaterVisualBlock) block);
    }

    // Execute behaviors
    for (AbstractBlock blk : grid.getGrid()[0][0]) {
      blk.executeBehaviors(blk, blockUpdater, cellIterator);
    }
    assertInstanceOf(expectedClass, grid.getGrid()[0][0].get(0),
        "Block should have transformed into " + expectedClass.getSimpleName());
  }

  @Test
  public void testBabaToWater() {
    performTransformationTest("BabaVisualBlock", "Water", WaterVisualBlock.class);
  }

  @Test
  public void testRockToBaba() {
    performTransformationTest("RockVisualBlock", "Baba", BabaVisualBlock.class);
  }

  @Test
  public void testFlagToLava() {
    performTransformationTest("FlagVisualBlock", "Lava", LavaVisualBlock.class);
  }

  @Test
  public void testLavaToEmpty() {
    performTransformationTest("LavaVisualBlock", "Empty", EmptyVisualBlock.class);
  }

  @Test
  public void testWaterToWall() {
    performTransformationTest("WaterVisualBlock", "Wall", WallVisualBlock.class);
  }

  @Test
  public void testEmptyToRock() {
    performTransformationTest("EmptyVisualBlock", "Rock", RockVisualBlock.class);
  }

  @Test
  public void testWallToFlag() {
    performTransformationTest("WallVisualBlock", "Flag", FlagVisualBlock.class);
  }
}
