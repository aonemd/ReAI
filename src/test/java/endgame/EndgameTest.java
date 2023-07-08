package endgame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndgameTest {
    @Test
    void justAnExample() {
        System.out.println("This test method should be run");
    }

    @Test
    public void testFinalCell() {
        String solution = Endgame.solve("2,2;0,0;0,0", "BF", false);

        assertEquals("snap;1", solution);
    }

    @Test
    public void testOneCellBeforeFinal() {
        String solution = Endgame.solve("2,2;0,0;0,1", "BF", false);

        assertEquals("right,snap;5", solution);
    }

    @Test
    public void testSimpleTestCase() {
        String solution = Endgame.solve("2,2;0,0;1,0;1,1;0,1", "BF", false);

        assertEquals("kill,right,down,collect,left,snap;13", solution);
    }

    @Test
    public void testGivenExample() {

        String solution = Endgame.solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3",
                "BF",
                false);

        assertEquals("up,collect,left,down,collect,down,collect,right,collect,kill,down,down,left,collect,left,collect,right,up,snap;63;52333",
                solution);
    }
}
