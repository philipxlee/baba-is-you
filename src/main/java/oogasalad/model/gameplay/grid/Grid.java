package oogasalad.model.gameplay.grid;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.visualblocks.AbstractVisualBlock;
import oogasalad.model.gameplay.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.exceptions.VisitorReflectionException;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.interpreter.RuleInterpreter;
import oogasalad.shared.loader.PropertiesLoader;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;


public class Grid extends GridHelper implements Observable<Grid> {

  private static final String BIMAP_PROPERTIES = "blockbehaviors/strategies.properties";
  public static final String CRAB_VISUAL_BLOCK = "CrabVisualBlock";
  private final List<Observer<Grid>> observers = new ArrayList<>();
  private final RuleInterpreter parser;
  private final BlockFactory factory;
  private final BlockUpdater blockUpdater;
  private final Properties properties;
  private final String[][][] initialConfiguration;

  private final BiMap<String, String> strategyMap;

  private static final String KILL = "Kill";
  private static final String KILLABLE = "Killable";
  private static final String CONTROLLABLE = "Controllable";

  private static final String WINNABLE = "Winnable";
  private static final String STOPPABLE = "Stoppable";

  private static final String HOTABLE = "Hotable";
  private static final String SINKABLE = "Sinkable";


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
    this.properties = PropertiesLoader.loadProperties(BIMAP_PROPERTIES);
    addMappingsToStrategyMap();
    initializeGrid();
  }

  private void addMappingsToStrategyMap() {
    // Iterate through properties and add them to the strategy map
    for (String key : properties.stringPropertyNames()) {
      String value = properties.getProperty(key);
      if (value != null) {
        strategyMap.put(key, value);
      }
    }
  }


  /**
   * Initializes the grid with blocks based on the initial configuration.
   */
  private void initializeGrid() {
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
  public void checkBehaviors() { //cant use Streams
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
    Arrays.stream(grid)
        .flatMap(Arrays::stream)
        .forEach(this::compareCellForRender);
  }


  /**
   * Compares blocks in a cell for rendering based on their attributes. Blocks with certain
   * attributes are sorted to be rendered first.
   *
   * @param cell The list of blocks in a cell.
   */
  private void compareCellForRender(List<AbstractBlock> cell) {
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

  /**
   * Checks each cell in the grid for blocks that should disappear based on their attributes.
   */
  public void checkForDisappear() { //cant use streams
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        checkCellForDisappear(i, j);
      }
    }
  }

  /**
   * Checks a specific cell in the grid for blocks that should disappear based on their attributes.
   *
   * @param cellI The row index of the cell.
   * @param cellJ The column index of the cell.
   */
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
          if (isActive && objectIndex == -1 && strategyMap.inverse().containsKey(attribute)) {
            objectIndex = k;
            objectStrategyValue = attribute;
          }
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


  /**
   * Checks if a cell is passable, i.e., if it does not contain blocks with certain attributes.
   *
   * @param cellI The row index of the cell.
   * @param cellJ The column index of the cell.
   * @return True if the cell is passable, false otherwise.
   */
  public boolean isPassable(int cellI, int cellJ) {
    return !cellHasAttribute(cellI, cellJ, STOPPABLE) && !cellHasAttribute(cellI, cellJ, WINNABLE)
        &&
        !cellHasAttribute(cellI, cellJ, HOTABLE) && !cellHasAttribute(cellI, cellJ, SINKABLE) &&
        !cellHasTextBlock(cellI, cellJ);
  }

  /**
   * Places an enemy block at the specified position in the grid.
   *
   * @param I The row index where the enemy should be placed.
   * @param J The column index where the enemy should be placed.
   */
  public void placeEnemy(int I, int J) {
    AbstractBlock crabBlock = factory.createBlock(CRAB_VISUAL_BLOCK, I, J);
    ((AbstractVisualBlock) crabBlock).modifyAttribute(KILL, true);
    grid[I][J].add(crabBlock);
    ((AbstractVisualBlock) crabBlock).modifyAttribute(KILL, true);
  }

  /**
   * Moves an enemy block from one position to another in the grid.
   *
   * @param fromI The row index of the original position.
   * @param fromJ The column index of the original position.
   * @param fromK The index of the enemy block in the original cell.
   * @param toI   The row index of the target position.
   * @param toJ   The column index of the target position.
   */
  public void moveEnemy(int fromI, int fromJ, int fromK, int toI, int toJ) {
    sortArray();
    moveBlock(fromI, fromJ, fromK, toI, toJ);
    sortArray();
    List<AbstractBlock> cell = grid[toI][toJ];
    AbstractBlock crabBlock = grid[toI][toJ].get(cell.size() - 1);
    if (!crabBlock.isTextBlock()) {
      ((AbstractVisualBlock) crabBlock).modifyAttribute(KILL, true);
    }

  }


  /**
   * Finds the position of the enemy block in the grid.
   *
   * @return An array containing the row index, column index, and block index of the enemy block.
   */
  public int[] enemyPosition() {
    return IntStream.range(0, grid.length)
        .boxed()
        .flatMap(i -> IntStream.range(0, grid[i].length)
            .boxed()
            .flatMap(j -> IntStream.range(0, grid[i][j].size())
                .filter(k -> grid[i][j].get(k).getAttribute(KILLABLE))
                .mapToObj(k -> new int[]{i, j, k})))
        .findFirst()
        .orElse(null);
  }


  /**
   * Sets the "kill" attribute for all crab blocks in the grid.
   */
  public void setCrabAttribute() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        for (int k = 0; k < grid[i][j].size(); k++) {
          AbstractBlock block = grid[i][j].get(k);
          if (!block.isTextBlock() && block.getBlockName().equals(CRAB_VISUAL_BLOCK)) {
            ((AbstractVisualBlock) block).modifyAttribute(KILL, true);
          }
        }
      }
    }
  }

  /**
   * Removes controllable blocks from a specified cell in the grid.
   *
   * @param i The row index of the cell.
   * @param j The column index of the cell.
   */
  public void removeControllable(int i, int j) {
    List<AbstractBlock> cell = grid[i][j];
    Iterator<AbstractBlock> iterator = cell.iterator();
    while (iterator.hasNext()) {
      AbstractBlock block = iterator.next();
      if (block.getAttribute(CONTROLLABLE)) {
        iterator.remove();
      }
    }
  }

  /**
   * Finds the index of the enemy block in a specified cell.
   *
   * @param cellI The row index of the cell.
   * @param cellJ The column index of the cell.
   * @return The index of the enemy block, or empty if not found.
   */
  public Optional<Integer> findEnemyIndex(int cellI, int cellJ) {
    for (int i = 0; i < grid[cellI][cellJ].size(); i++) {
      AbstractBlock block = grid[cellI][cellJ].get(i);
      if (block.getAttribute(KILLABLE)) {
        return Optional.of(i);
      }
    }
    return Optional.empty();
  }

  /**
   * Checks if a cell in the grid is empty.
   *
   * @param cellI The row index of the cell.
   * @param cellJ The column index of the cell.
   * @return True if the cell is empty, false otherwise.
   */
  public boolean cellIsEmpty(int cellI, int cellJ) {
    for (int i = 0; i < grid[cellI][cellJ].size(); i++) {
      AbstractBlock block = grid[cellI][cellJ].get(i);
      if (!block.isEmptyVisualBlock()) {
        return false;
      }
    }
    return true;
  }

}
