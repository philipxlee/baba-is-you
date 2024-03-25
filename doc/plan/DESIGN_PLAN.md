# OOGASalad Design Plan
### Arnav Nayak, Philip Lee, Divyansh Jain, Jonathan Esponda, Joseph Ogunbadewa, 
### Baba is Us

## Introduction

* Authoring Environment
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

Game Player
  * View
    * Scene
    * Use shared API
    * Observer design pattern to listen to model
  * Controller
    * ViewController, MainController
  * Model

Shared (Shared between Authoring Environment and Game Player), 
  * Scene, SceneSwitcher
  * BlockView
    * ObjectView (Baba, Wall, Rock, Empty, etc)
    * PropertyView (You, Push, Win)
    * RelationshipView (Is)
  * JSON File parser

## Configuration File Format

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
        "author": "GameDev123"
        "language": "English"
        "special-mode" "Normal"
    },
    "graphic": {
        "Baba": "images/baba.png",
        "Wall": "images/wall.png",
    }
}   


## Design Overview


## Design Details


## Design Considerations


## Test Plan


## Team Responsibilities

* Team Member #1

* Team Member #2

* Team Member #3

* Team Member #4

* Team Member #5

* Team Member #6

* Team Member #7

shared apis
- file management (loading/saving)
- view/gui widgets
- Scene interfacce and sceneswitcher
