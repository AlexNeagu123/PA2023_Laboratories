# Advanced Programming 2023. Tenth Laboratory.

:adult: **Alexandru Neagu** from **2B2** (transferred from **2A6**).

This repository contains solutions for:

## Seventh Laboratory ##

:white_check_mark: **Compulsory** - **all bullets** completed

:white_check_mark: **Homework** - **all bullets** completed

:white_check_mark: **Bonus** - **all bullets** completed

## Compulsory Part ##

:heavy_check_mark: Created the project **ServerApplication**.
- _:card_index_dividers: ServerApplication/*_

:heavy_check_mark: Created the class **GameServer**. An instance of this class creates a _ServerSocket_ running at a specified port.
  - _:file_folder: ServerApplication/src/main/java/ro/server/GameServer.java_

:heavy_check_mark: Created the class **ClientThread**. An instance of this class will be responsible with communicating with a client _Socket_.
- _:file_folder: ServerApplication/src/main/java/ro/server/communication/ClientThread.java_
  
:heavy_check_mark: Created the project **ClientApplication**.
  - _:card_index_dividers: ClientApplication/*_
 
 :heavy_check_mark: Defined repository clases for the entities.
  - _:card_index_dividers: src/main/java/ro/repository/jpa_
 
 :heavy_check_mark: Created the class **GameClient**. An instance of this class reads commands from the keyboard and it sends them to the server.
  - _:file_folder: ClientApplication/src/main/java/ro/client/GameClient.java_

 
## Homework Part ##

:heavy_check_mark: Implemented functionalities of the game, using the classes **Game**, **Board** and **Player**.
  - _:file_folder: src/main/java/ro/server/game/Board.java_
  - _:file_folder: src/main/java/ro/server/game/Game.java_
  - _:file_folder: src/main/java/ro/server/game/Player.java_

:heavy_check_mark: The clients send the following commands to the server: _create game_, _join game_, _submit move_ and _set_name_
  - _:card_index_dividers: ServerApplication/src/main/java/ro/server/command/*_

:heavy_check_mark: The server is responsible with the game management and mediating the players.

:heavy_check_mark: The games are played under time control (blitz) with each player having the same amount of time at the beginning of the game. If a player's time runs out, the game is lost.
  - _:file_folder: ServerApplication/src/main/java/ro/server/game/TimeKeeper.java_
    
## Bonus Part ##

:heavy_check_mark: Assuming there are _n_ players registered on the server, I used **Gurobi** to create a schedule such that:
- Each player plays with every other player exactly once
- A player can not have more than _p_ games in a day
- The tournament must finish in at most _d_ days
- _:file_folder: ServerApplication/src/main/java/ro/server/tournaments/TournamentScheduler.java_

:heavy_check_mark: Once the schedule is created, I generate random outcomes for all games. Then, I find the sequence of players p[1], p[2], ..., p[n] such that p[i] beats p[i+1], for all _1 <= i < n_. 
  - _:file_folder: ServerApplication/src/main/java/ro/server/tournaments/TournamentSimulation.java_
