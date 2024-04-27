package oogasalad.model.gameplay.grid;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.blockvisitor.AttributeVisitor;
import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.exceptions.VisitorReflectionException;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.interpreter.RuleInterpreter;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;


public class Grid extends GridHelper implements Observable<Grid> {

  private final List<Observer<Grid>> observers = new ArrayList<>();
  private final RuleInterpreter parser;
  private final BlockFactory factory;
  private final BlockUpdater blockUpdater;
  private final String[][][] initialConfiguration;

  private final BiMap<String, String> strategyMap;


  /**
   * Constructs a grid with the specified dimensions and initial configuration.
   *
   * @param rows                 The number of rows in the grid.
   * @param cols                 The number of columns in the grid.
   * @param initialConfiguration The initial configuration of the grid.
   * @throws InvalidBlockName if an invalid block name is encountered.
   */
  public Grid(int rows, int cols, String[][][] initialConfiguration) throws InvalidBlockName {
    super(rows, cols);
    this.parser = new RuleInterpreter();
    this.factory = new BlockFactory();
    this.blockUpdater = new BlockUpdater(this, factory);
    this.initialConfiguration = initialConfiguration; // Initialize initial configuration
    this.strategyMap = HashBiMap.create();
    addMappingsToStrategyMap();
    InitializeGrid();
  }

  private void addMappingsToStrategyMap() {
    strategyMap.put("Hotable", "Meltable");
    strategyMap.put("Sinkable", "Drownable");
    // Add more mappings as needed
  }


