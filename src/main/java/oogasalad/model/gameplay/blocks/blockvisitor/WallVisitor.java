package oogasalad.model.gameplay.blocks.blockvisitor;

import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.FlagVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.strategies.BecomesEmpty;
import oogasalad.model.gameplay.strategies.BecomesWall;

public class WallVisitor implements BlockVisitor {

  @Override
  public void visit(BabaVisualBlock baba) {
    baba.addBehavior(new BecomesWall());
  }

  @Override
  public void visit(WallVisualBlock wall) {
    wall.addBehavior(new BecomesWall());
  }

  @Override
  public void visit(RockVisualBlock rock) {
    rock.addBehavior(new BecomesWall());
  }

  @Override
  public void visit(FlagVisualBlock flag) {
    flag.addBehavior(new BecomesWall());
  }

  @Override
  public void visit(EmptyVisualBlock empty) {
    empty.addBehavior(new BecomesWall());
  }

}
