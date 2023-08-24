package endgame

import org.springframework.stereotype.Service
import search.SearchProblem
import search.strategy.SearchStrategy
import search.strategy.BFS

@Service
class EndgameService() {
  fun runSearch(grid: String, algo: String = "bfs"): EndgameDto {
    val problem = Endgame(grid)
    val searchStrategy: SearchStrategy = SearchProblem.searchStrategies.getOrDefault(algo, BFS())

    val goal = problem.search(searchStrategy)

    var nodes = emptyArray<NodeDto>()
    var current = goal
    while (current.parent() != null) {
      nodes += current.toNodeDto()

      current = current.parent()
    }
    nodes.reverse()

    val numOfNodes = problem.expandedNodesCount
    val score = goal.pathCost()

    return EndgameDto(nodes, score, numOfNodes, algo, problem.getGridWidth(), problem.getGridHeight())
  }
}
