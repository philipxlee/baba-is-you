package oogasalad.model.gameplay;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import oogasalad.model.gameplay.blocks.visualblocks.*;
import oogasalad.model.gameplay.blocks.textblocks.TextBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;

public class BlockCreationTest {

  private BlockFactory blockFactory;

  @BeforeEach
  public void setUp() {
    blockFactory = new BlockFactory();
  }

  @Test
  public void testBabaVisualBlockCreation() throws InvalidBlockName {
    AbstractBlock babaBlock = blockFactory.createBlock("BabaVisualBlock", 1, 2);
    assertTrue(babaBlock instanceof BabaVisualBlock);
    assertEquals(1, babaBlock.getRow());
    assertEquals(2, babaBlock.getCol());
    assertEquals("BabaVisualBlock", babaBlock.getBlockName());
  }

  @Test
  public void testEmptyVisualBlockCreation() throws InvalidBlockName {
    AbstractBlock emptyBlock = blockFactory.createBlock("EmptyVisualBlock", 0, 0);
    assertTrue(emptyBlock instanceof EmptyVisualBlock);
    assertEquals(0, emptyBlock.getRow());
    assertEquals(0, emptyBlock.getCol());
    assertEquals("EmptyVisualBlock", emptyBlock.getBlockName());
  }

  @Test
  public void testFlagVisualBlockCreation() throws InvalidBlockName {
    AbstractBlock flagBlock = blockFactory.createBlock("FlagVisualBlock", 3, 4);
    assertTrue(flagBlock instanceof FlagVisualBlock);
    assertEquals(3, flagBlock.getRow());
    assertEquals(4, flagBlock.getCol());
    assertEquals("FlagVisualBlock", flagBlock.getBlockName());
  }

  @Test
  public void testRockVisualBlockCreation() throws InvalidBlockName {
    AbstractBlock rockBlock = blockFactory.createBlock("RockVisualBlock", 5, 6);
    assertTrue(rockBlock instanceof RockVisualBlock);
    assertEquals(5, rockBlock.getRow());
    assertEquals(6, rockBlock.getCol());
    assertEquals("RockVisualBlock", rockBlock.getBlockName());
  }

  @Test
  public void testWallVisualBlockCreation() throws InvalidBlockName {
    AbstractBlock wallBlock = blockFactory.createBlock("WallVisualBlock", 7, 8);
    assertTrue(wallBlock instanceof WallVisualBlock);
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
