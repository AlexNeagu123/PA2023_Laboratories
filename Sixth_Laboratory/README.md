# Advanced Programming 2023. Sixth Laboratory.

:adult: **Alexandru Neagu** from **2B2** (transferred from **2A6**).

This repository contains solutions for:

## Sixth Laboratory ##

:white_check_mark: **Compulsory** - **all bullets** completed

:white_check_mark: **Homework** - **all bullets** completed

:white_check_mark: **Bonus** - **all bullets** completed

## Compulsory Part ##

:heavy_check_mark: Created the main frame of the application.
  - _:file_folder: src/main/java/com/gui/MainFrame.java_

:heavy_check_mark: Created a configuration panel for introducing parameters regarding the number of dots and lines and a button for creating a new game.
  - _:file_folder: src/main/java/com/gui/ConfigPanel.java_
  
:heavy_check_mark: Created a canvas (drawing panel) for drawing the board.
  - _:file_folder: src/main/java/com/gui/DrawingPanel.java_

:heavy_check_mark: Created control panel for managing the game.
  - _:file_folder: src/main/java/com/gui/ControlPanel.java_
  
## Homework Part ##

:heavy_check_mark: Created an object oriented model of the game.
  - _:card_index_dividers: src/main/java/com/game_

:heavy_check_mark: Implemented the logic of the game.
  - _:file_folder: src/main/java/com/game/Game.java_
  - _:file_folder: src/main/java/com/game/Player.java_

:heavy_check_mark: Exported the current image of the game board into a PNG file.
  -  _:page_with_curl: chooseFileAndSaveImage() method from the _GameUtils_ class_

:heavy_check_mark: Used object serialization in order to save and restore the current status of the game.
  - _:page_with_curl: chooseFileAndSerializeGame() and chooseFileAndDeserializeGame() methods from the GameUtils class_

## Bonus Part ##

:heavy_check_mark: Implemented an AI for the game.
  - _:page_with_curl: getBestLineToColor() method from the Player class_

:heavy_check_mark: Implement an efficient algorithm for counting all the triangles in a graph using _Graph4j_ API.
  - _:file_folder: src/main/java/com/graph/TriangleCountingAlgorithm.java_
