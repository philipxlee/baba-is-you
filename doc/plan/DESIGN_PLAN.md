# OOGASalad Design Plan
### Arnav Nayak, Philip Lee, Divyansh Jain, Jonathan Esponda, Joseph Ogunbadewa, Yasha Doddabele, Nikita Daga
### Baba is Us

## Introduction
- We will have two programs: Authoring Environment and Game Player
- Each application will be setup with a model-view controller architecture
- The Authoring environment will be responsible for creating a variation of a Baba is You game (JSON file)
- The Game player will be responsible for reading and playing a variation of a Baba is You game (JSON file)
- These two programs also have shared APIs and classes

### Shared
* Shared between Authoring Environment and Game Player
* Scene, SceneSwitcher
* BlockView
    * ObjectView (Baba, Wall, Rock, Empty, etc)
    * PropertyView (You, Push, Win)
    * RelationshipView (Is)
* JSON File parser

### Authoring Environment
* View
  * Scenes
  * Modify backend with drag-drop
  * This customizes what the user wants to be on the level upon initialization
* Controller
  * APIs to record down what was clicked by the player and selected.
  * Used to build the JSON file
  * addBlock(int m, int n, BlockEnum blockType);
  * saveLevel();
  * ViewController, MainController
* Model
  * Level
    * 2D Grid[m][n] (make abstract, iterator)
    * Metadata (Name, Description)
    * Block Interface
      * Baba
      * Empty
      * Wall
      * Rock
      * Relationship (Is)
      * Property (You, Push, Stop, Win)
    * Basic validation (At least one relationship: a block player, a property, a win block)
  * Turn level into JSON

### Game Player
* View
  * Scene
  * Use shared API
  * Observer design pattern to listen to model
* Controller
  * ViewController, MainController
* Model
  * Blocks (abstract class featuring subclasses Baba, Wall, Rock, Empty, etc)
  * Components pattern
    * Component interface
    * Controllable
    * Movable/Pushable
    * Winnable
  * Factory pattern
    * Block factory (uses reflections to create blocks)
  * Interpreter (parser to detect the rules/block sentences)
  * Handlers
    * MovementHandler
    * Provides APIs to move blocks 
  * Utility
    * Holds records, other utilities


## Configuration File Format

```json
{
    "levelName": "The Dungeon of Doom",
    "gridSize": {
        "rows": 5,
        "columns": 10
    },
    "grid": {
    "cells": [
        ["0", "0", "1", "1", "0", "0", "0", "1", "1", "0"],
        ["0", "0", "1", "0", "0", "1", "0", "0", "1", "0"],
        ["2", "0", "0", "0", "3", "0", "0", "0", "0", "0"],
        ["0", "1", "0", "0", "1", "1", "0", "0", "1", "0"],
        ["0", "1", "1", "0", "0", "0", "0", "1", "1", "0"]
    ],
    "metadata": {
        "difficulty": "Easy",
        "author": "GameDev123",
        "language": "English",
        "special-mode": "Normal",
    },
    "graphic": {
        "Baba": "images/baba.png",
        "Wall": "images/wall.png",
        "Rock": "images/rock.png",
    }
}  
```

## Design Overview
* Two apps: Game Player and Authoring Environment
* Shared classes between the two apps
* MVC design pattern


## Design Details
* Follow SOLID principles
* Use design patterns

## Design Considerations
* We will use the MVC design pattern
* We will use the Observer design pattern
* We will use the Factory design pattern
* We will use the Strategy design pattern
* We will use the Visitor design pattern
* We'll be sharing APIs between the respective models and views to avoid repetition.



## Test Plan
* Practice some TDD
* Aim to have a 70% internal testing throughout the milestones
* Increase testing near end of project


## Team Responsibilities

* Team Member #1: Arnav Nayak
  * Model for Game Player and Authoring Environment

* Team Member #2: Philip Lee
  * Model for Game Player

* Team Member #3: Joseph Ogunbadewa
  * Model for Game Player

* Team Member #4: Divyansh Jain
  * Model for Authoring Environment

* Team Member #5: Jonathan Esponda
  * View for Game Player and Authoring Environment

* Team Member #6: Yasha Doddabele
  * View for Game Player

* Team Member #7: Nikita Daga
  * View for Authoring Environment

