package oogasalad.model.authoring.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import oogasalad.model.authoring.block.Block;
import oogasalad.model.authoring.block.BlockFactory;
import oogasalad.shared.observer.Observer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GridTest {

  private final static int ROWS = 3;
  private final static int COLS = 3;
  private Grid grid;

  @Before
  public void setUp() throws Exception {
    // Initialize the grid
    grid = new Grid(ROWS, COLS);
  }

  @Test
  public void testGridInitialization() {
    // Test that all cells are initialized with "Empty" blocks
    for (Block block : grid) {
      assertEquals("EmptyVisualBlock", block.type().name());
    }
  }

  @Test
  public void testAddObserverAndNotify() {
    // Test addObserver and notifyObserver by adding a mock observer and verifying it gets updated
    Observer<Grid> observer = Mockito.mock(Observer.class);
    grid.addObserver(observer);
    grid.notifyObserver();
    Mockito.verify(observer).update(grid);
  }

  @Test
  public void testSetCellWithValidName() throws Exception {
    grid.setCell(0, 0, "BabaVisualBlock");
    Block block = grid.iterator().next();
    assertEquals("BabaVisualBlock", block.type().name());
  }

  @Test
  public void testSetCellWithInvalidPosition() {
    // Test setting a cell with an invalid position (out of bounds)
    Exception exception = assertThrows(Exception.class,
        () -> grid.setCell(ROWS, COLS, "Invalid Row/Col Position"));
    assertNotNull(exception);
  }

  @Test
  public void testIterator() {
    assertNotNull(grid.iterator());
    assertTrue(grid.iterator().hasNext());
    assertNotNull(grid.iterator().next());

    // Move iterator to the end to test hasNext returns false
    Iterator<Block> it = grid.iterator();
    while (it.hasNext()) {
      it.next();
    }
    assertFalse(it.hasNext());
  }
}
