package oogasalad.model.gameplay.grid;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.interpreter.RuleInterpreter;
import oogasalad.model.gameplay.strategies.*;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.utils.exceptions.VisitorReflectionException;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

public class Grid implements Observable<Grid> {
  private List<Observer<Grid>> observers = new ArrayList<>();
  private List<AbstractBlock>[][] grid;

  private RuleInterpreter parser;
  private BlockFactory factory;
  private BlockUpdater blockUpdater;

  public Grid(int rows, int cols) throws InvalidBlockName {
    this.grid = new ArrayList[rows][cols];
    this.parser = new RuleInterpreter();
    this.factory = new BlockFactory();
    this.blockUpdater = new BlockUpdater(this, factory);
    InitializeGrid();
  }

  // Call everytime there's a handle key press
  public void checkForRules() throws VisitorReflectionException {
    parser.interpretRules(grid);
  }
  public void moveBlock(int fromI, int fromJ, int fromK, int ToI, int ToJ){
    grid[ToI][ToJ].add(grid[fromI][fromJ].get(fromK));
    System.out.println("is the issue in Grid.moveBlock? ");
    grid[fromI][fromJ].remove(fromK);
    System.out.println("ran .remove ");
    if(grid[fromI][fromJ].isEmpty()) {
      System.out.println("about to run setBlock ");
      addBlock(fromI, fromJ, "EmptyVisualBlock");
    }
    System.out.println("NOPE");
  }
  private void addBlock(int i, int j, String BlockType){
    grid[i][j].add (factory.createBlock(BlockType));
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

  public int gridWidth(){
    return grid.length;
  }

  public int gridHeight(){
    return grid[0].length;
  }

  public int cellSize(int i, int j){
    return grid[i][j].size();
  }



  public List<int[]> findControllableBlock() { //record class
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

  public boolean isMovableToMargin(int endI, int endJ, int endK, int controllableintialI, int controllableintialJ, int controllableinitialK){
    //grid.isMovableToMargin(endI, endJ, k, i, j, k)
    boolean already_in_margin = isAlreadyInMargin(controllableintialI, controllableintialJ);
    if(already_in_margin){
      return true;
    }
    if((endI == grid.length -1 || endI == 0)){
      int indexI;
      indexI = (endI == 0) ? endI + 1 : endI - 1;

      return grid[indexI][endJ].get(controllableinitialK).hasBehavior(Controllable.class);
    }
    else if ((endJ == grid[0].length -1 || endJ == 0)){
      int indexJ;
      indexJ = (endJ == 0) ? endJ + 1 : endJ -1;
      return grid[endI][indexJ].get(controllableinitialK).hasBehavior(Controllable.class);
    }
    else{
      return true;
    }
  }

  private boolean isAlreadyInMargin(int i, int j){
    return ((i == grid.length -1 || i == 0) || (j == grid[0].length -1 || j == 0));
  }

  public void checkBehaviors(){
    for(int i = 0; i< grid.length; i++){
      for(int j = 0; j < grid[i].length; j++){
        for(int k = 0; k< grid[i][j].size(); k++){
          AbstractBlock block = grid[i][j].get(k);
          block.executeBehaviors(this, blockUpdater, i, j, k);
        }
      }
    }
  }

  public boolean cellHasStoppable(int i, int j){
    for (AbstractBlock block : grid[i][j]) {
      if (block.hasBehavior(Stoppable.class)) {
        return true;
      }
    }
    return false;
  }

  public boolean cellHasPushable(int i , int j){
    boolean hasPushable = false;
    boolean textBlock = false;
    for(AbstractBlock block : grid[i][j]){
      if(block.getBlockName().endsWith("TextBlock") || (block.getBlockName().endsWith("VisualBlock") && !block.hasBehavior(Stoppable.class) && !block.getBlockName().equals("EmptyVisualBlock"))){
        textBlock = true;
      }
      if (block.hasBehavior(Pushable.class)){
        hasPushable = true;
      }
    }
    //System.out.printf("hasPushable returned " + hasPushable + " and textBlock returned " + textBlock);
    return hasPushable || textBlock;
  }

  public boolean cellHasWinning(int i, int j){
    return grid[i][j].stream().anyMatch(block -> block.hasBehavior(Winnable.class));
  }

  public List<Integer> allPushableBlocksIndex(int i, int j) { //Cant use stream and ForEach because we want to ensure order of element in arraylist are kept same way in indiceslist
    List<Integer> indicesList = new ArrayList<>();
    for (int index = 0; index < grid[i][j].size(); index++) {
      AbstractBlock block = grid[i][j].get(index);
      if (block.getBlockName().endsWith("TextBlock") || block.hasBehavior(Pushable.class) || (block.getBlockName().endsWith("VisualBlock") && !block.hasBehavior(Stoppable.class) && !block.getBlockName().equals("EmptyVisualBlock"))) {
        indicesList.add(index);
      }
    }


    return indicesList;
  }

  public boolean isNotOutOfBounds(int i, int j){
    return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length;
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
        {"EmptyVisualBlock", "BabaVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
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
