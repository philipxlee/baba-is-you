package oogasalad.view.gameplay.mainscene;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.GameGridController;
import oogasalad.controller.gameplay.GameStateController;
import oogasalad.controller.gameplay.KeyHandlerController;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.level.Level;
import oogasalad.shared.blockview.BlockViewFactory;
import oogasalad.shared.observer.Observer;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import oogasalad.view.gameplay.gamestates.PauseScene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that encapsulates the grid interactions from the model and displays them. Uses the Observer
 * and Controller paradigms.
 */
public class GamePane implements Observer<Grid> {

  private int cellSize;
  private Group root;
  private KeyHandlerController keyHandlerController;
  private SceneController sceneController;
  private GameGridController gridController;
  private GameStateController gameStateController;
  private MainScene scene;
  private int width;
  private int height;
  private BlockViewFactory blockFactory;
  private Level level;
  private String currentDirection = "Right";
  private WidgetFactory factory;
  private Timeline timeline;
  private long milliseconds = 0;
  private Text time;
  private List<KeyCode> cheatKeys = Arrays.asList(KeyCode.W, KeyCode.L, KeyCode.R, KeyCode.X,
      KeyCode.E);
  private boolean keyPressed;
  private boolean firstKeyPressed = false;
  private final double INITIAL_DELAY = 1;
  private double currentDelay = INITIAL_DELAY;
  private final double DECREASE_FACTOR = 0.8;
  private Timeline timeline_Enemy;
  private boolean babaCute = false;
  private boolean isGameOver = true;
  private LevelController levelController;
  private static final Logger logger = LogManager.getLogger(GamePane.class);


  public void initializeGameGrid(int width, int height, MainScene scene,
      SceneController sceneController, Level initialLevel, LevelController levelController) {
    try {
      this.blockFactory = new BlockViewFactory("/blocktypes/blocktypes.json");
      this.width = width;
      this.height = height;
      this.root = new Group();
      this.scene = scene;
      this.sceneController = sceneController;
      this.gameStateController = new GameStateController(sceneController);
      this.keyHandlerController = new KeyHandlerController(
          gameStateController);
      this.level = initialLevel;
      this.gridController = new GameGridController(this, keyHandlerController, level);
      gameStateController.setGameGridController(gridController);
      this.factory = new WidgetFactory();
      this.time = factory.generateLine("00:00:00");
      time.setId("timeText");
      this.levelController = levelController;
      handleKeyPresses(scene);
      moveEnemy();

    } catch (Exception e) {
      gridController.showError("ERROR", e.getClass().getName());
    }
    renderGrid(); // Initial grid rendering
    startTimer();
  }

  private HBox setUpTimer() {
    Text timer = factory.generateLine(new WidgetConfiguration("Timer",
        sceneController.getLanguage()));
    HBox text = factory.wrapInHBox(new ArrayList<>(Arrays.asList(timer, time)), (int)
        (timer.getWrappingWidth()+ time.getWrappingWidth()));
    text.setAlignment(Pos.TOP_RIGHT);
    text.setPadding(new Insets(20));
    return text;
  }

  private void startTimer() {
    isGameOver = false;
    timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        milliseconds++;
        updateTimer();
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
    logger.info("Timer starts for this game.");
  }

  private void pauseTimer() {
    timeline.pause();
  }

  private ImageView setUpPauseButton() {
    InputStream stream = getClass().getResourceAsStream("/images/PauseImage.png");
    ImageView pauseBtn = new ImageView(new Image(stream));
    pauseBtn.setOnMouseClicked(event -> {
      sceneController.switchToScene(new PauseScene(sceneController, milliseconds, timeline));
      pauseTimer();
    });
    pauseBtn.setFitHeight(cellSize);
    pauseBtn.setFitWidth(cellSize);
    pauseBtn.setId("pauseButton");
    return pauseBtn;
  }

