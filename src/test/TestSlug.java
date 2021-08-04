package test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.util.PathPosition;

import org.javatuples.Pair;
import org.json.JSONObject;

public class TestSlug {
    @Test
    public void testMovement() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(0, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        orderedPath.add(pair3);
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        LoopManiaWorld world = new LoopManiaWorld(1, 1, orderedPath);
        slug.move(world);
        assertEquals(slug.getX(), 0);
        assertEquals(slug.getY(), 1);
    }

    @Test
    public void testSpecialAttack() {
        Slug slug = new Slug(null);
        slug.specialAttack(null, null, new Random());
        assertEquals(slug.getHP(), 150);
    }

    @Test
    public void testReward() {
        Slug slug = new Slug(null);
        JSONObject obj = slug.battleReward();
        assertEquals(obj.getInt("gold"), 300);
        assertEquals(obj.getInt("exp"), 100);
    }
}
