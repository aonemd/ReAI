package endgame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndgameTest {
    @Test
    void justAnExample() {
        System.out.println("This test method should be run");
    }

    @Test
    public void testFinalCell() {
        String solution = Endgame.solve("2,2;0,0;0,0", "BF", false);

        assertTrue(solution.contains("SNAP;0;1"));
    }

    @Test
    public void testOneCellBeforeFinal() {
        String solution = Endgame.solve("2,2;0,0;0,1", "BF", false);

        assertTrue(solution.contains("RIGHT,SNAP"));
    }

    @Test
    public void testSimpleTestCase() {
        String solution = Endgame.solve("2,2;0,0;1,0;1,1;0,1", "BF", false);

        assertTrue(solution.contains("KILL,RIGHT,DOWN,COLLECT,LEFT,SNAP"));
    }
}
