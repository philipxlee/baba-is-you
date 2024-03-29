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
1. 

## Authoring Environment - Controller
1.


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
1. Handle user input

## JsonManager - Configuration
1. Save authoring environment creations into JSON
2. Load JSON files into both the authoring environment and game player
3. Save current game in GamePlayer
