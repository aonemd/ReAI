package endgame

import org.springframework.stereotype.Service
import search.SearchTreeNode
import search.strategy.SearchStrategy
import search.strategy.BFS

@Service
class EndgameService() {
  fun runSearch(grid: String, algo: SearchStrategy = BFS()): EndgameDto {
    val problem = Endgame(grid)

    val goal = problem.search(algo)

    var nodes = emptyArray<SearchTreeNode>()
    var current = goal
    while (current.parent() != null) {
      nodes += current

      current = current.parent()
    }
    nodes.reverse()

    val numOfNodes = problem.expandedNodesCount
    val score = goal.pathCost()

    return EndgameDto(nodes, score, numOfNodes)
  }
}
