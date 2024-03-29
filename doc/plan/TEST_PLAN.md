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
- Happy Path Test for moveBlock (Positive Test):
  - Action: Moving a block to an adjacent free space.
  - Expected Outcome: The block is successfully moved to the new position.
  - We can verify this by checking the block's new position in the grid and ensuring it matches the expected position.
- Attempting to Move a Non-Pushable Block (Negative Test):
  - Action: Trying to move a block that is not pushable.
  - Expected Outcome: The block remains in its original position.
  - Our design allows us to mock a block's components, setting a block as non-pushable and asserting that its position does not change post-action.
- Win Condition Activation (Positive Test):
  - Action: Moving the player block to overlap with a winnable block.
  - Expected Outcome: The game state changes to a win condition.
  - We can invoke the interact method on the winnable component and check if the game context's state updates to reflect the win.
- Moving Block Outside Grid Boundaries (Negative Test):
  - Action: Attempting to move a block outside the grid.
  - Expected Outcome: The block does not move.
  - By checking the block's position remains unchanged and ensuring no exceptions are thrown, we confirm the grid boundary logic is correct.

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
##### [NEXT API] - AUTHOR