package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


import unsw.loopmania.entity.staticEntity.equipment.*;
import unsw.loopmania.LoopManiaWorld;

import org.javatuples.Pair;
import java.util.ArrayList;
import java.util.List;

public class TestConfusingMode {
    @Test
    public void testConfusingRing() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        world.setupMode("confusing");

        RareItem theOneRing = new TheOneRing(null, null);
        world.addEntity(theOneRing);

        assertEquals(true, theOneRing.shouldExist().getValue());
    }

    @Test
    public void testRareEquipment() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        world.setupMode("confusing");

        RareItem anduril = new Anduril(null, null);
        world.addEntity(anduril);
        assertEquals(true, anduril.shouldExist().getValue());

        RareItem treeStump = new TreeStump(null, null);
        world.addEntity(treeStump);
        assertEquals(true, treeStump.shouldExist().getValue());
    }
}

