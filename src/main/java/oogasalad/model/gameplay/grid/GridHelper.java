package oogasalad.model.gameplay.grid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;


public class GridHelper {

  protected final List<AbstractBlock>[][] grid;
  private final BlockFactory factory;
  private static final String KILL = "Kill";
  private static final String KILLABLE = "Killable";
  private static final String CONTROLLABLE = "Controllable";

  private static final String PUSHABLE = "Pushable";

  private static final String HOTABLE = "Hotable";

  private static final String SINKABLE = "Sinkable";
  /**
   * Constructs a new instance of the GridHelper class with the specified number of rows and columns.
   * The constructor initializes the grid with the given dimensions and instantiates a BlockFactory.
   * @param rows The number of rows in the grid.
   * @param cols The number of columns in the grid.
   */
  public GridHelper(int rows, int cols) {
    this.grid = new ArrayList[rows][cols];
    this.factory = new BlockFactory();
  }


  /**
   * Moves a block from one cell to another.
   *
   * @param fromI The row index of the source cell.
   * @param fromJ The column index of the source cell.
   * @param fromK The index of the block in the source cell.
   * @param ToI   The row index of the target cell.
   * @param ToJ   The column index of the target cell.
   */
  public void moveBlock(int fromI, int fromJ, int fromK, int ToI, int ToJ) {
    AbstractBlock block = grid[fromI][fromJ].get(fromK);
    grid[ToI][ToJ].add(block);
    grid[fromI][fromJ].remove(fromK);

    // Important: Set the row and col of visual blocks to the new position if moved
    if (!block.isTextBlock()) {
      block.setRow(ToI);
      block.setCol(ToJ);
    }

    if (grid[fromI][fromJ].isEmpty()) {
      addBlock(fromI, fromJ, "EmptyVisualBlock");
    }
  }



  /**
   * Resets all behaviors for blocks in the grid.
   */
  public void resetAllBlocks() {
    //List<AbstractBlock>[][] grid = getGrid();
    for (List<AbstractBlock>[] blocksRow : grid) {
      for (List<AbstractBlock> cell : blocksRow) {
        for (AbstractBlock block : cell) {
          if (!block.isTextBlock()) {
            block.resetAllBehaviors();
          }
        }
      }
    }
  }

  /**
   * Adds a block to the specified cell in the grid.
   *
   * @param i         The row index of the cell.
   * @param j         The column index of the cell.
   * @param BlockType The type of block to add.
   */
  private void addBlock(int i, int j, String BlockType) {
    grid[i][j].add(factory.createBlock(BlockType, i, j));
  }

  /**
   * Checks if a block can be moved to the margin of the grid.
   *
   * @param endI                 The target row index for the block.
   * @param endJ                 The target column index for the block.
   * @param endK                 The index of the block in the target cell.
   * @param controllableintialI  The initial row index of the controllable block.
   * @param controllableintialJ  The initial column index of the controllable block.
   * @param controllableinitialK The index of the controllable block in its initial cell.
   * @return True if the block can be moved to the margin, false otherwise.
   */
  public boolean isMovableToMargin(int endI, int endJ, int endK, int controllableintialI,
      int controllableintialJ, int controllableinitialK) {
    boolean already_in_margin = isAlreadyInMargin(controllableintialI, controllableintialJ);
    if (already_in_margin) {
      return true;
    }
    if (!isMovingToMargin(endI, endJ)) {
      return true;
    }
    if ((endI == grid.length - 1 || endI == 0)) {
      int indexI;
      indexI = (endI == 0) ? endI + 1 : endI - 1;
      return grid[indexI][endJ].stream().anyMatch(block -> block.getAttribute("Controllable"));
    } else if ((endJ == grid[0].length - 1 || endJ == 0)) {
      int indexJ;
      indexJ = (endJ == 0) ? endJ + 1 : endJ - 1;
      return grid[endI][indexJ].stream().anyMatch(block -> block.getAttribute("Controllable"));
    } else {
      return false;
    }
  }




