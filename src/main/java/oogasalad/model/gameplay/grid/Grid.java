package oogasalad.model.gameplay.grid;
import oogasalad.model.gameplay.strategies.Controllable;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.utils.exceptions.VisitorReflectionException;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.handlers.KeyHandler;
import oogasalad.model.gameplay.interpreter.RuleInterpreter;

import java.util.ArrayList;
import java.util.List;

public class Grid {
  private List<AbstractBlock>[][] grid;
  private RuleInterpreter parser;
  private KeyHandler keyHandler;
  private BlockFactory factory;

  public Grid(int rows, int cols) throws InvalidBlockName {
    this.grid = new ArrayList[rows][cols];
    this.parser = new RuleInterpreter();
    this.factory = new BlockFactory();
    InitializeGrid();
  }

  // Call everytime there's a handle key press
  public void checkForRules() throws VisitorReflectionException {
    parser.interpretRules(grid);
  }
  public void moveBlock(int fromI, int fromJ, int fromK, int ToI, int ToJ, int ToK){
    grid[ToI][ToJ].set(ToK, grid[fromI][fromJ].get(fromK));
  }
  public void setBlock(int i, int j, int k, String BlockType){
    grid[i][j].set(k, factory.createBlock(BlockType));
  }
  public AbstractBlock getBlock(int i, int j, int k){
    return grid[i][j].get(k);
  }
  public List<AbstractBlock>[][] getGrid() {
    return this.grid;
  }

  public List<int[]> findControllableBlock() {
    List<int[]> AllControllableBlocks = new ArrayList<>();
    for(int i = 0; i < grid.length; i++){
      for(int j = 0; j < grid[i].length; j++){
        for(int k= 0; k < grid[i][j].size(); k++){
          AbstractBlock block = grid[i][j].get(k);
          if(block != null && block.hasBehavior(Controllable.class)){
            int [] a = {i, j, k};
            AllControllableBlocks.add(a);
          }
        }
      }
    }
    return AllControllableBlocks;
  }

  private void InitializeGrid(){
    // Initializing elements
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = new ArrayList<AbstractBlock>();
        createBlocks(grid[i][j], tempconfiguration[i][j]);
      }
    }
  }

  private void createBlocks(List<AbstractBlock> AbstractBlocks, String[] Blocktypes){
    for(int i=0; i< Blocktypes.length; i++){
      AbstractBlocks.add(factory.createBlock(Blocktypes[i]));
    }
  }
  String[][][] tempconfiguration = {
          {{"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}},
          {{"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"RockVisualBlock"}, {"EmptyVisualBlock"}},
          {{"EmptyVisualBlock"}, {"RockTextBlock"}, {"BabaVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}},
          {{"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}},
          {{"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"RockTextBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}},
          {{"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"BabaTextBlock"}, {"IsTextBlock"}, {"YouTextBlock"}, {"EmptyVisualBlock"}},
          {{"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}},
          {{"BabaVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}}
  };


}
