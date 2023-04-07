# Advanced Programming 2023. Second Laboratory.
:adult: **Alexandru Neagu** from **2B2** (transferred from **2A6**).

This repository contains solutions for:

## Second Laboratory ##

:white_check_mark: **Homework** - **all bullets** completed

:white_check_mark: **Bonus** - **all bullets** completed

## Homework Part ##
:heavy_check_mark: A class that describes an instance of the _problem_: 

  - _:file_folder: src/main/java/com/problem/Problem.java_

:heavy_check_mark: Overriding _Object.equals_ method for _Location_ and _Road_ classes: 

  - _:file_folder: src/main/java/com/locations/Location.java_
  - _:file_folder: src/main/java/com/roads/Road.java_

:heavy_check_mark: Dedicated classes for **locations** (cities, air ports and gas stations) and **roads** (highway, express, country)
  - _:card_index_dividers: src/main/java/com/locations_
  - _:card_index_dividers: src/main/java/com/roads_

:heavy_check_mark: A method that determines if a problem's instance is valid 
  - _:file_folder: src/main/java/com/problem/algorithms/Algorithm.java_

:heavy_check_mark: An algorithm for determining if it's possible to go from one location to another using the given roads 
  - _:file_folder: src/main/java/com/problem/algorithms/DFSAlgorithm.java_
 
## Bonus Part ##
:heavy_check_mark: A class that describes the _solution_
  - _:file_folder: src/main/java/com/problem/algorithms/DijkstraSolution.java_

:heavy_check_mark: An algorithm that finds the shortest route from a location to another 
  - _:file_folder: src/main/java/com/problem/algorithms/DijkstraAlgorithm.java_

:heavy_check_mark: Generating large random instances of the problem and analyzing the performance of the algorithm
 - _:file_folder: src/main/java/com/testing/InstanceGenerator.java_
 - _:file_folder: src/main/java/com/Main.java_
 
 :heavy_plus_sign: Reading custom instances of the problem from a file 
 - _:file_folder: src/main/java/com/testing/InstanceReader.java_
