package oogasalad.model.gameplay.interpreter;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.*;

public class RuleInterpreter {

  public void interpretRules(AbstractBlock[][] grid) {
    for (AbstractBlock[] abstractBlocks : grid) {
      for (int col = 0; col < abstractBlocks.length - 2; col++) {
        AbstractBlock firstBlock = abstractBlocks[col];
        AbstractBlock secondBlock = abstractBlocks[col + 1];
        AbstractBlock thirdBlock = abstractBlocks[col + 2];
        if (firstBlock != null &&  secondBlock != null && thirdBlock != null &&
            firstBlock.isTextBlock() && secondBlock.isTextBlock() && thirdBlock.isTextBlock()) {
          processRule(firstBlock, secondBlock, thirdBlock, grid);
        }
      }
    }
  }

  private void processRule(AbstractBlock first, AbstractBlock second, AbstractBlock third, AbstractBlock[][] grid) {
    BlockVisitor visitor = determineVisitor(third.getBlockName());
    // Ensuring a visitor is available
    if (visitor == null) {
      return; // or handle error
    }

    // Loop through the grid to apply the visitor to all matching visual blocks
    for (AbstractBlock[] row : grid) {
      for (AbstractBlock cell : row) {
        if (!cell.isTextBlock() && cell.matches(first.getBlockName())) {
          System.out.println("Applying visitor to " + cell.getBlockName());
          cell.accept(visitor);
        }
      }
    }
  }

  private BlockVisitor determineVisitor(String blockName) {
    return switch (blockName) {
      case "YouTextBlock" -> new YouVisitor();
      case "PushTextBlock" -> new PushVisitor();
      case "WinTextBlock" -> new WinVisitor();
      case "StopTextBlock" -> new StopVisitor();
      default -> null; // or handle the default case
    };
  }
}
