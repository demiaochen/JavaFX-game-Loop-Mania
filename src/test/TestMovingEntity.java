package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.MovingEntity;
import unsw.loopmania.util.PathPosition;

public class TestMovingEntity {
    @Test
    public void testBasic() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        MovingEntity entity = new MovingEntity(new PathPosition(0, orderedPath)){};
        world.addEntity(entity);

        assertEquals(1, entity.getX());
        assertEquals(2, entity.getY());

        // move clockwise then move anti-clockwise will back to original
        entity.moveDownPath();
        entity.moveUpPath();

        assertEquals(1, entity.getX());
        assertEquals(2, entity.getY());
    }
}
