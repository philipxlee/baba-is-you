package oogasalad.model.gameplay.handlers;

import oogasalad.model.gameplay.grid.Grid;

public class UpKeyHandler extends KeyHandler {
    public UpKeyHandler(Grid grid){
        super(grid);
    }
    @Override
    public void execute(){
        handleKeyPress(-1, 0);
    }
}
