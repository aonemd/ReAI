package endgame;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EndGameTest {
    @Test
    public void testFinalCell() {
        String solution = EndGame.solve("2,2;0,0;0,0", "BF", false);

        assertEquals("snap;1", solution);
    }

    @Test
    public void testOneCellBeforeFinal() {
        String solution = EndGame.solve("2,2;0,0;0,1", "BF", false);

        assertEquals("right,snap;5", solution);
    }

    @Test
    public void testSimpleTestCase() {
        String solution = EndGame.solve("2,2;0,0;1,0;1,1;0,1", "BF", false);

        assertEquals("kill,right,down,collect,left,snap;13", solution);
    }

    @Test
    public void testGivenExample() {

        String solution = EndGame.solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3",
                "BF",
                false);

        assertEquals("up,collect,left,down,collect,down,collect,right,collect,kill,down,down,left,collect,left,collect,right,up,snap;63;52333",
                solution);
    }
}