  private Button setUpSaveButton() {
    Button save = factory.makeButton(new WidgetConfiguration(70, 30,
        "Save", "white-button", sceneController.getLanguage()));
    save.setOnAction(event -> {
      try {
        levelController.saveLevel(this);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    save.setId("saveButton");
    return save;
  }

  private void updateTimer() {
    long min = milliseconds / (60 * 1000);
    long sec = (milliseconds / 1000) % 60;
    long mill = milliseconds % 1000;

    String timerString = String.format("%02d:%02d:%03d", min, sec, mill);
    time.setText(timerString);
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public Group getGrid() {
    return this.root;
  }

  @Override
  public void update(Grid data) {
    renderGrid();
  }

  public Pane setUpScreen() {
    HBox buttons = factory.wrapInHBox(new ArrayList<>(Arrays.asList(setUpSaveButton(), setUpPauseButton())),
        cellSize*2);
    buttons.setAlignment(Pos.BOTTOM_RIGHT);
    StackPane gameScreen = new StackPane(root, setUpTimer(), buttons);
    gameScreen.setAlignment(Pos.CENTER);
    gameScreen.setPrefWidth(width);

    Pane pane = new Pane();
    pane.setPrefSize(width, height);
    pane.getChildren().addAll(gameScreen);
    return pane;
  }

  private void renderGrid() {
    root.getChildren().clear();
    List<AbstractBlock>[][] grid = gridController.getGameGrid().getGrid();
    calculateCellSize(grid.length, grid[0].length);
    double blockOffset = 0; // Offset for displaying stacked blocks

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        List<AbstractBlock> blocks = grid[i][j];
        for (int k = 0; k < blocks.size(); k++) {
          initializeBlockImage(blocks, k, j, i, blockOffset);
        }
      }
    }
  }

  private void initializeBlockImage(List<AbstractBlock> blocks, int k, int j, int i, double blockOffset) {
    try {
      AbstractBlock block = blocks.get(k);
      String modifiedBlockName;

      if (block.getBlockName().contains("BabaVisual") && block.getAttribute("Controllable")) {
        if (!babaCute) {
          modifiedBlockName = block.getBlockName() + currentDirection;
        } else {
          modifiedBlockName = "Cute" + block.getBlockName() + currentDirection;
        }
      } else {
        modifiedBlockName = block.getBlockName();
      }
      ImageView visualObj = blockFactory.createBlockView(modifiedBlockName);

      if (visualObj == null) {
        gridController.showError("ERROR", "ImageView in AbstractBlockView is null");
      }
      formatImage(visualObj, k, blockOffset, j, i);
    }
    catch (Exception e) {
      gridController.showError("ERROR", e.getMessage());
      logger.error("Error initializing block images: " + e.getMessage());
    }
  }

  private void formatImage(ImageView visualObj, int k, double blockOffset, int j, int i) {
    double offsetX = j * cellSize + k * blockOffset;
    double offsetY = i * cellSize + k * blockOffset;

    // Ensure that the block does not exceed the boundaries of the cell
    offsetX = Math.min(offsetX, j * cellSize + cellSize - blockOffset);
    offsetY = Math.min(offsetY, i * cellSize + cellSize - blockOffset);

    visualObj.setFitWidth(cellSize - k * blockOffset);
    visualObj.setFitHeight(cellSize - k * blockOffset);
    visualObj.setPreserveRatio(true);
    visualObj.setX(offsetX);
    visualObj.setY(offsetY);
    root.getChildren().add(visualObj);
  }

  private void calculateCellSize(int r, int c) {
    int smallerDimension = Math.min(r, c);
    this.cellSize = Math.min(width / smallerDimension, height / smallerDimension);
  }

  private void handleKeyPresses(MainScene scene) {
    // For grid movement
    this.scene.getScene().addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode().isArrowKey() || cheatKeys.contains(event.getCode())) {
        gridController.sendPlayToModel(event.getCode());
        if (event.getCode().isArrowKey()) {
          this.currentDirection = event.getCode().getName();
        }
        renderGrid(); // Render grid
        gridController.resetBlocks(); // Reset all blocks
        scene.getInteractionPane().updateKeyPress(event.getCode());
        event.consume();
        keyPressed = true;
        firstKeyPressed = true;
      }
      if (event.getCode().isLetterKey()) {
        handleLetterKeyPresses(event);
      }
    });
    // For key press visualizer
    this.scene.getScene().addEventFilter(javafx.scene.input.KeyEvent.KEY_RELEASED, event -> {
      if (event.getCode().isArrowKey()) {
        scene.getInteractionPane().updateKeyRelease(event.getCode());
        event.consume();
      }
    });
  }

  private void handleLetterKeyPresses(KeyEvent event) {
      if (event.getCode() == KeyCode.X) {
        babaCute = !babaCute;
      }
      gridController.sendPlayToModel(event.getCode());
      renderGrid(); // Render grid
      gridController.resetBlocks(); // Reset all blocks
      event.consume();
      keyPressed = true;
      firstKeyPressed = true;
  }


  private void moveEnemy() {
    // Create a new Timeline
    timeline_Enemy = new Timeline();
    // Add a KeyFrame to the Timeline
    timeline_Enemy.getKeyFrames().add(
            new KeyFrame(Duration.seconds(currentDelay), event -> {
              if (!keyPressed && firstKeyPressed) {
                // No valid key pressed within current delay, call moveEnemy()
                keyHandlerController.moveEnemy();
              }
              // Reset the keyPressed flag after each execution
              keyPressed = false;
              // Update delay for next cycle
              currentDelay *= DECREASE_FACTOR; // Adjust DECREASE_FACTOR for desired slowdown rate (e.g., 0.9 for 10% decrease)
              if (isGameOver) {
                // Stop the Timeline
                timeline_Enemy.stop();
              }
            })

    );
    // Set the cycle count to indefinite so it repeats
    timeline_Enemy.setCycleCount(Timeline.INDEFINITE);
    // Start the Timeline
    timeline_Enemy.play();
  }

  public GameGridController getGridController() {
    return this.gridController;
  }
  public void setGameOverStatus(boolean status){
    this.isGameOver = status;
  }
}
