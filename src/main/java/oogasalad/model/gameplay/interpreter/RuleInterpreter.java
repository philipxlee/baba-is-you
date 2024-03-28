package oogasalad.model.gameplay.interpreter;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.*;
import oogasalad.model.gameplay.grid.Grid;

public class RuleInterpreter {

  public void processRule(AbstractBlock first, AbstractBlock second, AbstractBlock third, Grid grid) {
    BlockVisitor visitor = determineVisitor(third.getBlockName());

    // Ensuring a visitor is available
    if (visitor == null) {
      return; // or handle error
    }

    // Loop through the grid to apply the visitor to all matching visual blocks
    AbstractBlock[][] gridArray = grid.getGrid();
    for (AbstractBlock[] row : gridArray) {
      for (AbstractBlock cell : row) {
        if (!cell.isTextBlock() && cell.matches(first.getBlockName())) {
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
