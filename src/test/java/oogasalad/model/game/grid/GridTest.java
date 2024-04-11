package oogasalad.model.game.grid;

import oogasalad.model.gameplay.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.model.authoring.block.BlockFactory;
import oogasalad.shared.observer.Observer;
import org.mockito.Mockito;

public class GridTest {

    private final static int ROWS = 3;
    private final static int COLS = 3;
    private Grid grid;
    private BlockFactory blockFactory;

    @BeforeEach
    public void setUp() throws Exception {

        // Initialize the grid
        String[][][] initialConfiguration = new String[ROWS][COLS][1];
        grid = new Grid(ROWS, COLS, initialConfiguration);
    }

    @Test
    public void testGridInitialization() {
        // Test that all cells are initialized with an empty block
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                assertEquals(1, grid.cellSize(i, j));
            }
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
    public void testMoveBlock() {
        // Test moving a block within the grid and indirectly verify addBlock functionality
        grid.moveBlock(0, 0, 0, 0, 1); //move to the next column, same row
        assertEquals("EmptyVisualBlock", grid.getBlock(0, 0, 0).getBlockName()); // Original cell should be empty
        assertEquals("EmptyVisualBlock", grid.getBlock(0, 1, 1).getBlockName()); // Block should be moved to new cell
        assertEquals(2, grid.cellSize(0, 1));
        assertEquals(1, grid.cellSize(0, 0)); // Original cell should now be 1
    }

    @Test
    public void testIsNotOutOfBoundsWithinBounds() {
        grid = new Grid(ROWS, COLS, new String[ROWS][COLS][0]);
        assertTrue(grid.isNotOutOfBounds(1, 1)); // Inside the grid
        assertTrue(grid.isNotOutOfBounds(0, 0)); // On the edge of the grid
        assertTrue(grid.isNotOutOfBounds(ROWS - 1, COLS - 1)); // On the opposite edge of the grid
    }

    @Test
    public void testIsNotOutOfBoundsOnBoundary() {
        grid = new Grid(ROWS, COLS, new String[ROWS][COLS][0]);
        assertTrue(grid.isNotOutOfBounds(0, 2)); // On the boundary
        assertTrue(grid.isNotOutOfBounds(2, 0)); // On the boundary
        assertFalse(grid.isNotOutOfBounds(3, 1)); // Out of bounds
        assertFalse(grid.isNotOutOfBounds(1, -1)); // Out of bounds
    }

    @Test
    public void testIsNotOutOfBoundsOutOfBounds() {
        grid = new Grid(ROWS, COLS, new String[ROWS][COLS][0]);
        assertFalse(grid.isNotOutOfBounds(-1, 1)); // Out of bounds
        assertFalse(grid.isNotOutOfBounds(1, 3)); // Out of bounds
        assertFalse(grid.isNotOutOfBounds(3, 3)); // Out of bounds
    }



}