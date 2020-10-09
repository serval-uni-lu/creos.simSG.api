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




