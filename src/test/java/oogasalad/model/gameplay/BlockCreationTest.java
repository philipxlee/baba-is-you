package oogasalad.model.gameplay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.textblocks.TextBlock;
import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.exceptions.InvalidBlockName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlockCreationTest {

  private BlockFactory blockFactory;

  @BeforeEach
  public void setUp() {
    blockFactory = new BlockFactory();
  }

  @Test
  public void testBabaVisualBlockCreation() throws InvalidBlockName {
    AbstractBlock babaBlock = blockFactory.createBlock("BabaVisualBlock", 1, 2);
    assertInstanceOf(BabaVisualBlock.class, babaBlock);
    assertEquals(1, babaBlock.getRow());
    assertEquals(2, babaBlock.getCol());
    assertEquals("BabaVisualBlock", babaBlock.getBlockName());
  }

  @Test
  public void testEmptyVisualBlockCreation() throws InvalidBlockName {
    AbstractBlock emptyBlock = blockFactory.createBlock("EmptyVisualBlock", 0, 0);
    assertInstanceOf(EmptyVisualBlock.class, emptyBlock);
    assertEquals(0, emptyBlock.getRow());
    assertEquals(0, emptyBlock.getCol());
    assertEquals("EmptyVisualBlock", emptyBlock.getBlockName());
  }

  @Test
  public void testFlagVisualBlockCreation() throws InvalidBlockName {
    AbstractBlock flagBlock = blockFactory.createBlock("FlagVisualBlock", 3, 4);
    assertInstanceOf(FlagVisualBlock.class, flagBlock);
    assertEquals(3, flagBlock.getRow());
    assertEquals(4, flagBlock.getCol());
    assertEquals("FlagVisualBlock", flagBlock.getBlockName());
  }

  @Test
  public void testRockVisualBlockCreation() throws InvalidBlockName {
    AbstractBlock rockBlock = blockFactory.createBlock("RockVisualBlock", 5, 6);
    assertInstanceOf(RockVisualBlock.class, rockBlock);
    assertEquals(5, rockBlock.getRow());
    assertEquals(6, rockBlock.getCol());
    assertEquals("RockVisualBlock", rockBlock.getBlockName());
  }

  @Test
  public void testWallVisualBlockCreation() throws InvalidBlockName {
    AbstractBlock wallBlock = blockFactory.createBlock("WallVisualBlock", 7, 8);
    assertInstanceOf(WallVisualBlock.class, wallBlock);
    assertEquals(7, wallBlock.getRow());
    assertEquals(8, wallBlock.getCol());
    assertEquals("WallVisualBlock", wallBlock.getBlockName());
  }

  @Test
  public void testYouTextBlockCreation() {
    TextBlock youTextBlock = new TextBlock("You");
    assertTrue(youTextBlock.isTextBlock());
    assertEquals("YouTextBlock", youTextBlock.getBlockName());
    assertNotNull(youTextBlock.getBlockGrammar());
  }

  @Test
  public void testWinTextBlockCreation() {
    TextBlock winTextBlock = new TextBlock("Win");
    assertTrue(winTextBlock.isTextBlock());
    assertEquals("WinTextBlock", winTextBlock.getBlockName());
    assertNotNull(winTextBlock.getBlockGrammar());
  }

  @Test
  public void testFlagTextBlockCreation() {
    TextBlock flagTextBlock = new TextBlock("Flag");
    assertTrue(flagTextBlock.isTextBlock());
    assertEquals("FlagTextBlock", flagTextBlock.getBlockName());
    assertNotNull(flagTextBlock.getBlockGrammar());
  }

  @Test
  public void testRockTextBlockCreation() {
    TextBlock rockTextBlock = new TextBlock("Rock");
    assertTrue(rockTextBlock.isTextBlock());
    assertEquals("RockTextBlock", rockTextBlock.getBlockName());
    assertNotNull(rockTextBlock.getBlockGrammar());
  }

  @Test
  public void testBabaTextBlockCreation() {
    TextBlock babaTextBlock = new TextBlock("Baba");
    assertTrue(babaTextBlock.isTextBlock());
    assertEquals("BabaTextBlock", babaTextBlock.getBlockName());
    assertNotNull(babaTextBlock.getBlockGrammar());
  }

  @Test
  public void testWallTextBlockCreation() {
    TextBlock wallTextBlock = new TextBlock("Wall");
    assertTrue(wallTextBlock.isTextBlock());
    assertEquals("WallTextBlock", wallTextBlock.getBlockName());
    assertNotNull(wallTextBlock.getBlockGrammar());
  }

  @Test
  public void testEmptyTextBlockCreation() {
    TextBlock emptyTextBlock = new TextBlock("Empty");
    assertTrue(emptyTextBlock.isTextBlock());
    assertEquals("EmptyTextBlock", emptyTextBlock.getBlockName());
    assertNotNull(emptyTextBlock.getBlockGrammar());
  }

  @Test
  public void testFlagTextBlockCreationWithInvalidName() {
    assertThrows(InvalidBlockName.class, () -> blockFactory.createBlock("FlagText", 0, 0));
  }

}
