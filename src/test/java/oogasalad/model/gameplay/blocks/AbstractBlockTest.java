package oogasalad.model.gameplay.blocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AbstractBlockTest {

  private AbstractBlock block;

  @BeforeEach
  public void setUp() {
    block = new AbstractBlock() {
    };
  }

  @Test
  public void testIsTextBlock_DefaultBehavior() {
    assertFalse(block.isTextBlock(), "Default isTextBlock should return false.");
  }

  @Test
  public void testMatches_DefaultBehavior() {
    String blockName = block.getBlockName();
    assertTrue(block.matches(blockName), "Block should match its class name by default.");
    assertFalse(block.matches("AnotherName"), "Block should not match different name.");
  }

  @Test
  public void testAccept_DefaultBehavior() {
    block.accept(null); // Test that no exception is thrown
  }

  @Test
  public void testResetAllBehaviors_DefaultBehavior() {
    block.resetAllBehaviors(); // Test that no exception is thrown
  }

  @Test
  public void testExecuteBehaviors_DefaultBehavior() {
    block.executeBehaviors(block, null, null); // Test that no exception is thrown
  }

  @Test
  public void testGetBlockGrammarIterator_DefaultBehavior() {
    Iterator<String> it = block.getBlockGrammarIterator();
    assertFalse(it.hasNext(), "Default grammar iterator should be empty.");
    assertThrows(NoSuchElementException.class, it::next,
        "Calling next on empty iterator should throw.");
  }

  @Test
  public void testGetRow_DefaultBehavior() {
    assertEquals(0, block.getRow(), "Default row should be 0.");
  }

  @Test
  public void testGetCol_DefaultBehavior() {
    assertEquals(0, block.getCol(), "Default column should be 0.");
  }

  @Test
  public void testGetAttribute_DefaultBehavior() {
    assertFalse(block.getAttribute("anyAttribute"),
        "Default getAttribute should return false for any attribute.");
  }

  @Test
  public void testGetAttributeIterator_DefaultBehavior() {
    assertFalse(block.getAttributeIterator().isPresent(),
        "Default getAttributeIterator should return an empty Optional.");
  }
}
