package oogasalad.model.gameplay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.CellIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CellIteratorTest {

  private List<AbstractBlock>[][] grid;
  private CellIterator cellIterator;

  @BeforeEach
  void setUp() {
    grid = new List[3][3];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = new ArrayList<>();
      }
    }
    grid[1][1].add(mock(AbstractBlock.class));
    grid[1][1].add(mock(AbstractBlock.class));
    grid[1][1].add(mock(AbstractBlock.class));
    cellIterator = new CellIterator(grid, 1, 1);
  }

  @Test
  void testHasNext_ExpectTrue() {
    assertTrue(cellIterator.hasNext(), "Iterator should have next element");
  }

  @Test
  void testNext_ExpectBlocks() {
    assertNotNull(cellIterator.next(), "Should return the next block");
    assertTrue(cellIterator.hasNext(), "Should still have blocks after one retrieval");
    cellIterator.next(); // Move iterator
    cellIterator.next(); // Move iterator to the end
    assertFalse(cellIterator.hasNext(), "Should not have blocks after all are retrieved");
  }

  @Test
  void testNext_NoSuchElementException() {
    cellIterator.next(); // 1st block
    cellIterator.next(); // 2nd block
    cellIterator.next(); // 3rd block

    assertThrows(NoSuchElementException.class, cellIterator::next,
        "Should throw exception when no more elements");
  }

  @Test
  void testOutOfBounds_ThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new CellIterator(grid, 3, 3),
        "Should throw IllegalArgumentException for out-of-bounds indices");
  }

  @Test
  void testResetIterator() {
    cellIterator.next(); // 1st block
    cellIterator.next(); // 2nd block
    cellIterator.reset();
    assertTrue(cellIterator.hasNext(), "Iterator should be reset to the start");
    assertEquals(grid[1][1].get(0), cellIterator.next(),
        "Should return the first block after reset");
  }
}
