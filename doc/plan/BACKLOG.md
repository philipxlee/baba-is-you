# Backlog

## Authoring Environment - View
1. Loading and saving of levels from configuration files
2. Drag and drop or Clicking feature that allows user to insert game elements
   into the grid that represents the level being built.
3. Creating grid and object panels
4. Implementing the same visuals as the GamePlayer

## Authoring Environment - Model
1. Creating Level Representation (Iterator)
2. Creating Block Representation
3. Methods to easily modify Level's grid 
4. Methods to easily modify Level's metadata

## Authoring Environment - Controller
1. API for View to interact with Model
2. Parse 


## GamePlayer - View
1. Scene and grid representation
2. Observer pattern to listen to model
3. Loading and saving of levels from configuration files

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
