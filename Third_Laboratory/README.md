# Advanced Programming 2023. Third Laboratory.

:adult: **Alexandru Neagu** from **2B2** (transferred from **2A6**).

This repository contains solutions for:

## Third Laboratory ##

:white_check_mark: **Homework** - **all bullets** completed

:white_check_mark: **Bonus** - **all bullets** completed

## Homework Part ##

:heavy_check_mark: Created a complete model: _Person_, _Programmer_, _Designer_, _Company_. All persons have a birth date and each class has one specific property.
  - _:card_index_dividers: src/main/java/com/entities_

:heavy_check_mark: Each _Person_ contains a Map that defines the relationships to other persons or companies.
  - _:file_folder: src/main/java/com/entities/Person.java_
  
:heavy_check_mark: Created the _Network_ class that contains a _List_ of identifiable nodes.
  - _:file_folder: src/main/java/com/graph/Network.java_

:heavy_check_mark: Created a method that computes the importance of a node in the network, as the number of its connections to other nodes.
  - _:file_folder: src/main/java/com/graph/Network.java_
  
:heavy_check_mark: Created a _Network_ object containing persons, companies and relationships and printed it on the screen. All the nodes are printed in the increasing order of importance.
  - _:file_folder: src/main/java/com/Main.java_

## Bonus Part ##

:heavy_check_mark: Implemented an efficient algorithm to determine if there are nodes in this networks which, if they are removed, disconnect the network.
  - _:file_folder: src/main/java/com/graph/TarjanAlgorithm.java_

:heavy_check_mark: Identified maximal 2-connected blocks of the network (biconnected components).
  - _:file_folder: src/main/java/com/graph/TarjanAlgorithm.java_

:heavy_check_mark: Created JUnit tests for these algorithms.
  - _:file_folder: src/test/java/com/graph/TarjanAlgorithmTest.java_
