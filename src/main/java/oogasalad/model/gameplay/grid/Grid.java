package oogasalad.model.gameplay.grid;

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

  public List<AbstractBlock>[][] getGrid() {
    return this.grid;
  }
  private void InitializeGrid(){
    ArrayList<String>[][] gridOfArrayLists = new ArrayList[8][8];

    // Initializing elements
    for (int i = 0; i < gridOfArrayLists.length; i++) {
      for (int j = 0; j < gridOfArrayLists[i].length; j++) {

        gridOfArrayLists[i][j] = new ArrayList<String>();
        createBlocks(gridOfArrayLists[i][j],tempconfig[i][j]);
      }
    }
  }

  private void createBlocks(ArrayList<String> stringOfAbstractBlocks, String[] Blocktypes){
    for(int i=0; i< Blocktypes.length; i++){
      factory.createBlock(Blocktypes[i]);
    }
  }
  String[][][] tempconfig = {
          {{"BabaVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}},
          {{"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"RockVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}},
          {{"EmptyVisualBlock", "BabaVisualBlock"}, {"RockTextBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}},
          {{"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}},
          {{"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"RockVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}},
          {{"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"BabaTextBlock", "EmptyVisualBlock"}, {"IsTextBlock", "EmptyVisualBlock"}, {"YouTextBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}},
          {{"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"RockVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}},
          {{"BabaVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyVisualBlock"}}
  };

}
//[ ["RockVisualBlock", "BabaTextBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ["BabaTextBlock", "EmptyVisualBlock"] ["RockVisualBlock", "EmptyVisualBlock"] ["RockVisualBlock", "RockVisualBlock"] ["BabaVisualBlock", "EmptyVisualBlock"] ["RockVisualBlock", "RockVisualBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ];
//        [ ["BabaTextBlock", "EmptyVisualBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ["BabaVisualBlock", "EmptyVisualBlock"] ["BabaVisualBlock", "EmptyVisualBlock"] ["RockVisualBlock", "RockVisualBlock"] ["RockVisualBlock", "EmptyVisualBlock"] ["YouTextBlock", "BabaTextBlock"] ];
//        [ ["EmptyVisualBlock", "YouTextBlock"] ["RockTextBlock", "EmptyVisualBlock"] ["EmptyVisualBlock", "RockVisualBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ["EmptyVisualBlock", "YouTextBlock"] ["RockVisualBlock", "EmptyVisualBlock"] ["EmptyVisualBlock", "RockVisualBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ];
//        [ ["BabaTextBlock", "RockTextBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ["RockVisualBlock", "EmptyVisualBlock"] ["RockTextBlock", "EmptyVisualBlock"] ["RockVisualBlock", "EmptyVisualBlock"] ["EmptyVisualBlock", "RockVisualBlock"] ["BabaTextBlock", "RockVisualBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ];
//        [ ["RockTextBlock", "RockVisualBlock"] ["BabaTextBlock", "BabaVisualBlock"] ["BabaVisualBlock", "EmptyVisualBlock"] ["EmptyVisualBlock", "RockVisualBlock"] ["RockVisualBlock", "YouTextBlock"] ["EmptyVisualBlock", "YouTextBlock"] ["BabaVisualBlock", "RockVisualBlock"] ["BabaVisualBlock", "EmptyVisualBlock"] ];
//        [ ["EmptyVisualBlock", "RockVisualBlock"] ["RockTextBlock", "BabaTextBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ["EmptyVisualBlock", "RockTextBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ["RockVisualBlock", "RockTextBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ["EmptyVisualBlock", "BabaVisualBlock"] ];
//        [ ["EmptyVisualBlock", "EmptyVisualBlock"] ["YouTextBlock", "BabaTextBlock"] ["RockTextBlock", "BabaTextBlock"] ["RockVisualBlock", "BabaTextBlock"] ["EmptyVisualBlock", "RockTextBlock"] ["EmptyVisualBlock", "RockTextBlock"] ["EmptyVisualBlock", "RockTextBlock"] ["BabaTextBlock", "RockVisualBlock"] ];
//        [ ["EmptyVisualBlock", "YouTextBlock"] ["RockVisualBlock", "BabaVisualBlock"] ["BabaVisualBlock", "BabaTextBlock"] ["EmptyVisualBlock", "BabaTextBlock"] ["EmptyVisualBlock", "BabaTextBlock"] ["EmptyVisualBlock", "EmptyVisualBlock"] ["BabaVisualBlock", "EmptyVisualBlock"] ["BabaVisualBlock", "EmptyVisualBlock"] ];
