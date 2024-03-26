## Example Data

---
### Functional Variation 1:
- "The Dungeon of Doom" data file provides an example of a standard game without enhanced AI features, focusing on player navigation and interaction with static game elements.
### Functional Variation 2:
- "The Caverns of Insight" data file showcases a more advanced setup with AI elements and additional environmental hazards, indicating the need to support varied gameplay dynamics and interactions.

---


```JSON

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
        "special-mode": "Normal"
    },
    "graphic": {
        "Baba": "images/baba.png",
        "Wall": "images/wall.png",
        "Rock": "images/rock.png"
    }
}  


```

---


```JSON

{
    "levelName": "The Caverns of Insight",
        "gridSize": {
        "rows": 6,
        "columns": 8
    },
    "grid": {
    "cells": [
        ["0", "2", "0", "1", "1", "0", "2", "0"],
        ["1", "0", "0", "0", "1", "0", "0", "1"],
        ["1", "0", "3", "0", "0", "0", "3", "0"],
        ["0", "1", "0", "1", "0", "1", "0", "0"],
        ["1", "0", "0", "0", "3", "0", "0", "1"],
        ["0", "1", "1", "0", "2", "1", "0", "0"]
    ],
    "metadata": {
        "difficulty": "Medium",
        "author": "PuzzleMaster777",
        "language": "French",
        "special-mode": "ArtificialIntelligence"
    },
    "graphic": {
        "Player": "images/player.png",
        "Wall": "images/steel_wall.png",
        "Lava": "images/lava.png"
      }
    }
}

```