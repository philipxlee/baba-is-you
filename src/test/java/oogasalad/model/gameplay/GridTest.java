package oogasalad.model.gameplay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.model.gameplay.blocks.blockvisitor.AttributeVisitor;
import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.shared.observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GridTest {

  private final static int ROWS = 5;

  private final static int COLS = 5;
  private Grid grid;
  AttributeVisitor pushVisitor;
  AttributeVisitor stopVisitor;
  AttributeVisitor winVisitor;
  AttributeVisitor youVisitor;
  private final String[][][] initialConfiguration = {
      {{"BabaVisualBlock"}, {"BabaVisualBlock"}, {"FlagVisualBlock"}, {"WallVisualBlock"},
          {"EmptyVisualBlock"}},
      {{"EmptyVisualBlock"}, {"BabaVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"},
          {"FlagVisualBlock"}},
      {{"FlagVisualBlock"}, {"EmptyVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"},
          {"BabaVisualBlock"}},
      {{"WallVisualBlock"}, {"FlagVisualBlock"}, {"EmptyVisualBlock"}, {"BabaVisualBlock"},
          {"EmptyVisualBlock"}},
      {{"EmptyVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"FlagVisualBlock"},
          {"BabaVisualBlock"}}
  };


  /**
   * Initialize test data and objects.
   */
  @BeforeEach
  void setUp() {
    youVisitor = new AttributeVisitor("You");
    pushVisitor = new AttributeVisitor("Push");
    winVisitor = new AttributeVisitor("Win");
    stopVisitor = new AttributeVisitor("Stop");
    grid = new Grid(ROWS, COLS, initialConfiguration);
  }


  /**
   * Test the initialization of the grid.
   */
  @Test
  public void testGridInitialization() {
    // Test that all cells are initialized with an empty block
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        assertEquals(1, grid.cellSize(i, j));
      }
    }
  }


  /**
   * Test adding an observer and notifying it.
   */
  @Test
  public void testAddObserverAndNotify() {
    // Test addObserver and notifyObserver by adding a mock observer and verifying it gets updated
    Observer<Grid> observer = Mockito.mock(Observer.class);
    grid.addObserver(observer);
    grid.notifyObserver();
    Mockito.verify(observer).update(grid);
  }


  /**
   * Test moving a block within the grid.
   */
  @Test
  public void testMoveBlock() {
    // Test moving a block within the grid and indirectly verify addBlock functionality
    grid.moveBlock(0, 0, 0, 0, 1); //move to the next column, same row
    assertEquals("EmptyVisualBlock",
        grid.getBlock(0, 0, 0).getBlockName()); // Original cell should be empty
    assertEquals("BabaVisualBlock",
        grid.getBlock(0, 1, 1).getBlockName()); // Block should be moved to new cell
    assertEquals(2, grid.cellSize(0, 1));
    assertEquals(1, grid.cellSize(0, 0)); // Original cell should now be 1
  }


  /**
   * Test the isNotOutOfBounds method when within bounds.
   */
  @Test
  public void testIsNotOutOfBoundsWithinBounds() {
    assertTrue(grid.isNotOutOfBounds(1, 1)); // Inside the grid
    assertTrue(grid.isNotOutOfBounds(0, 0)); // On the edge of the grid
    assertTrue(grid.isNotOutOfBounds(ROWS - 1, COLS - 1)); // On the opposite edge of the grid
    assertFalse(grid.isNotOutOfBounds(ROWS, COLS));
  }


  /**
   * Test the isNotOutOfBounds method on the boundary.
   */
  @Test
  public void testIsNotOutOfBoundsOnBoundary() {
    assertTrue(grid.isNotOutOfBounds(0, 4)); // On the boundary
    assertTrue(grid.isNotOutOfBounds(4, 0)); // On the boundary
    assertFalse(grid.isNotOutOfBounds(5, 5)); // Out of bounds
  }


  /**
   * Test the isNotOutOfBounds method when out of bounds.
   */
  @Test
  public void testIsNotOutOfBoundsOutOfBounds() {
    assertFalse(grid.isNotOutOfBounds(-1, 1)); // Out of bounds
    assertFalse(grid.isNotOutOfBounds(6, 2)); // Out of bounds
  }


  /**
   * Test the gridWidth method.
   */
  @Test
  public void testGridWidth() {
    assertEquals(5, grid.getGridWidth());
  }


  /**
   * Test the gridHeight method.
   */
  @Test
  public void testGridHeight() {
    assertEquals(5, grid.getGridHeight());
  }


  /**
   * Test the cellSize method.
   */
  @Test
  public void testCellSize() {
    // Check the size of a specific cell
    assertEquals(1, grid.cellSize(0, 0));
    assertEquals(1, grid.cellSize(1, 1));
    assertEquals(1, grid.cellSize(2, 2));
    assertEquals(1, grid.cellSize(3, 3));
    assertEquals(1, grid.cellSize(4, 4));

    // Add more blocks to a specific cell and check its size again
    grid.getGrid()[0][0].add(
        new BabaVisualBlock("Baba", 0, 0)); // Adding a new block to cell (0, 0)
    assertEquals(2, grid.cellSize(0, 0));
  }


  /**
   * Test the cellHasControllable method.
   */
  @Test
  public void testCellHasControllable() {
    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
    babaBlock.accept(youVisitor);
    grid.getGrid()[0][0].add(babaBlock); // Adding a new block to cell (0, 0)
    assertTrue(grid.cellHasControllable(0, 0));
  }


  /**
   * Test the cellHasWinning method.
   */
  @Test
  public void testCellHasWinning() {
    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
    babaBlock.accept(winVisitor);
    grid.getGrid()[0][0].add(babaBlock);
    assertTrue(grid.cellHasWinning(0, 0));
  }


  /**
   * Test the cellHasStoppable method.
   */
  @Test
  public void testCellHasStoppable() {
    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
    babaBlock.accept(stopVisitor);
    grid.getGrid()[0][0].add(babaBlock);
    assertTrue(grid.cellHasStoppable(0, 0));
  }


  /**
   * Test the cellHasPushable method.
   */
  @Test
  public void testCellHasPushable() {
    BabaVisualBlock babaBlock = new BabaVisualBlock("Baba", 0, 0);
    babaBlock.accept(pushVisitor);
    grid.getGrid()[0][0].add(babaBlock);
    assertTrue(grid.cellHasPushable(0, 0));
  }
}