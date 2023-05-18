# Advanced Programming 2023. Ninth Laboratory.

:adult: **Alexandru Neagu** from **2B2** (transferred from **2A6**).

This repository contains solutions for:

## Seventh Laboratory ##

:white_check_mark: **Compulsory** - **all bullets** completed

:white_check_mark: **Homework** - **all bullets** completed

:white_check_mark: **Bonus** - **all bullets** completed

## Compulsory Part ##

:heavy_check_mark: Created a persistence unit (used Hibernate).
- _:file_folder: src/main/resources/META-INF/persistence.xml_

:heavy_check_mark: Defined the entity classes for the model (_Albums_, _Artists_ and _Genres_).
  - _:card_index_dividers: src/main/java/ro/entity_

:heavy_check_mark: Created a singleton responsible with the management of an _EntityManagerFactory_ object.
- _:file_folder: src/main/java/ro/repository/jpa/manager/EntityManagerSingleton.java_
  
:heavy_check_mark: Created a singleton class in order to manage a connection to the database.
  - _:file_folder: src/main/java/ro/db/Database.java_
 
 :heavy_check_mark: Defined repository clases for the entities.
  - _:card_index_dividers: src/main/java/ro/repository/jpa_
 
 :heavy_check_mark: Created Unit Tests for the application.
  - _:file_folder: src/test/java/ro/repository/interfaces/AlbumRepositoryTest.java_
  - _:file_folder: src/test/java/ro/repository/interfaces/ArtistRepositoryTest.java_

 
## Homework Part ##

:heavy_check_mark: Created all entity classes and repositories. Implemented properly the one-to-many and many-to-many relationships.
  - _:card_index_dividers: src/main/java/ro/entity_
  - _:card_index_dividers: src/main/java/ro/repository/jpa_

:heavy_check_mark: Created a generic _AbstractRepository_ using generics in order to simplify the creation of the repository classes.
  - _:file_folder: src/main/java/ro/repository/jpa/AbstractRepository.java_

:heavy_check_mark: Inserted a large number of fake artists and albums in the database and loged the execution time of the statements.
  - _:file_folder: src/main/java/ro/utils/InsertionUtils.java_
  
## Bonus Part ##

:heavy_check_mark: Implemented both the JDBC and JPA implementations and used an _AbstractFactory_ in order to create the the repositories.
  - _:card_index_dividers: src/main/java/ro/factory_

:heavy_check_mark: Used **Choco solver** constraint solver, in order to find a set of at least _k_ albums having titles that start with the same letter and they are released in the same period of time (for any two albums, the difference between their release years must not exceed a given value _p_).
  - _:card_index_dividers: src/main/java/ro/solver_