  /**
   * Initializes the grid with blocks based on the initial configuration.
   */
  private void InitializeGrid() {
    // Initializing elements
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = new ArrayList<AbstractBlock>();
        createBlocks(grid[i][j], initialConfiguration[i][j], i, j);
      }
    }
  }

  /**
   * Retrieves an iterator for the specified cell.
   *
   * @param row The row index of the cell.
   * @param col The column index of the cell.
   * @return A CellIterator for the specified cell.
   */
  private CellIterator iterator(int row, int col) {
    return new CellIterator(grid, row, col);
  }


  /**
   * Retrieves the block at the specified indices.
   *
   * @param i The row index.
   * @param j The column index.
   * @param k The index of the block in the cell.
   * @return The block at the specified indices.
   */
  public AbstractBlock getBlock(int i, int j, int k) {
    return grid[i][j].get(k);
  }

  /**
   * Retrieves the grid.
   *
   * @return The grid.
   */
  public List<AbstractBlock>[][] getGrid() {
    sortCellsForRender();
    return this.grid;
  }

  /**
   * Retrieves the width of the grid.
   *
   * @return The width of the grid.
   */
  public int getGridWidth() {
    return grid.length;
  }

  /**
   * Retrieves the height of the grid.
   *
   * @return The height of the grid.
   */
  public int getGridHeight() {
    return grid[0].length;
  }


  /**
   * Retrieves the size of the cell at the specified indices.
   *
   * @param i The row index of the cell.
   * @param j The column index of the cell.
   * @return The size of the cell at the specified indices.
   */
  public int cellSize(int i, int j) {
    return grid[i][j].size();
  }


  /**
   * Executes behaviors for all blocks in the grid.
   */
  public void checkBehaviors() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        for (int k = 0; k < grid[i][j].size(); k++) {
          AbstractBlock block = grid[i][j].get(k);
          block.executeBehaviors(block, blockUpdater, iterator(i, j));
        }
      }
    }
  }

  /**
   * Checks if the specified indices are within the bounds of the grid.
   *
   * @param i The row index.
   * @param j The column index.
   * @return True if the indices are within the bounds of the grid, false otherwise.
   */
  public boolean isNotOutOfBounds(int i, int j) {
    return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length;
  }


  /**
   * Checks for rules based on the current state of the grid.
   *
   * @throws VisitorReflectionException if an error occurs during rule interpretation.
   */
  public void checkForRules() throws VisitorReflectionException {
    parser.interpretRules(grid);
  }

  /**
   * Adds an observer to the grid.
   *
   * @param o The observer to add.
   */
  @Override
  public void addObserver(Observer<Grid> o) {
    observers.add(o);
  }

  /**
   * Notifies all observers of changes in the grid.
   */
  @Override
  public void notifyObserver() {
    checkForDisappear();
    for (Observer<Grid> observer : observers) {
      observer.update(this);
    }
  }

  /**
   * Creates blocks based on the provided block types and adds them to the specified cell in the
   * grid.
   *
   * @param AbstractBlocks The list of abstract blocks representing the cell in the grid.
   * @param Blocktypes     The array of block types to create.
   * @param row            The row index of the cell.
   * @param col            The column index of the cell.
   */
  private void createBlocks(List<AbstractBlock> AbstractBlocks, String[] Blocktypes, int row,
      int col) {
    for (int i = 0; i < Blocktypes.length; i++) {
      AbstractBlocks.add(factory.createBlock(Blocktypes[i], row, col));
    }
  }


  /**
   * This method sorts each cell's list so that EmptyVisualBlock instances come first.
   */
  private void sortCellsForRender() {
    for (List<AbstractBlock>[] row : grid) {
      for (List<AbstractBlock> cell : row) {
        cell.sort(new Comparator<AbstractBlock>() {
          @Override
          public int compare(AbstractBlock block1, AbstractBlock block2) {
            if (block1.isEmptyVisualBlock() && !(block2.isEmptyVisualBlock())) {
              return -1;
            } else if (!(block1.isEmptyVisualBlock())
                && block2.isEmptyVisualBlock()) {
              return 1;
            }
            return 0;
          }
        });
      }
    }
  }

  private void checkForDisappear() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        checkCellForDisappear(i, j);
      }
    }
  }

  private void checkCellForDisappear(int cellI, int cellJ) {
    int subjectIndex = -1;
    int objectIndex = -1;
    String subjectStrategyKey = "";
    String objectStrategyValue = "";

    for (int k = 0; k < grid[cellI][cellJ].size(); k++) {
      AbstractBlock block = grid[cellI][cellJ].get(k);
      Optional<Iterator<Entry<String, Boolean>>> optionalIterator = block.getAttributeIterator();

      if (optionalIterator.isPresent()) {
        Iterator<Entry<String, Boolean>> iterator = optionalIterator.get();
        while (iterator.hasNext()) {
          Entry<String, Boolean> entry = iterator.next();
          String attribute = entry.getKey();
          boolean isActive = entry.getValue();

          // Check if a subject is found, if not, check if it's a potential subject
          if (isActive && subjectIndex == -1 && strategyMap.containsKey(attribute)) {
            subjectIndex = k;
            subjectStrategyKey = attribute;
          }

          // Check if an object is found, if not, check if it's a potential object
          if (isActive && objectIndex == -1 && strategyMap.inverse().containsKey(attribute)) {
            objectIndex = k;
            objectStrategyValue = attribute;
          }

          // If both indices are found but not matched yet, check if they complete each other
          if (subjectIndex != -1 && objectIndex != -1 && subjectIndex != objectIndex) {
            if (strategyMap.get(subjectStrategyKey).equals(objectStrategyValue)
                || strategyMap.inverse().get(objectStrategyValue).equals(subjectStrategyKey)) {
              grid[cellI][cellJ].remove(objectIndex);
              return;
            }
          }
        }
      }
    }
  }

  public boolean hasEnemy(){ //
    System.out.println();
    System.out.println();
    System.out.println();System.out.println();
    System.out.println();
    System.out.println();

    boolean hasACrab = false;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        for(AbstractBlock block : grid[i][j]){
          if(!block.isTextBlock() && block.getAttribute("Killable")){
            hasACrab = true;
          }
        }
      }
    }
    System.out.println("Has a crab " + hasACrab);
    return hasACrab;
  }

  public boolean isPassable(int cellI, int cellJ){
    return !cellHasStoppable(cellI, cellJ); //should return opposite of cellHasStoppabe
  }

  public void placeEnemy(int I, int J){
    AbstractBlock crabBlock = factory.createBlock("CrabVisualBlock", I, J);
    ((AbstractVisualBlock)crabBlock).modifyAttribute("Kill", true);
    grid[I][J].add(crabBlock);
    ((AbstractVisualBlock)crabBlock).modifyAttribute("Kill", true);
  }

  public void moveEnemy(int fromI, int fromJ, int fromK, int toI, int toJ){
    sortArray();
    moveBlock(fromI, fromJ, fromK, toI, toJ);
    sortArray();
    List<AbstractBlock> cell = grid[toI][toJ];
    AbstractBlock crabBlock = grid[toI][toJ].get(cell.size()-1);
    if (!crabBlock.isTextBlock()) {
      ((AbstractVisualBlock) crabBlock).modifyAttribute("Kill", true);
    }

  }



  public int [] enemyPosition(){
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        for (int k = 0; k < grid[i][j].size(); k++) {
          AbstractBlock block = grid[i][j].get(k);
          if(block.getAttribute("Killable")){
            int[] enemyPosition = {i, j, k};
            return enemyPosition;
          }
        }
      }
    }
    return null;
  }

  public void setCrabAttribute(){
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        for (int k = 0; k < grid[i][j].size(); k++) {
          AbstractBlock block = grid[i][j].get(k);
          if(!block.isTextBlock() && block.getBlockName().equals("CrabVisualBlock")) {
            ((AbstractVisualBlock)block).modifyAttribute("Kill", true);
          }
        }
      }
    }
  }

}
