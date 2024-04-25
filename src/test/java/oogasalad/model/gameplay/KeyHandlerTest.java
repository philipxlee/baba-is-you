package oogasalad.model.gameplay;

import oogasalad.controller.gameplay.GameStateController;
import oogasalad.model.gameplay.blocks.blockvisitor.AttributeVisitor;
import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WallVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.LavaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.WaterVisualBlock;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.handlers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeyHandlerTest {
    private KeyHandler keyHandler;
    private Grid grid;
    private GameStateController gameStateControllerMock;
    private final static int ROWS = 5;

    private final static int COLS = 5;
    AttributeVisitor wallVisitor;
    AttributeVisitor youVisitor;

    AttributeVisitor hotVisitor;

    AttributeVisitor meltVisitor;

    AttributeVisitor sinkVisitor;
    AttributeVisitor drownVisitor;
    private  BabaVisualBlock babaBlock;
    private WallVisualBlock wallBlock;


    private final String[][][] initialConfiguration = {
            {{"EmptyVisualBlock"}, {"WallVisualBlock"}, {"FlagVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}},
            {{"EmptyVisualBlock"}, {"BabaVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"FlagVisualBlock"}},
            {{"FlagVisualBlock"}, {"EmptyVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"BabaVisualBlock"}},
            {{"WallVisualBlock"}, {"FlagVisualBlock"}, {"EmptyVisualBlock"}, {"BabaVisualBlock"}, {"EmptyVisualBlock"}},
            {{"EmptyVisualBlock"}, {"WallVisualBlock"}, {"EmptyVisualBlock"}, {"FlagVisualBlock"}, {"BabaVisualBlock"}}
    };





    /**
     * Set up method executed before each test case.
     * Initializes the necessary objects and dependencies for testing, including:
     * - Instantiation of a YouVisitor object.
     * - Creation of a grid with specified rows, columns, and initial configuration.
     * - Mocking of a GameStateController object.
     * - Creation and visiting of visual blocks (Baba and Wall) for testing movement and interaction.
     */
    @BeforeEach
    public void setUp() {
        youVisitor = new AttributeVisitor("You");
        wallVisitor = new AttributeVisitor("Stop");
        hotVisitor = new AttributeVisitor("Hot");
        meltVisitor = new AttributeVisitor("Melt");
        sinkVisitor = new AttributeVisitor("Sink");
        drownVisitor = new AttributeVisitor("Drown");
        grid = new Grid(ROWS, COLS, initialConfiguration);
        gameStateControllerMock = Mockito.mock(GameStateController.class);
        babaBlock = new BabaVisualBlock("Baba", 1, 1);
        //youVisitor.visit(babaBlock);
        babaBlock.accept(youVisitor);
        wallBlock = new WallVisualBlock("Wall", 0, 0);
        wallBlock.accept(wallVisitor);

    }




    /**
     * Test method to verify the behavior of moving a block to the right using the RightKeyHandler.
     * It checks if the size of the cell after the execution is as expected.
     */
    @Test
    public void testRightKeyHandler(){
        grid.getGrid()[0][0].add(babaBlock);
        keyHandler = new RightKeyHandler(grid, gameStateControllerMock);
        keyHandler.execute();
        int size = grid.cellSize(0, 1);
        assertEquals(2, size);

    }




    /**
     * Test method to verify the behavior of moving a block to the left using the LeftKeyHandler.
     * It checks if the size of the cell after the execution is as expected.
     */
    @Test
    public void testLeftKeyHandler(){
        grid.getGrid()[0][1].add(babaBlock);
        keyHandler = new LeftKeyHandler(grid, gameStateControllerMock);
        keyHandler.execute();
        assertEquals(2, grid.cellSize(0, 0));
    }




    /**
     * Test method to verify the behavior of moving a block up using the UpKeyHandler.
     * It checks if the size of the cell after the execution is as expected.
     */
    @Test
    public void testUpKeyHandler(){
        grid.getGrid()[1][0].add(babaBlock);
        keyHandler = new UpKeyHandler(grid, gameStateControllerMock);
        keyHandler.execute();
        assertEquals(2, grid.cellSize(0, 0));
    }





    /**
     * Test method to verify the behavior of moving a block down using the DownKeyHandler.
     * It checks if the size of the cell after the execution is as expected.
     */
    @Test
    public void DownKeyHandler(){
        grid.getGrid()[0][0].add(babaBlock);
        keyHandler = new DownKeyHandler(grid, gameStateControllerMock);
        keyHandler.execute();
        assertEquals(2, grid.cellSize(1, 0));
    }





    /**
     * Test method to verify that a block cannot move to a cell with a wall using LeftKeyHandler.
     * It checks if the wall is still present in the same position and if the cell size remains unchanged.
     */
    @Test
    public void testNotMovableToMargin(){
        grid.getGrid()[1][1].add(wallBlock);
        keyHandler = new LeftKeyHandler(grid,gameStateControllerMock);
        keyHandler.execute();
        assertEquals("Wall", grid.getBlock(1, 1, 1).getBlockName());
        assertEquals(2, grid.cellSize(1, 1));
    }




    /**
     * Test method to verify that a block can move to a cell with no obstacles using LeftKeyHandler.
     * It checks if the size of the destination cell after the execution is as expected.
     */
    @Test
    public void testMovableToMargin(){
        grid.getGrid()[1][1].add(babaBlock);
        keyHandler = new LeftKeyHandler(grid,gameStateControllerMock);
        keyHandler.execute();
        assertEquals(2, grid.cellSize(1, 0));
    }





    /**
     * Test method to verify the phasing behavior of a block to a cell with another block.
     * It checks if the size of the destination cell after the execution is as expected.
     */
    @Test
    public void testPhasing(){
        grid.getGrid()[1][1].add(babaBlock);
        keyHandler = new RightKeyHandler(grid,gameStateControllerMock);
        keyHandler.execute();
        assertEquals(2, grid.cellSize(1, 2));
    }




    /**
     * Test method to verify the behavior of having multiple instances of the same block (Baba).
     * It checks if the size of the destination cells after the execution is as expected for both instances.
     */
    @Test
    public void testMultipleBaba(){
        grid.getGrid()[1][1].add(babaBlock);
        BabaVisualBlock babaBlock2 = new BabaVisualBlock("Baba2", 1, 1);
        babaBlock2.accept(youVisitor);
        grid.getGrid()[2][1].add(babaBlock2);
        keyHandler = new RightKeyHandler(grid,gameStateControllerMock);
        keyHandler.execute();
        assertEquals(2, grid.cellSize(1, 2));
        assertEquals(2, grid.cellSize(2, 2));
    }
    /**
     * Test method to verify the behavior of melting for a BabaBlock.
     * The test ensures that when a BabaBlock is subjected to a meltVisitor,
     * it melts as expected and occupies the cell where a lava block is present.
     * The cell sizes before and after the execution of the RightKeyHandler are compared.
     */
    @Test
    public void testMeltable(){
        babaBlock.accept(meltVisitor);
        grid.getGrid()[1][1].add(babaBlock);
        LavaVisualBlock lavaBlock = new LavaVisualBlock("Lava", 1, 2);
        lavaBlock.accept(hotVisitor);
        grid.getGrid()[1][2].add(lavaBlock);
        int nextCellSize = grid.cellSize(1, 2);
        int presentCellSize = grid.cellSize(1, 1);

        keyHandler = new RightKeyHandler(grid,gameStateControllerMock);
        keyHandler.execute();

        assertEquals(presentCellSize - 1, grid.cellSize(1, 1));
        assertEquals(nextCellSize, grid.cellSize(1, 2));
    }

    /**
     * Test method to verify the behavior of sinking for a BabaBlock.
     * The test ensures that when a BabaBlock is subjected to a drownVisitor,
     * it sinks as expected and occupies the cell where a WaterVisualBlock is present.
     * The cell sizes before and after the execution of the RightKeyHandler are compared.
     */
    @Test
    public void testSinkable(){
        babaBlock.accept(drownVisitor);
        grid.getGrid()[1][1].add(babaBlock);
        WaterVisualBlock waterBlock = new WaterVisualBlock("Water", 1, 2);
        waterBlock.accept(sinkVisitor);
        grid.getGrid()[1][2].add(waterBlock);
        int nextCellSize = grid.cellSize(1, 2);
        int presentCellSize = grid.cellSize(1, 1);

        keyHandler = new RightKeyHandler(grid,gameStateControllerMock);
        keyHandler.execute();

        assertEquals(presentCellSize - 1, grid.cellSize(1, 1));
        assertEquals(nextCellSize, grid.cellSize(1, 2));
    }

}