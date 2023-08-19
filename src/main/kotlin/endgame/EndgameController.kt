package endgame

import kotlin.arrayOf
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import search.SearchTreeNode
import search.strategy.BFS

@RestController
@RequestMapping("api/endgame")
class EndgameController(private val endgameService: EndgameService) {

  @GetMapping("/health")
  fun Health(): String {

    return "healthy"
  }

  @GetMapping("/search", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
  fun Search(@RequestParam grid: String, @RequestParam algo: String): ResponseEntity<EndgameDto> {
    val dto = endgameService.runSearch(grid)

    return ResponseEntity.status(HttpStatus.OK).body(dto)
  }
}