  /**
   * Retrieves the indices of all pushable blocks in a cell.
   *
   * @param i The row index of the cell.
   * @param j The column index of the cell.
   * @return A list containing the indices of all pushable blocks in the cell.
   */
  public List<Integer> allPushableBlocksIndex(int i,
      int j) { //Cant use stream and ForEach because we want to ensure order of element in arraylist are kept same way in indiceslist
    List<Integer> indicesList = new ArrayList<>();
    for (int index = 0; index < grid[i][j].size(); index++) {
      AbstractBlock block = grid[i][j].get(index);
      if (block.isTextBlock() || (
          !block.isTextBlock() && block.getAttribute("Pushable"))) {
        indicesList.add(index);
      }
    }

    return indicesList;
  }

  /**
   * Checks if a cell contains a block with the Controllable behavior.
   *
   * @param i The row index of the cell.
   * @param j The column index of the cell.
   *@param attribute The attribue you want to check for in the cell.
   * @return True if the cell contains a block with the Controllable behavior, false otherwise.
   */
  public boolean cellHasAttribute(int i, int j, String attribute){
    return grid[i][j].stream().anyMatch(block -> block.getAttribute(attribute));
  }


  /**
   * Checks if a cell contains a block with the Pushable behavior or a TextBlock.
   *
   * @param i The row index of the cell.
   * @param j The column index of the cell.
   * @return True if the cell contains a block with the Pushable behavior or a TextBlock, false
   * otherwise.
   */
  public boolean cellHasPushable(int i, int j) {
    boolean hasPushable = false;
    boolean textBlock = false;
    for (AbstractBlock block : grid[i][j]) {
      if (block.isTextBlock()) {
        textBlock = true;
      }
      if (block.getAttribute(PUSHABLE)) {
        hasPushable = true;
      }
    }
    return hasPushable || textBlock;
  }

  /**
   * Checks if the specified indices represent a cell that is already at the margin of the grid.
   *
   * @param i The row index of the cell.
   * @param j The column index of the cell.
   * @return True if the cell is already at the margin, false otherwise.
   */
  private boolean isAlreadyInMargin(int i, int j) {
    return ((i == grid.length - 1 || i == 0) || (j == grid[0].length - 1 || j == 0));
  }

  /**
   * Checks if moving to the specified indices would cause the cell to be at the margin of the
   * grid.
   *
   * @param nextI The next row index.
   * @param nextJ The next column index.
   * @return True if moving to the specified indices would lead to the cell being at the margin,
   * false otherwise.
   */
  private boolean isMovingToMargin(int nextI, int nextJ) {
    return ((nextI == grid.length - 1 || nextI == 0) || (nextJ == grid[0].length - 1
        || nextJ == 0));
  }


  public void sortArray(){
    for (List<AbstractBlock>[] row : grid) {
      for (List<AbstractBlock> blockList : row) {
        if (blockList != null) {
          // Filter out all CrabVisualBlock instances into a separate list
          List<AbstractBlock> crabs = blockList.stream()
                  .filter(block -> block.getBlockName().equals("CrabVisualBlock"))
                  .collect(Collectors.toList());

          // Remove all CrabVisualBlock instances from the original list
          blockList.removeIf(block -> block.getBlockName().equals("CrabVisualBlock"));

          // Add all CrabVisualBlock instances at the end of the list
          blockList.addAll(crabs);
        }
      }
    }
  }
  public boolean cellHasTextBlock(int i, int j){
    for(AbstractBlock block : grid[i][j]){
      if(block.isTextBlock()){
        return true;
      }
    }
    return false;
  }


  /**
   * Finds all controllable blocks in the grid.
   * @param blocktype the kind of block you want to look for in the grid
   * @return A list of arrays containing the row, column, and index of each desired block.
   */
  public List<int[]> findAllPresentBlock(String blocktype) { //record class
    List<int[]> allPresentBlock = new ArrayList<>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        for (int k = 0; k < grid[i][j].size(); k++) {
          AbstractBlock block = grid[i][j].get(k);
          if (block != null && block.getAttribute(blocktype)) {
            int[] a = {i, j, k};
            allPresentBlock.add(a);
          }
        }
      }
    }
    return allPresentBlock;
  }



}
