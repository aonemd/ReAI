package endgame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.HashSet;

public class EndgameStateTest {
    @Test
    void testClonesShouldNotShareTheSameReferences() {
        var stonePositions = new HashSet<Cell>(Arrays.asList(new Cell(5, 2)));

        EndGameState s1 = new EndGameState(false, new Cell(1, 2), new Cell(2, 3), stonePositions, new HashSet<Cell>());
        EndGameState s2 = s1.clone();

        stonePositions.add(new Cell(1, 222));

        assertNotEquals(s1.stonePositions().size(), s2.stonePositions().size());
    }

    @Test
    void testEmptyStatesShouldBeEuqal() {
        EndGameState s1 = new EndGameState(false, null, null, null, null);
        EndGameState s2 = s1.toEmptyState();

        assertEquals(s1, s2, "empty states should be equal");
    }

    @Test
    void testEmptyStatesShouldHaveEqualHashCode() {
        EndGameState s1 = new EndGameState(false, null, null, null, null);
        EndGameState s2 = s1.toEmptyState();

        assertEquals(s1.hashCode(), s2.hashCode(), "empty states should have equal hash code");
    }
}
