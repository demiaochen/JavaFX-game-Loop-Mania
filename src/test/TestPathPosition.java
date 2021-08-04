package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import unsw.loopmania.util.PathPosition;

public class TestPathPosition {
    @Test
    public void testBasic() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        PathPosition position = new PathPosition(0, orderedPath);

        // move clockwise then move anti-clockwise will back to original
        position.moveDownPath();
        position.moveUpPath();

        assertEquals(1, position.getX().get());
        assertEquals(2, position.getY().get());
    }
}
