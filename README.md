SimSG - Backend
---

This repository contains the business logic of the smart grid simulator (SimSG). Should run along with the [WebUI](https://github.com/UL-SnT-Serval/creos.simSG.website).

## Features

- Load approximation
  - certain load approximation
  - uncertain load approximation
    - naive version: all versions are considered
    - efficient version: only versions that validate business rules
- circle finder
- JSON importer and exporter
- Connection through a web socket to the WebUI

## Project setup

### Requirements

- JDK 14 or later

### Build, run test

The project uses Maven for the project management. Uses the classic cycle goals to build or execute the test.

### Run the web server

- From your favaorite idea, run execute the main class in `RunServer`
- After packaging the project (`mvn package`): ` java -jar server/ws/target/creos.simsg.server.ws.jar`

## Project structure 

- `algorithms`: this directory contains all modules that implement an algorithm. **Note:** algorithms **SHOULD NOT** modify the model itself.
    - `circlefinder`: this module retrieves all possible circle in a topology
    - `extractor`: modules that contains algorithm to extract different elements (cables, entities, fuses, ...) of a grid
    - `loadapproximator`: contains different algorithms to approximate the load
        - `certain`: algorithm that do not consider fuse status confidence
        - `uncertain`: contains the different algorithms to handle uncertainty for the load approximation
            - `naive`: naive version that process all possible configurations for mono-substation topologies
            - `multisubs`: contains first steps/thoughts towards an algorithm for uncertain load approximation **WARNING**: none of the algorithms are tested. So they might contain bugs or be even completely wrong
            - `bsrules`: version that filter out invalid configurations for mono-substation topologies
    - `matrixbuilder`: contains different algorithms to generate the equation matrix
        - `certain`: algorithm that do not consider fuse status confidence
        - `uncertain`: algorithm that lists all possible configurations for mono-substation topologies
    - `navigation`: algorithms to navigate through the grid and collect different elements (fuses, entities, ...)
        - `bfs`: navigate the grid using a [BFS](https://en.wikipedia.org/wiki/Breadth-first_search) strategy
    - `powerflow`: algorithm that defines the power flow in a grid
    - `transformers`: contain different modules to transform the grid into another format or from another format to the grid
            - `json`: transform the grid from/into JSON object
    - `validator`: contains a set of validation algorithms for the model or the result of another algorithm.
- `model`: module that contains the smart grid model, should only contain an abstraction of the grid state
- `scenarios`: this module contains the implementation of the different pre-built topologies
- `server`: directories with a list of modules for the server
    - `ws`: module that contains a WebSocket server
- `simsg.uncertainty`: contains all uncertainty elements specific for the smart grid
- `uncertainty`: module that contains different utils to manipulate uncertain data, should be general and not smart grid specific
- `utils`

## Version schema

For this project, we use the [calendar versioning schema](https://calver.org/).

Schema used: ![](https://img.shields.io/badge/calver-YYYY.0M.0D-22bfda.svg)

- YYYY: full year  (*e.g.,* 2006, 2016, 2106)
- 0M: zero-padded month  (*e.g.,* 01, 02, 11, 12)
- 0D: zero-padded day (*e.g.,* 04, 08, 25, 31)

SimSG is a research prototype, mainly used by those who developed it. 
Its main goal is to be used as demonstration tool for academic conference or in front of (potential) partners.
The version number will not be used to inform users about new features or patches fix.
Instead, the goal was to use it as timestamp and refer them in report, academic papers, or any other written document.











