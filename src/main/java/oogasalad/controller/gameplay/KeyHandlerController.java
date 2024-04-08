package oogasalad.controller.gameplay;
import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.handlers.*;

public class KeyHandlerController {
    private KeyHandler keyHandler;
    private GameOverController gameOverController;

    public KeyHandlerController(GameOverController gameOverController) {
        this.gameOverController = gameOverController;
    }

    public void executeKey(Grid grid, KeyCode code) {
        keyHandler = switch (code) {
            case UP -> new UpKeyHandler(grid, gameOverController);
            case DOWN -> new DownKeyHandler(grid, gameOverController);
            case LEFT -> new LeftKeyHandler(grid, gameOverController);
            case RIGHT -> new RightKeyHandler(grid, gameOverController);
            default -> null;
        };

        if (keyHandler != null) {
            keyHandler.execute();
        }
    }
}

