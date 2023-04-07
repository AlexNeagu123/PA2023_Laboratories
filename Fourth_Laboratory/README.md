# Advanced Programming 2023. Fourth Laboratory.

:adult: **Alexandru Neagu** from **2B2** (transferred from **2A6**).

This repository contains solutions for:

## Fourth Laboratory ##

:white_check_mark: **Compulsory** - **all bullets** completed

:white_check_mark: **Homework** - **all bullets** completed

:white_check_mark: **Bonus** - **all bullets** completed

## Compulsory Part ##

:heavy_check_mark: Created a Maven project
  - _:file_folder: pom.xml_

:heavy_check_mark: Created an object-oriented model of the problem. Students and projects have names and are both comparable.
  - _:card_index_dividers: src/main/java/com/graph_
  
:heavy_check_mark: Created the student and the project objects. Used streams in order to easily create the objects.
  - _:file_folder: src/main/java/com/generators/MatchingProblemGenerator.java_

:heavy_check_mark: Printed the students and the projects ordered by names.
  - _:file_folder: src/main/java/com/problem/MatchingProblem.java_
  - _:page_with_curl: getEntitiesSortedByNames() method_
  
## Homework Part ##

:heavy_check_mark: Created a class that describes the problem.
  - _:file_folder: src/main/java/com/problem/MatchingProblem.java_

:heavy_check_mark: Used Java Stream API to write a query that display all the students that have a number of preferences lower than the average number of preferences.
  - _:file_folder: src/main/java/com/problem/MatchingProblem.java_
  - _:page_with_curl: printSpecialStudents() method_

:heavy_check_mark: Used Faker in order to generate random fake names for students and projects.
  - _:file_folder: src/main/java/com/graph_
  -  _:page_with_curl: In class constructors_

:heavy_check_mark: Created a Greedy algorithm in order to compute the maximum cardinality of a bipartite matching.
  - _:file_folder: src/main/java/com/algorithms/GreedyMaximumMatching.java_

## Bonus Part ##

:heavy_check_mark: Used the JGraphT Library to compute the maximum cardinality of a bipartite matching.
  - _:file_folder: src/main/java/com/algorithms/JgraphtMaximumMatching.java_

:heavy_check_mark: Used the Graph4J Library to compute the maximum cardinality of a bipartite matching..
  - _:file_folder: src/main/java/com/algorithms/Graph4jMaximumMatching.java_

:heavy_check_mark: Created a random problem generator (with thousands of objects) and tested the performance of both libraries.
  - _:file_folder: src/main/java/com/generators/MatchingProblemGenerator.java_
  - _:file_folder: src/main/java/com/Main.java_

:heavy_check_mark: Computed a minimum cardinality set formed of students and projects with the property that each admissible pair (student-project) contains at least an element of this set (**minimum vertex cover**).
  - _:file_folder: src/main/java/com/algorithms/MinimumVertexCover.java_

:heavy_check_mark: Computed a maximum cardinality set of of students and projects such that there is no admissible pair (student-project) formed with elements of this set (**maximum independent set**).
  - _:file_folder: src/main/java/com/algorithms/MaximumIndependentSet.java_

 :heavy_plus_sign: Created some JUnit tests for the algorithms that compute the **Minimum Vertex Cover** and **Maximum Independent Set**. 
 - _:file_folder: src/test/java/com/algorithms/MaximumIndependentSetTest.java_
 - _:file_folder: src/test/java/com/algorithms/MinimumVertexCoverTest.java_
