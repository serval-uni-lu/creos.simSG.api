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

- From your favaorite idea, run execute the main class in `duc.sg.java.server.RunServer`
- After packaging the project (`mvn package`): ` java -jar server/ws/target/creos.simsg.server.ws.jar`

## Project structure 

- `model`: module that contains the smart grid model, should only contain an abstraction of the grid state
- `simsg.uncertainty`: contains all uncertainty elements specific for the smart grid
- `uncertainty`: module that contains different utils to manipulate uncertain data, should be general and not smart grid specific
- `utils`

to delete

- `algorithms`: folder that contains different module to analyse the grid and save the result into the model that can be used by other algorithms
    - `cyle`: folder that contain different modules to find circles in a grid
    - `importer`: contain the different algorithms to import grid from different format
        - `json`: import data from Json files
    - `powerflow`: ...
    - `validator`: module that contains algorithms to validate a grid
- `loadapproximator`: contain different algorithms to approximate the load
    - `certain`: algorithm that do not consider fuse uncertainty
    - `uncertainty.naive`: naive  version that process all possibilities
    - `uncertain.onlyvalid`: version that approximate the load only on the valid configurations
- `scenarios`: contain a module that stores different predefined module











