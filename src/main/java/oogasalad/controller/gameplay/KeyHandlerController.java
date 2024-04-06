package oogasalad.controller.gameplay;
import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.handlers.*;

public class KeyHandlerController {
    private KeyHandler keyHandler;

    public KeyHandlerController(Grid grid, KeyCode code) {
        keyHandler = switch (code) {
            case UP -> new UpKeyHandler(grid);
            case DOWN -> new DownKeyHandler(grid);
            case LEFT -> new LeftKeyHandler(grid);
            case RIGHT -> new RightKeyHandler(grid);
            default -> null;
        };

        if (keyHandler != null) {
            keyHandler.execute();
        }
    }
}

