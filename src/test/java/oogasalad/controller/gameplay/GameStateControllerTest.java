package oogasalad.controller.gameplay;

import oogasalad.view.gameplay.gamestates.LoseScene;
import oogasalad.view.gameplay.gamestates.WinScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class GameStateControllerTest {

  @Mock
  private SceneController mockSceneController;

  private GameStateController gameStateController;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    gameStateController = new GameStateController(mockSceneController);
  }

  @Test
  void testDisplayGameOverWin() {
    gameStateController.displayGameOver(true);
    verify(mockSceneController, times(1)).switchToScene(any(WinScene.class));
  }

  @Test
  void testDisplayGameOverLose() {
    gameStateController.displayGameOver(false);
    verify(mockSceneController, times(1)).switchToScene(any(LoseScene.class));
  }
}
