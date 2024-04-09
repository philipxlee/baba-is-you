package oogasalad.model.gameplay.level;

import oogasalad.model.gameplay.grid.Grid;

public class Level {
    private final LevelMetadata levelMetadata;
    private final Grid grid;

    public Level(LevelMetadata levelMetadata){
        this.levelMetadata = levelMetadata;
        this.grid = new Grid(levelMetadata.rows(), levelMetadata.columns(), levelMetadata.initialConfiguration());
    }


}

