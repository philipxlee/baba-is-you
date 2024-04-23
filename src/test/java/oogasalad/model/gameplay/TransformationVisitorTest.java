//package oogasalad.model.gameplay;
//
//import oogasalad.model.gameplay.blocks.blockvisitor.TransformationVisitor;
//import org.junit.jupiter.api.BeforeEach;
//import oogasalad.model.gameplay.blocks.visualblocks.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TransformationVisitorTest {
//
//  private TransformationVisitor visitor;
//  private BabaVisualBlock babaBlock;
//  private WallVisualBlock wallBlock;
//  private RockVisualBlock rockBlock;
//  private FlagVisualBlock flagBlock;
//  private EmptyVisualBlock emptyBlock;
//  private LavaVisualBlock lavaBlock;
//  private WaterVisualBlock waterBlock;
//
//  @BeforeEach
//  public void setUp() {
//    // Initialize with a sample type that corresponds to an actual Strategy implementation
//    visitor = new TransformationVisitor("SomeValidStrategy");
//
//    // Initialize blocks
//    babaBlock = new BabaVisualBlock("BabaVisualBlock", 0, 0);
//    wallBlock = new WallVisualBlock("WallVisualBlock", 0, 0);
//    rockBlock = new RockVisualBlock("RockVisualBlock", 0, 0);
//    flagBlock = new FlagVisualBlock("FlagVisualBlock", 0, 0);
//    emptyBlock = new EmptyVisualBlock("EmptyVisualBlock", 0, 0);
//    lavaBlock = new LavaVisualBlock();
//    waterBlock = new WaterVisualBlock();
//  }
//
//  @Test
//  public void testVisitBabaBlock() {
//    // Assuming a method exists in BabaVisualBlock to verify if a Strategy has been added
//    visitor.visit(babaBlock);
//    assertTrue(babaBlock.hasBehavior(), "Strategy should have been added to Baba block");
//  }
//
//  @Test
//  public void testVisitWallBlock() {
//    visitor.visit(wallBlock);
//    assertTrue(wallBlock.hasBehavior(), "Strategy should have been added to Wall block");
//  }
//
//  // Continue with similar tests for other block types
//}