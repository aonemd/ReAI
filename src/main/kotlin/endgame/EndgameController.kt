package endgame;

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("api/endgame")
class EndgameController {

  @GetMapping("/health")
  fun Health(): String {

    return "healthy"
  }
}
