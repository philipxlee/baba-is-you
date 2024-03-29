## Test Plan

---


### Strategies for Enhancing API Testability:
- Incorporating Dependency Injection (DI): 
  - We will implement or focus on introducing DI to facilitate easier testing and better isolation of our components. 
  - This strategy allows us to inject mock implementations of dependencies during testing, ensuring that we can test 
  each class in isolation and control the test environment more effectively.
- Defining Clear Interfaces: 
  - By clearly defining interfaces for our components, we ensure that any component can be easily mocked or replaced 
  with a stub during testing. This approach also helps us adhere to the single responsibility principle, making our 
  code more maintainable and testable.

### Test Scenarios

---

#### Moving a Block - Philip
- Moving a Block to an Adjacent Free Space (Positive Test)
  - GIVEN a grid with at least one movable block adjacent to an empty space
  - IF the player initiates a move command in the direction of the empty space
  - THEN the block should move to the new position successfully
  - We verify this by checking the new position of the block in the grid to ensure it occupies the space we expected it to move into.
- Attempting to Move a Non-Pushable Block (Negative Test)
  - GIVEN a grid with a non-pushable block adjacent to the player's block
  - IF the player initiates a move command towards the non-pushable block
  - THEN the non-pushable block should remain in its original position
  - We can confirm this by asserting the block's position remains unchanged after the move command is processed.
- Win Condition Activation (Positive Test)
  - GIVEN a grid where moving the player block to a specific position activates a win condition
  - IF the player moves the block to that specific position
  - THEN the game state should change to reflect a win condition
  - We check if the game context or state updates to a win scenario after the player block overlaps with the winnable block.
- Moving Block Outside Grid Boundaries (Negative Test)
  - GIVEN a grid with a movable block positioned at the boundary
  - IF the player initiates a move command directing the block outside the grid boundaries
  - THEN the block does not move and remains at its boundary position
  - We verify this by confirming the block's position remains constant post-command and ensuring no exceptions or unintended behavior occur.

##### Loading/Saving - Jonathan
- File Loads in Correctly (Positive Test)
  - GIVEN a working load feature in the GamePlayer and Authoring Environment
  - IF the user uploads a file through a file chooser
  - THEN the testing should return the correct file
  - We would confirm that it is working by checking that the file is not null and that the path 
  matches that of the file chosen.
- Invalid File is uploaded (Negative Test)
  - GIVEN a working load feature in the GamePlayer and Authoring Environment
  - IF the user uploads an invalid file
  - THEN an error pop up should appear that describes why the file is invalid
  - ErrorHandler interface will allow for error pop ups through an 
  onError(AlertType type, String message) method
- File Saves Correctly (Positive Test)
  - GIVEN a working save feature in the GamePlayer and Authoring Environment
  - IF the user saves an authoring environment or current game 
  - THEN the saved file should load into both authoring environment and game player accurately
  - We would confirm this by verifying the size of the grid and the location of specific blocks. 
- File Does Not Save Correctly (Negative Test)
  - GIVEN a working save feature in the GamePlayer and Authoring Environment
  - IF the user saves a game or level file that is invalid
  - THEN an error message should pop up explaining why the current state cannot be saved. 
  - ErrorHandler interface will allow for error pop ups through an
    onError(AlertType type, String message) method


##### Rule Interaction - Joseph
- Creating a New Rule (Positive Test):
  - Action: Creating a new rule by aligning the words "BABA", "IS", and "YOU".
  - Expected Outcome: The character named BABA becomes controllable by the player.
  - Verification: Verify that the player can now move the character BABA according to the rules.
- Overlapping Rules (Negative Test):
  - Action: Overlapping conflicting rules, such as "BABA IS YOU" and "BABA IS WIN".
  - Expected Outcome: The game should handle conflicting rules gracefully without crashing.
  - Verification: Ensure that the game does not allow contradictory rules to simultaneously apply, maintaining game integrity.
- Achieving Victory (Positive Test):
  - Action: Aligning the words "BABA", "IS", and "WIN" to make BABA the winning condition.
  - Expected Outcome: The level is completed when BABA reaches the winning condition.
  - Verification: Confirm that the victory condition is triggered when BABA overlaps with the winning object.
- Unreachable Victory (Negative Test):
  - Action: Making the winning condition unreachable due to blocked paths or conflicting rules.
  - Expected Outcome: The level remains incomplete until the victory condition becomes reachable.
  - Verification: Ensure that the victory condition is not triggered prematurely and that the player must solve the level correctly to win.


##### View - Keyboard presses/Screen interactions - Yasha
- Game is paused (Positive Test)
  - GIVEN a working frontend with timeline functionality
  - IF a user presses the pause button
  - THEN the game time should pause and a splash screen should show with a continue button
  - We would confirm this by checking that the time stays the same and the main screen switches to this
  pause screen.
- Keyboard keys that aren't arrows are pressed (Negative Test)
  - GIVEN functionality for the handleKeyboardPress() method in the View
  - IF a user presses keys that aren't arrow buttons
  - THEN Baba/all blocks should remain in the same place.
  - We could check this by ensuring the position of both the front and backend blocks remain in the same 
  place. (The backend would probably be a separate test though)
- The Left arrow key is pressed (Positive Test)
  - GIVEN functionality for the handleKeyboardPress() method in the View
  - IF a user presses the left arrow
  - THEN the View should move Baba/associated blocks to the left.
  - This would be checked by checking all associated blocks.
- User presses on blocks/parts of the game screen (Negative Test)
  - GIVEN a separate functionality for a game screen (Baba Is You gameplay) and an interaction screen
    (where you can load a file and pick new games),
  - IF the user clicks on any of the blocks in the game screen,
  - THEN nothing should happen.
  - We can check this by ensuring the positions of all blocks remain the same. You should only be able
  to click on icons in the interaction screen and the pause button in the game screen.


##### Authoring Environment - Adding blocks and elements : Divyansh

- Adding a Block Element to an Empty Grid Cell (Positive Test)

    - GIVEN a grid with at least one empty space
    - IF the user inserts a block element into an empty grid cell
    - THEN the block element should occupy the specified grid cell
    - We verify this by checking if the specified grid cell contains the inserted block element after the insertion operation. 
  
- Attempting to Add a Block Element to an Occupied Grid Cell (Negative Test)

  - GIVEN a grid with a block element already occupying a specific grid cell
  - IF the user attempts to insert another block element into the same grid cell
  - THEN the insertion operation should fail, and the grid cell should remain unchanged
  - We can confirm this by checking if the grid cell still contains the initial block element after the insertion attempt.

- Adding a Block Element Outside Grid Boundaries (Negative Test)

  - GIVEN a grid with predefined boundaries
  - IF the user tries to insert a block element outside the grid boundaries
  - THEN the insertion operation should fail, and no block element should be added
  - We verify this by checking that the grid remains unchanged after the insertion attempt, and no exceptions are thrown.

- Modifying an Existing Block Element (Positive Test)

  - GIVEN a grid with an existing block element occupying a specific grid cell
  - IF the user modifies the properties or characteristics of the existing block element
  - THEN the changes should be reflected in the grid cell containing the block element
  - We confirm this by checking that the modified properties of the block element are accurately represented in the corresponding grid cell.

##### [NEXT API] - AUTHOR