package endgame;

import org.junit.jupiter.api.Test;

import search.strategy.BFS;
import search.strategy.SearchStrategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndgameTest {
    private SearchStrategy searchStrategy = new BFS();

    @Test
    void justAnExample() {
        System.out.println("This test method should be run");
    }

    @Test
    public void testFinalCell() {
        String solution = new Endgame().solve("2,2;0,0;0,0", searchStrategy, false);

        assertTrue(solution.contains("SNAP;0;"));
    }

    @Test
    public void testOneCellBeforeFinal() {
        String solution = new Endgame().solve("2,2;0,0;0,1", searchStrategy, false);

        assertTrue(solution.contains("RIGHT,SNAP"));
    }

    @Test
    public void testSimpleTestCase() {
        String solution = new Endgame().solve("2,2;0,0;1,0;1,1;0,1", searchStrategy, false);

        assertTrue(solution.contains("KILL,RIGHT,DOWN,COLLECT,LEFT,SNAP"));
    }
}
