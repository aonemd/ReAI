package endgame;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EndGameTest {
    @Test
    public void testHelloWorld() {

        String solution = EndGame.solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3",
                "BF",
                false);

        assertEquals("up,collect,left,down,collect,down,collect,right,collect,kill,down,down,left,collect,left,collect,right,up,snap;63;52333",
                solution);
    }
}
