package oogasalad.model.gameplay;

import oogasalad.controller.gameplay.GameStateController;
import oogasalad.model.gameplay.blocks.blockvisitor.AttributeVisitor;
import oogasalad.model.gameplay.blocks.visualblocks.*;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.handlers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

public class KeyHandlerTest {

  private KeyHandler keyHandler;
  private Grid grid;
  private GameStateController gameStateControllerMock;
  private final static int ROWS = 5;

  private final static int COLS = 5;
  AttributeVisitor wallVisitor;
  AttributeVisitor youVisitor;

  AttributeVisitor hotVisitor;

  AttributeVisitor meltVisitor;

  AttributeVisitor sinkVisitor;
  AttributeVisitor drownVisitor;

  AttributeVisitor killVisitor;

  AttributeVisitor winVisitor;
  private BabaVisualBlock babaBlock;
  private WallVisualBlock wallBlock;




  private final String[][][] initialConfiguration = {
      {{"EmptyVisualBlock"}, {"WallVisualBlock"}, {"WallVisualBlock"}, {"WallVisualBlock"},
          {"EmptyVisualBlock"}},
      {{"EmptyVisualBlock"}, {"WallVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}},
      {{"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}},
      {{"WallVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"WallVisualBlock"},
          {"EmptyVisualBlock"}},
      {{"EmptyVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"WallVisualBlock"},
          {"EmptyVisualBlock"}}
  };


  /**
   * Set up method executed before each test case. Initializes the necessary objects and
   * dependencies for testing, including: - Instantiation of a YouVisitor object. - Creation of a
   * grid with specified rows, columns, and initial configuration. - Mocking of a
   * GameStateController object. - Creation and visiting of visual blocks (Baba and Wall) for
   * testing movement and interaction.
   */
  @BeforeEach
  public void setUp() {
    youVisitor = new AttributeVisitor("You");
    wallVisitor = new AttributeVisitor("Stop");
    hotVisitor = new AttributeVisitor("Hot");
    meltVisitor = new AttributeVisitor("Melt");
    sinkVisitor = new AttributeVisitor("Sink");
    drownVisitor = new AttributeVisitor("Drown");
    winVisitor = new AttributeVisitor("Win");
    killVisitor = new AttributeVisitor("Kill");
    grid = new Grid(ROWS, COLS, initialConfiguration);
    gameStateControllerMock = Mockito.mock(GameStateController.class);
    babaBlock = new BabaVisualBlock("Baba", 1, 1);
    //youVisitor.visit(babaBlock);
    babaBlock.accept(youVisitor);
    wallBlock = new WallVisualBlock("Wall", 0, 0);
    wallBlock.accept(wallVisitor);

  }


  /**
   * Test method to verify the behavior of moving a block to the right using the RightKeyHandler. It
   * checks if the size of the cell after the execution is as expected.
   */
  @Test
  public void testRightKeyHandler() {
    grid.getGrid()[0][0].add(babaBlock);
    keyHandler = new RightKeyHandler(grid, gameStateControllerMock);
    keyHandler.execute();
    int size = grid.cellSize(0, 1);
    assertEquals(2, size);

  }


  /**
   * Test method to verify the behavior of moving a block to the left using the LeftKeyHandler. It
   * checks if the size of the cell after the execution is as expected.
   */
  @Test
  public void testLeftKeyHandler() {
    grid.getGrid()[0][1].add(babaBlock);
    keyHandler = new LeftKeyHandler(grid, gameStateControllerMock);
    keyHandler.execute();
    assertEquals(2, grid.cellSize(0, 0));
  }


  /**
   * Test method to verify the behavior of moving a block up using the UpKeyHandler. It checks if
   * the size of the cell after the execution is as expected.
   */
  @Test
  public void testUpKeyHandler() {
    grid.getGrid()[1][0].add(babaBlock);
    keyHandler = new UpKeyHandler(grid, gameStateControllerMock);
    keyHandler.execute();
    assertEquals(2, grid.cellSize(0, 0));
  }


  /**
   * Test method to verify the behavior of moving a block down using the DownKeyHandler. It checks
   * if the size of the cell after the execution is as expected.
   */
  @Test
  public void DownKeyHandler() {
    grid.getGrid()[0][0].add(babaBlock);
    keyHandler = new DownKeyHandler(grid, gameStateControllerMock);
    keyHandler.execute();
    assertEquals(2, grid.cellSize(1, 0));
  }


  /**
   * Test method to verify that a block cannot move to a cell with a wall using LeftKeyHandler. It
   * checks if the wall is still present in the same position and if the cell size remains
   * unchanged.
   */
  @Test
  public void testNotMovableToMargin() {
    grid.getGrid()[1][1].add(wallBlock);
    keyHandler = new LeftKeyHandler(grid, gameStateControllerMock);
    keyHandler.execute();
    assertEquals("Wall", grid.getBlock(1, 1, 1).getBlockName());
    assertEquals(2, grid.cellSize(1, 1));
  }


  /**
   * Test method to verify that a block can move to a cell with no obstacles using LeftKeyHandler.
   * It checks if the size of the destination cell after the execution is as expected.
   */
  @Test
  public void testMovableToMargin() {
    grid.getGrid()[1][1].add(babaBlock);
    keyHandler = new LeftKeyHandler(grid, gameStateControllerMock);
    keyHandler.execute();
    assertEquals(2, grid.cellSize(1, 0));
  }


  /**
   * Test method to verify the phasing behavior of a block to a cell with another block. It checks
   * if the size of the destination cell after the execution is as expected.
   */
  @Test
  public void testPhasing() {
    grid.getGrid()[1][1].add(babaBlock);
    keyHandler = new RightKeyHandler(grid, gameStateControllerMock);
    keyHandler.execute();
    assertEquals(2, grid.cellSize(1, 2));
  }


  /**
   * Test method to verify the behavior of having multiple instances of the same block (Baba). It
   * checks if the size of the destination cells after the execution is as expected for both
   * instances.
   */
  @Test
  public void testMultipleBaba() {
    grid.getGrid()[1][1].add(babaBlock);
    BabaVisualBlock babaBlock2 = new BabaVisualBlock("Baba2", 1, 1);
    babaBlock2.accept(youVisitor);
    grid.getGrid()[2][1].add(babaBlock2);
    keyHandler = new RightKeyHandler(grid, gameStateControllerMock);
    keyHandler.execute();
    assertEquals(2, grid.cellSize(1, 2));
    assertEquals(2, grid.cellSize(2, 2));
  }

  /**
   * Test method to verify the behavior of melting for a BabaBlock. The test ensures that when a
   * BabaBlock is subjected to a meltVisitor, it melts as expected and occupies the cell where a
   * lava block is present. The cell sizes before and after the execution of the RightKeyHandler are
   * compared.
   */
  @Test
  public void testMeltable() {
    babaBlock.accept(meltVisitor);
    grid.getGrid()[1][1].add(babaBlock);
    LavaVisualBlock lavaBlock = new LavaVisualBlock("Lava", 1, 2);
    lavaBlock.accept(hotVisitor);
    grid.getGrid()[1][2].add(lavaBlock);
    int nextCellSize = grid.cellSize(1, 2);
    int presentCellSize = grid.cellSize(1, 1);

    keyHandler = new RightKeyHandler(grid, gameStateControllerMock);
    keyHandler.execute();

    assertEquals(presentCellSize - 1, grid.cellSize(1, 1));
    assertEquals(nextCellSize, grid.cellSize(1, 2));
  }

  /**
   * Test method to verify the behavior of sinking for a BabaBlock. The test ensures that when a
   * BabaBlock is subjected to a drownVisitor, it sinks as expected and occupies the cell where a
   * WaterVisualBlock is present. The cell sizes before and after the execution of the
   * RightKeyHandler are compared.
   */
  @Test
  public void testSinkable() {
    babaBlock.accept(drownVisitor);
    grid.getGrid()[1][1].add(babaBlock);
    WaterVisualBlock waterBlock = new WaterVisualBlock("Water", 1, 2);
    waterBlock.accept(sinkVisitor);
    grid.getGrid()[1][2].add(waterBlock);
    int nextCellSize = grid.cellSize(1, 2);
    int presentCellSize = grid.cellSize(1, 1);

    keyHandler = new RightKeyHandler(grid, gameStateControllerMock);
    keyHandler.execute();

    assertEquals(presentCellSize - 1, grid.cellSize(1, 1));
    assertEquals(nextCellSize, grid.cellSize(1, 2));
  }

  @Test
  public void testMoveEnemy(){
    BabaVisualBlock babaBlock3 = new BabaVisualBlock("Baba3", 1, 1);
    babaBlock3.accept(killVisitor);
    grid.getGrid()[2][2].add(babaBlock3);
    grid.getGrid()[4][2].add(babaBlock);
    EnemyKeyHandler EKH = new EnemyKeyHandler(grid, gameStateControllerMock);
    EKH.moveEnemy();
    int [] newEnemyPosition = grid.enemyPosition();
    System.out.println("new EnemyPosition is " + Arrays.toString(newEnemyPosition));
    assertEquals(3, newEnemyPosition[0]);
    assertEquals(newEnemyPosition[1], 2);


  }

  @Test
  public void testCrabBug(){
    babaBlock.accept(killVisitor);
    grid.getGrid()[1][1].add(babaBlock);
    CrabVisualBlock crab = new CrabVisualBlock("Crab", 2, 2);
    crab.modifyAttribute("Kill", true);
    grid.getGrid()[1][2].add(crab);
    EnemyKeyHandler enemy = new EnemyKeyHandler(grid, gameStateControllerMock);
    enemy.moveEnemy();
    assertEquals(grid.getGrid()[1][1].size(), 1);
  }



}