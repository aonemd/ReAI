package endgame

import search.SearchTreeNode

data class EndgameDto(val path: Array<SearchTreeNode>, val score: Int, val numOfNodes: Int)
