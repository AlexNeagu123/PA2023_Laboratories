# Advanced Programming 2023. Fifth Laboratory.

:adult: **Alexandru Neagu** from **2B2** (transferred from **2A6**).

This repository contains solutions for:

## Fifth Laboratory ##

:white_check_mark: **Compulsory** - **all bullets** completed

:white_check_mark: **Homework** - **all bullets** completed

:white_check_mark: **Bonus** - **all bullets** completed

## Compulsory Part ##

:heavy_check_mark: Created an object-oriented model of the problem. Implemented the following classes: **Catalog**, **Document**. Created a class responsible with external operations regarding a catalog.
  - _:card_index_dividers: src/main/java/com/entities_
  - _:file_folder: src/main/java/com/utils/CatalogUtils.java_

:heavy_check_mark: Implemented the **add**, **toString**, **save** and **load** commands.
  - _:file_folder: src/main/java/com/utils/CatalogUtils.java_
    
## Homework Part ##

:heavy_check_mark: Represented the commands using classes instead of methods.
  - _:card_index_dividers: src/main/java/com/commands_

:heavy_check_mark: Implemented the _load_, _list_, _view_, _report_ commands.
  - _:card_index_dividers: src/main/java/com/commands_
  - _:file_folder: src/main/java/com/utils/CatalogUtils.java_

:heavy_check_mark: Used FreeMarker template engine, in order to create a HTML report (for _report_ command).
  - _:file_folder: src/main/java/templates/template.ftl_
  
:heavy_check_mark: Signaled invalid data or the commands that are not valid using custom exceptions.
  - _:card_index_dividers: src/main/java/com/exceptions_

:heavy_check_mark: Created an executable JAR archive.
  - _:file_folder: out/artifacts/Fifth_Week_jar/Fifth_Week.jar_
  
## Bonus Part ##

:heavy_check_mark: Used **Apache Tika** in order to extract metadata from the catalog items and implemented the command _info_ in order to display them.
  - _:file_folder: src/main/java/com/commands/InfoCommand.java_

:heavy_check_mark: Implemented the **Brown Coloring Algorithm** using JgraphT API.
  - _:file_folder: src/main/java/com/problem/JgraphTBrownColoring.java_

:heavy_check_mark: Implemented a **Greedy Algorithm** to solve the **Graph Coloring Problem**.
  - _:file_folder: src/main/java/com/problem/GreedyColoring.java_

:heavy_check_mark: Created large instances of the problem and compared the algorithms.
  - _:file_folder: src/main/java/com/generators/ColoringProblemGenerator.java_
