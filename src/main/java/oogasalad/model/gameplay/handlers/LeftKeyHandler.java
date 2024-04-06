package oogasalad.model.gameplay.handlers;

import oogasalad.model.gameplay.grid.Grid;

public class LeftKeyHandler extends KeyHandler {
    public LeftKeyHandler(Grid grid){
        super(grid);
    }
    @Override
    public void execute(){
        handleKeyPress(0, -1);
    }
}
