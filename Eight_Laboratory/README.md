# Advanced Programming 2023. Eight Laboratory.

:adult: **Alexandru Neagu** from **2B2** (transferred from **2A6**).

This repository contains solutions for:

## Seventh Laboratory ##

:white_check_mark: **Compulsory** - **all bullets** completed

:white_check_mark: **Homework** - **all bullets** completed

:white_check_mark: **Bonus** - **all bullets** completed

## Compulsory Part ##

:heavy_check_mark: Created a relational database using Postgres RDBMS

:heavy_check_mark: Wrote an SQL script that creates _albums_, _artists_ and _genres_ tables. Additionally, I created a junction table _album_genre_ to store each album genres.
  - _:file_folder: src/main/resources/creation.sql_

:heavy_check_mark: Updated pom.xml, in order to add the database driver to the project libraries.
  
:heavy_check_mark: Created a singleton class in order to manage a connection to the database.
  - _:file_folder: src/main/java/ro/db/Database.java_
 
 :heavy_check_mark: Created DAO classes that offer methods for managing artists, genres and albums.
  - _:card_index_dividers: src/main/java/ro/controllers_
  
## Homework Part ##

:heavy_check_mark: Created an object-oriented model of the data managed by the Java application.
  - _:card_index_dividers: src/main/java/ro/models_
  - _:card_index_dividers: src/main/java/ro/controllers_

:heavy_check_mark: Implement all the DAO classes.

:heavy_check_mark: Used Hikari connection pool in order to manage database connections.
  - _:file_folder: src/main/java/ro/db/ConnectionPool.java_
  - _:file_folder: src/main/java/ro/ConnectionPoolTest.java_
  - _:file_folder: src/main/java/ro/utils/ParallelInsert.java_

:heavy_check_mark: Created a tool to import data from a real datase.
  - _:file_folder: src/main/java/ro/population/DatabasePopulation.java_
  - _:file_folder: src/main/resources/albumlist.csv_

## Bonus Part ##

:heavy_check_mark: Extended the model in order to create _playlists_.
  - _:file_folder: src/main/java/ro/models/Playlist.java_
  - _:file_folder: src/main/java/ro/controllers/PlaylistController.java_ 

:heavy_check_mark: Created an algorithm that generates maximal playlists (a limited number, if there are too many) that contain only **unrelated** albums. 
Two albums are related if they are composed by the same artist, have been released in the same year or have at least one common genre.
  - _:file_folder: src/main/java/ro/utils/PlaylistBuilder.java_
