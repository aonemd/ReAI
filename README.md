Search Algorithms
---

A small React app with a Springboot API to compare between graph search algorithms:

##### Uninformed
- BFS: Breadth First Search
- DFS: Depth First Search
- IDS: Iterative Deepening First Search
- UCS: Uniform Cost Search
##### Informed
- ASS: A* Search

### Demo
![demo](https://user-images.githubusercontent.com/16504838/263450821-d866212a-b3b9-4a79-80fe-ac8fb92fcdb1.gif)

### Requirements
- Java 20 (search algorithms code)
- Kotlin 1.8 (Springboot API)
- Gradle 8
- Node 18 and yarn (React)

### Running
- backend: `./gradlew bootRun`
- frontend: `cd frontend && yarn && yarn dev`
- tests: `./gradlew test`
