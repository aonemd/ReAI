package endgame

import search.Operator
import search.SearchTreeNode

class OperatorDto(val name: String)

fun Operator.toOperatorDto() = OperatorDto(name = name)

class EndgameStateDto(
    val snapped: Boolean,
    val ironManPosition: Cell,
    val thanosPosition: Cell,
    val stonePositions: HashSet<Cell>,
    val warriorPositions: HashSet<Cell>
)

fun EndGameState.toEndgameStateDto() =
    EndgameStateDto(
        snapped = snapped(),
        ironManPosition = ironManPosition(),
        thanosPosition = thanosPosition(),
        stonePositions = stonePositions(),
        warriorPositions = warriorPositions(),
    )

class NodeDto(val state: EndgameStateDto, val operator: OperatorDto, val pathCost: Int)

fun SearchTreeNode.toNodeDto() =
    NodeDto(
        state = (state() as EndGameState).toEndgameStateDto(),
        operator = operator().toOperatorDto(),
        pathCost = pathCost
    )

data class EndgameDto(val path: Array<NodeDto>, val score: Int, val numOfNodes: Int, val algo : String, val gridWidth: Int, val gridHeight: Int)
