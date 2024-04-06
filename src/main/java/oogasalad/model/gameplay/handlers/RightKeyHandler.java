package oogasalad.model.gameplay.handlers;

import oogasalad.model.gameplay.grid.Grid;

public class RightKeyHandler extends KeyHandler {

    public RightKeyHandler(Grid grid){
        super(grid);
    }
    @Override
    public void execute(){
        handleKeyPress(0, 1);
    }
}
