package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.strategies.Stoppable;

public class StopVisitor implements BlockVisitor {

  @Override
  public void visit(BabaVisualBlock baba) {
    baba.addBehavior(new Stoppable());
  }

  @Override
  public void visit(WallVisualBlock wall) {
    wall.addBehavior(new Stoppable());
  }

  @Override
  public void visit(FlagVisualBlock flag) {
    flag.addBehavior(new Stoppable());
  }

  @Override
  public void visit(RockVisualBlock rock) {
    rock.addBehavior(new Stoppable());
  }

}
