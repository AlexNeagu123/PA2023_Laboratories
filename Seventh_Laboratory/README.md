# Advanced Programming 2023. Seventh Laboratory.

:adult: **Alexandru Neagu** from **2B2** (transferred from **2A6**).

This repository contains solutions for:

## Seventh Laboratory ##

:white_check_mark: **Compulsory** - **all bullets** completed

:white_check_mark: **Homework** - **all bullets** completed

:white_check_mark: **Bonus** - **3/4 bullets** completed with **additional features**.

## Compulsory Part ##

:heavy_check_mark: Created an object oriented model of the game.
  - _:card_index_dividers: src/main/java/ro/game_
  - _:card_index_dividers: src/main/java/ro/players_

:heavy_check_mark: Each robot has a unique name and perform in a concurrent manner.
  - _:file_folder: src/main/java/ro/players/Robot.java_

:heavy_check_mark: The robots are moving randomly around the map and extract tokens from the shared memory when reaching an unvisited cell.
  - _:file_folder: src/main/java/ro/players/Robot.java_
  - _:card_index_dividers: src/main/java/ro/shared_
  
:heavy_check_mark: A message is displayed on the screen every time a robot visits a new cell.
  - _:card_index_dividers: src/main/java/ro/game/explorations_
 
 :heavy_check_mark: Simulated the exploration using a thread for each robot.
  - _:file_folder: src/main/java/ro/players/Robot.java_
  - _:file_folder: src/main/java/ro/game/explorations/Exploration.java_
  
## Homework Part ##

:heavy_check_mark: Implemented keyboard commands for starting/pausing the robots (all of them or only a specific one).
  - _:card_index_dividers: src/main/java/ro/commands_

:heavy_check_mark: Designed a parallel dfs algorithm such that each robot explores the map in a systematic fashion, ensuring the termination of the exploration process.
  - _:file_folder: src/main/java/ro/players/Robot.java_
  - _:file_folder: src/main/java/ro/game/maps/ExplorationMap.java_

:heavy_check_mark: Implemented a **timekeeper** thread that runs concurrently with the player threads, as a daemon.
  -  _:file_folder: src/main/java/ro/app/TimeKeeper.java_

:heavy_check_mark: At the end of the exploration, the number of tokens placed by each robot is displayed on the screen.
  - _:file_folder: src/main/java/ro/game/explorations/Exploration.java_

## Bonus Part ##

:heavy_check_mark: Implemented another game mode where the robots are exploring a grapgh instead of a matrix along with a collaborative graph search algorithm using Graph4J API.
  - _:file_folder: src/main/java/ro/game/explorations/GraphExploration.java_
  - _:file_folder: src/main/java/ro/game/maps/GraphMap.java_ 

:heavy_check_mark: Implemented a concurrent algorithm for determining a **minimum spanning tree** in a weighted graph.
  - _:file_folder: src/main/java/ro/extra/ParallelMSTAlgorithm.java_

:x: Considered the case in which the robots can share information when meeting at a vertex (fast collaborative graph exploration), the goal being to explore the whole graph in a minimum number of steps.

:heavy_plus_sign: Created some JUnit tests for the concurrent algorithm that computes the minimum spanning tree in a weighted graph. 
 - _:file_folder: src/test/java/ro/extra/ParallelEdgeSorterTest.java_
 - _:file_folder: src/test/java/ro/extra/ParallelMSTAlgorithmTest.java_

:heavy_plus_sign: Implemented an interactive command line shell where the user introduces several **exploration parameters** (such as exploration type and the maximum number of seconds for the exploration) and types **add/start/pause** commands. 
 - _:file_folder: src/main/java/ro/app/Shell.java_
