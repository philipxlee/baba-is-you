package oogasalad.model.gameplay.grid;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.handlers.KeyHandler;
import oogasalad.model.gameplay.interpreter.RuleInterpreter;
import oogasalad.model.gameplay.strategies.BecomesEmpty;
import oogasalad.model.gameplay.strategies.BecomesWall;
import oogasalad.model.gameplay.strategies.Controllable;
import oogasalad.model.gameplay.strategies.Winnable;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.utils.exceptions.VisitorReflectionException;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

public class Grid implements Observable<Grid> {
  private List<Observer<Grid>> observers = new ArrayList<>();
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

  @Override
  public void addObserver(Observer<Grid> o) {
    observers.add(o);
  }

  @Override
  public void notifyObserver() {
    for (Observer<Grid> observer : observers) {
      observer.update(this);
    }
  }

  public void renderChanges() {
    notifyObserver();
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

  public void checkBehaviors(){
    for(int i = 0; i< grid.length; i++){
      for(int j = 0; j < grid[i].length; j++){
        for(int k = 0; k< grid[i][j].size(); k++){
          AbstractBlock block = grid[i][j].get(k);

          if (block != null && block.hasBehavior(BecomesWall.class)) {
            changeBlockToWall(i, j, k);
          }
          // temporary, needs refactoring
          if (block != null && block.hasBehavior(BecomesEmpty.class)) {
            changeBlockToEmpty(i, j, k);
          }
        }
      }
    }
  }
  private void changeBlockToEmpty(int i, int j, int k) {
    grid[i][j].set(k, factory.createBlock("EmptyVisualBlock"));
  }

  private void changeBlockToWall(int i, int j, int k) {
    grid[i][j].set(k, factory.createBlock("WallVisualBlock"));
  }


  String[][][] tempConfiguration = {
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyTextBlock"}, {"EmptyVisualBlock"}, {"RockVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"IsTextBlock"}, {"WallTextBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"BabaTextBlock"}, {"IsTextBlock"}, {"YouTextBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"WinTextBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"WallVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"WallVisualBlock"},
        {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"StopTextBlock"},
        {"EmptyVisualBlock"}, {"BabaTextBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"WallVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"WallVisualBlock"},
        {"BabaVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"FlagTextBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"WallVisualBlock"},
        {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"FlagVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}
    },
      {
        {"EmptyVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"IsTextBlock"}, {"YouTextBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"RockTextBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"WallVisualBlock"},
        {"WallVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"WallTextBlock"},
        {"IsTextBlock"}, {"StopTextBlock"}, {"EmptyVisualBlock"}
      },
      {
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
        {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      }
  };

  private void createBlocks(List<AbstractBlock> AbstractBlocks, String[] Blocktypes){
    for(int i=0; i< Blocktypes.length; i++){
      AbstractBlocks.add(factory.createBlock(Blocktypes[i]));
    }
  }

  private void InitializeGrid(){
    // Initializing elements
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = new ArrayList<AbstractBlock>();
        createBlocks(grid[i][j], tempConfiguration[i][j]);
      }
    }
  }


}
