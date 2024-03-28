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

##### [NEXT API] - AUTHOR