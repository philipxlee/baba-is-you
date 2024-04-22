//package oogasalad.model.gameplay;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import oogasalad.model.gameplay.blocks.blockvisitor.BabaVisitor;
//import oogasalad.model.gameplay.blocks.blockvisitor.EmptyVisitor;
//import oogasalad.model.gameplay.blocks.blockvisitor.FlagVisitor;
//import oogasalad.model.gameplay.blocks.blockvisitor.PushVisitor;
//import oogasalad.model.gameplay.blocks.blockvisitor.RockVisitor;
//import oogasalad.model.gameplay.blocks.blockvisitor.StopVisitor;
//import oogasalad.model.gameplay.blocks.blockvisitor.WallVisitor;
//import oogasalad.model.gameplay.blocks.blockvisitor.WinVisitor;
//import oogasalad.model.gameplay.blocks.blockvisitor.YouVisitor;
//import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
//import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
//import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
//import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
//import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
//import oogasalad.model.gameplay.strategies.attributes.Controllable;
//import oogasalad.model.gameplay.strategies.attributes.Pushable;
//import oogasalad.model.gameplay.strategies.attributes.Stoppable;
//import oogasalad.model.gameplay.strategies.attributes.Winnable;
//import oogasalad.model.gameplay.strategies.BecomesBaba;
//import oogasalad.model.gameplay.strategies.BecomesEmpty;
//import oogasalad.model.gameplay.strategies.BecomesFlag;
//import oogasalad.model.gameplay.strategies.BecomesRock;
//import oogasalad.model.gameplay.strategies.BecomesWall;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class BlockVisitorTest {
//
//  private BabaVisitor babaVisitor;
//  private EmptyVisitor emptyVisitor;
//  private FlagVisitor flagVisitor;
//  private RockVisitor rockVisitor;
//  private WallVisitor wallVisitor;
//  private PushVisitor pushVisitor;
//  private StopVisitor stopVisitor;
//  private WinVisitor winVisitor;
//  private YouVisitor youVisitor;
//
//  @BeforeEach
//  void setUp() {
//    babaVisitor = new BabaVisitor();
//    emptyVisitor = new EmptyVisitor();
//    flagVisitor = new FlagVisitor();
//    rockVisitor = new RockVisitor();
//    wallVisitor = new WallVisitor();
//    pushVisitor = new PushVisitor();
//    stopVisitor = new StopVisitor();
//    winVisitor = new WinVisitor();
//    youVisitor = new YouVisitor();
//  }
//
//  @Test
//  void testBabaVisitor() {
//    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
//    babaVisitor.visit(babaBlock);
//    assertTrue(babaBlock.hasBehavior(BecomesBaba.class),
//        "Baba block should have BecomesBaba behavior after visiting.");
//
//    WallVisualBlock wallBlock = new WallVisualBlock("Wall", 0, 0);
//    babaVisitor.visit(wallBlock);
//    assertTrue(wallBlock.hasBehavior(BecomesBaba.class),
//        "Wall block should have BecomesBaba behavior after visiting.");
//
//    RockVisualBlock rockBlock = new RockVisualBlock("Rock", 0, 0);
//    babaVisitor.visit(rockBlock);
//    assertTrue(rockBlock.hasBehavior(BecomesBaba.class),
//        "Rock block should have BecomesBaba behavior after visiting.");
//
//    FlagVisualBlock flagBlock = new FlagVisualBlock("Flag", 0, 0);
//    babaVisitor.visit(flagBlock);
//    assertTrue(flagBlock.hasBehavior(BecomesBaba.class),
//        "Flag block should have BecomesBaba behavior after visiting.");
//
//    EmptyVisualBlock emptyBlock = new EmptyVisualBlock("Empty", 0, 0);
//    babaVisitor.visit(emptyBlock);
//    assertTrue(emptyBlock.hasBehavior(BecomesBaba.class),
//        "Empty block should have BecomesBaba behavior after visiting.");
//  }
//
//  @Test
//  void testEmptyVisitor() {
//    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
//    emptyVisitor.visit(babaBlock);
//    assertTrue(babaBlock.hasBehavior(BecomesEmpty.class),
//        "Baba block should have BecomesEmpty behavior after visiting.");
//
//    WallVisualBlock wallBlock = new WallVisualBlock("Wall", 0, 0);
//    emptyVisitor.visit(wallBlock);
//    assertTrue(wallBlock.hasBehavior(BecomesEmpty.class),
//        "Wall block should have BecomesEmpty behavior after visiting.");
//
//    RockVisualBlock rockBlock = new RockVisualBlock("Rock", 0, 0);
//    emptyVisitor.visit(rockBlock);
//    assertTrue(rockBlock.hasBehavior(BecomesEmpty.class),
//        "Rock block should have BecomesEmpty behavior after visiting.");
//
//    FlagVisualBlock flagBlock = new FlagVisualBlock("Flag", 0, 0);
//    emptyVisitor.visit(flagBlock);
//    assertTrue(flagBlock.hasBehavior(BecomesEmpty.class),
//        "Flag block should have BecomesEmpty behavior after visiting.");
//
//    EmptyVisualBlock emptyBlock = new EmptyVisualBlock("Empty", 0, 0);
//    emptyVisitor.visit(emptyBlock);
//    assertTrue(emptyBlock.hasBehavior(BecomesEmpty.class),
//        "Empty block should have BecomesEmpty behavior after visiting.");
//  }
//
//  @Test
//  void testFlagVisitor() {
//    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
//    flagVisitor.visit(babaBlock);
//    assertTrue(babaBlock.hasBehavior(BecomesFlag.class),
//        "Baba block should have BecomesFlag behavior after visiting.");
//
//    WallVisualBlock wallBlock = new WallVisualBlock("Wall", 0, 0);
//    flagVisitor.visit(wallBlock);
//    assertTrue(wallBlock.hasBehavior(BecomesFlag.class),
//        "Wall block should have BecomesFlag behavior after visiting.");
//
//    RockVisualBlock rockBlock = new RockVisualBlock("Rock", 0, 0);
//    flagVisitor.visit(rockBlock);
//    assertTrue(rockBlock.hasBehavior(BecomesFlag.class),
//        "Rock block should have BecomesFlag behavior after visiting.");
//
//    FlagVisualBlock flagBlock = new FlagVisualBlock("Flag", 0, 0);
//    flagVisitor.visit(flagBlock);
//    assertTrue(flagBlock.hasBehavior(BecomesFlag.class),
//        "Flag block should have BecomesFlag behavior after visiting.");
//
//    EmptyVisualBlock emptyBlock = new EmptyVisualBlock("Empty", 0, 0);
//    flagVisitor.visit(emptyBlock);
//    assertTrue(emptyBlock.hasBehavior(BecomesFlag.class),
//        "Empty block should have BecomesFlag behavior after visiting.");
//  }
//
//  @Test
//  void testPushVisitor() {
//    PushVisitor pushVisitor = new PushVisitor();
//    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
//    pushVisitor.visit(babaBlock);
//    assertTrue(babaBlock.hasBehavior(Pushable.class),
//        "Baba block should have Pushable behavior after visiting.");
//
//    WallVisualBlock wallBlock = new WallVisualBlock("Wall", 0, 0);
//    pushVisitor.visit(wallBlock);
//    assertTrue(wallBlock.hasBehavior(Pushable.class),
//        "Wall block should have Pushable behavior after visiting.");
//
//    RockVisualBlock rockBlock = new RockVisualBlock("Rock", 0, 0);
//    pushVisitor.visit(rockBlock);
//    assertTrue(rockBlock.hasBehavior(Pushable.class),
//        "Rock block should have Pushable behavior after visiting.");
//
//    FlagVisualBlock flagBlock = new FlagVisualBlock("Flag", 0, 0);
//    pushVisitor.visit(flagBlock);
//    assertTrue(flagBlock.hasBehavior(Pushable.class),
//        "Flag block should have Pushable behavior after visiting.");
//
//    EmptyVisualBlock emptyBlock = new EmptyVisualBlock("Empty", 0, 0);
//    pushVisitor.visit(emptyBlock);
//    assertTrue(emptyBlock.hasBehavior(Pushable.class),
//        "Empty block should have Pushable behavior after visiting.");
//  }
//
//  @Test
//  void testRockVisitor() {
//    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
//    rockVisitor.visit(babaBlock);
//    assertTrue(babaBlock.hasBehavior(BecomesRock.class),
//        "Baba block should have BecomesRock behavior after visiting.");
//
//    WallVisualBlock wallBlock = new WallVisualBlock("Wall", 0, 0);
//    rockVisitor.visit(wallBlock);
//    assertTrue(wallBlock.hasBehavior(BecomesRock.class),
//        "Wall block should have BecomesRock behavior after visiting.");
//
//    RockVisualBlock rockBlock = new RockVisualBlock("Rock", 0, 0);
//    rockVisitor.visit(rockBlock);
//    assertTrue(rockBlock.hasBehavior(BecomesRock.class),
//        "Rock block should have BecomesRock behavior after visiting.");
//
//    FlagVisualBlock flagBlock = new FlagVisualBlock("Flag", 0, 0);
//    rockVisitor.visit(flagBlock);
//    assertTrue(flagBlock.hasBehavior(BecomesRock.class),
//        "Flag block should have BecomesRock behavior after visiting.");
//
//    EmptyVisualBlock emptyBlock = new EmptyVisualBlock("Empty", 0, 0);
//    rockVisitor.visit(emptyBlock);
//    assertTrue(emptyBlock.hasBehavior(BecomesRock.class),
//        "Empty block should have BecomesRock behavior after visiting.");
//  }
//
//  @Test
//  void testWallVisitor() {
//    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
//    wallVisitor.visit(babaBlock);
//    assertTrue(babaBlock.hasBehavior(BecomesWall.class),
//        "Baba block should have BecomesWall behavior after visiting.");
//
//    WallVisualBlock wallBlock = new WallVisualBlock("Wall", 0, 0);
//    wallVisitor.visit(wallBlock);
//    assertTrue(wallBlock.hasBehavior(BecomesWall.class),
//        "Wall block should have BecomesWall behavior after visiting.");
//
//    RockVisualBlock rockBlock = new RockVisualBlock("Rock", 0, 0);
//    wallVisitor.visit(rockBlock);
//    assertTrue(rockBlock.hasBehavior(BecomesWall.class),
//        "Rock block should have BecomesWall behavior after visiting.");
//
//    FlagVisualBlock flagBlock = new FlagVisualBlock("Flag", 0, 0);
//    wallVisitor.visit(flagBlock);
//    assertTrue(flagBlock.hasBehavior(BecomesWall.class),
//        "Flag block should have BecomesWall behavior after visiting.");
//
//    EmptyVisualBlock emptyBlock = new EmptyVisualBlock("Empty", 0, 0);
//    wallVisitor.visit(emptyBlock);
//    assertTrue(emptyBlock.hasBehavior(BecomesWall.class),
//        "Empty block should have BecomesWall behavior after visiting.");
//  }
//
//  @Test
//  void testStopVisitor() {
//    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
//    stopVisitor.visit(babaBlock);
//    assertTrue(babaBlock.hasBehavior(Stoppable.class),
//        "Baba block should have BecomesStop behavior after visiting.");
//
//    WallVisualBlock wallBlock = new WallVisualBlock("Wall", 0, 0);
//    stopVisitor.visit(wallBlock);
//    assertTrue(wallBlock.hasBehavior(Stoppable.class),
//        "Wall block should have BecomesStop behavior after visiting.");
//
//    RockVisualBlock rockBlock = new RockVisualBlock("Rock", 0, 0);
//    stopVisitor.visit(rockBlock);
//    assertTrue(rockBlock.hasBehavior(Stoppable.class),
//        "Rock block should have BecomesStop behavior after visiting.");
//
//    FlagVisualBlock flagBlock = new FlagVisualBlock("Flag", 0, 0);
//    stopVisitor.visit(flagBlock);
//    assertTrue(flagBlock.hasBehavior(Stoppable.class),
//        "Flag block should have BecomesStop behavior after visiting.");
//
//    EmptyVisualBlock emptyBlock = new EmptyVisualBlock("Empty", 0, 0);
//    stopVisitor.visit(emptyBlock);
//    assertTrue(emptyBlock.hasBehavior(Stoppable.class),
//        "Empty block should have BecomesStop behavior after visiting.");
//  }
//
//  @Test
//  void testWinVisitor() {
//    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
//    winVisitor.visit(babaBlock);
//    assertTrue(babaBlock.hasBehavior(Winnable.class),
//        "Baba block should have BecomesWin behavior after visiting.");
//
//    WallVisualBlock wallBlock = new WallVisualBlock("Wall", 0, 0);
//    winVisitor.visit(wallBlock);
//    assertTrue(wallBlock.hasBehavior(Winnable.class),
//        "Wall block should have BecomesWin behavior after visiting.");
//
//    RockVisualBlock rockBlock = new RockVisualBlock("Rock", 0, 0);
//    winVisitor.visit(rockBlock);
//    assertTrue(rockBlock.hasBehavior(Winnable.class),
//        "Rock block should have BecomesWin behavior after visiting.");
//
//    FlagVisualBlock flagBlock = new FlagVisualBlock("Flag", 0, 0);
//    winVisitor.visit(flagBlock);
//    assertTrue(flagBlock.hasBehavior(Winnable.class),
//        "Flag block should have BecomesWin behavior after visiting.");
//
//    EmptyVisualBlock emptyBlock = new EmptyVisualBlock("Empty", 0, 0);
//    winVisitor.visit(emptyBlock);
//    assertTrue(emptyBlock.hasBehavior(Winnable.class),
//        "Empty block should have BecomesWin behavior after visiting.");
//  }
//
//  @Test
//  void testYouVisitor() {
//    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
//    youVisitor.visit(babaBlock);
//    assertTrue(babaBlock.hasBehavior(Controllable.class),
//        "Baba block should have BecomesYou behavior after visiting.");
//
//    WallVisualBlock wallBlock = new WallVisualBlock("Wall", 0, 0);
//    youVisitor.visit(wallBlock);
//    assertTrue(wallBlock.hasBehavior(Controllable.class),
//        "Wall block should have BecomesYou behavior after visiting.");
//
//    RockVisualBlock rockBlock = new RockVisualBlock("Rock", 0, 0);
//    youVisitor.visit(rockBlock);
//    assertTrue(rockBlock.hasBehavior(Controllable.class),
//        "Rock block should have BecomesYou behavior after visiting.");
//
//    FlagVisualBlock flagBlock = new FlagVisualBlock("Flag", 0, 0);
//    youVisitor.visit(flagBlock);
//    assertTrue(flagBlock.hasBehavior(Controllable.class),
//        "Flag block should have BecomesYou behavior after visiting.");
//
//    EmptyVisualBlock emptyBlock = new EmptyVisualBlock("Empty", 0, 0);
//    youVisitor.visit(emptyBlock);
//    assertTrue(emptyBlock.hasBehavior(Controllable.class),
//        "Empty block should have BecomesYou behavior after visiting.");
//  }
//
//}
