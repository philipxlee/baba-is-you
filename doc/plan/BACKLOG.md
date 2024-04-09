# Backlog

## Authoring Environment - View

1. Loading of previously constructed levels for editing
2. Saving of levels into configuration files
3. Drag and drop feature that allows user to insert game elements into the grid
4. Creating grid and object panels
5. Implementing the same visuals for elements as the GamePlayer
6. Provide a way for users to tag levels as easy, medium, or hard
7. Allow the m*n grid dimensions to be modifiable
8. Allow game elements on the grid to be deleted
9. Be able to classify different blocks (rule subject, rule relationship, rule predicate, objects)

## Authoring Environment - Model

1. Creating Level Representation (Iterator?)
2. Creating Block Representation
3. Methods to easily modify Level's grid
4. Methods to easily modify Level's metadata
5. Methods to clear and reset Level

## Authoring Environment - Controller

1. Serve as API for View to interact with Model
2. Read and update specific position of 2D grid with Block from controller
3. Load previously constructed levels from JSON for editing
4. Save current levels (Serialize to JSON)
5. Update grid dimensions dynamically on the backend
6. Implement validations for valid game constructions
7. Handle basic errors and exceptions in authoring environment.
8. Relay user commands from the View to the Model and update the View with changes in the Model.
9. Receiving and processing user input from the View.

## GamePlayer - View

1. Moving the Baba character around the screen with arrows keys
2. Interacting/moving other blocks with the Baba characters
3. Showing a completed level screen when the flag object is obtained
4. Scene and grid representation
5. Observer pattern to listen to model - potentially
6. Loading and saving of levels from configuration files
7. Clicking the pause/resume buttons in the game screen to show a pause screen

## GamePlayer - Model

1. Create grid representation
2. Creation of blocks via factory pattern
3. Movement of blocks via handlers
4. Observer pattern to listen to model to update the View
5. Components pattern to establish dynamic rule changes
6. Rule interpretation via interpreter
7. Loading of levels from configuration files

## GamePlayer - Controller

1. Act as an API for the View to interact with the Model
2. Method to request new game state given event (e.g. key press)
3. Instantiate frontend abstractions based on backend abstractions using reflection
4. Use Model as a data store to modify as events are given to it by the View

## JsonManager - Configuration

1. Save authoring environment creations into JSON
2. Load JSON files into both the authoring environment and game player
3. Save current game in GamePlayer
