package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicEnemy.ElanMuske;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.util.PathPosition;

public class TestElan {
    @Test
    public void testMovement() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(0, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        orderedPath.add(pair3);
        ElanMuske elan = new ElanMuske(new PathPosition(0, orderedPath));
        LoopManiaWorld world = new LoopManiaWorld(1, 1, orderedPath);
        elan.move(world);
        assertEquals(elan.getX(), 0);
        assertEquals(elan.getY(), 1);
    }

    @Test
    public void testSpecialAttack() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        Character character = new Character(new PathPosition(0, orderedPath));
        Slug slug1 = new Slug(new PathPosition(0, orderedPath));
        Slug slug2 = new Slug(new PathPosition(0, orderedPath));
        ElanMuske elan = new ElanMuske(new PathPosition(1, orderedPath));
        world.addEnemy(slug1);
        world.addEnemy(slug2);
        world.addEnemy(elan);
        slug1.takeDamage(100);
        slug2.takeDamage(50);
        elan.specialAttack(character, world, new Random());
        assertEquals(150, slug1.getHP());
        assertEquals(150, slug2.getHP());
    }

    @Test
    public void testReward() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        ElanMuske elan = new ElanMuske(new PathPosition(0, orderedPath));
        JSONObject reward = elan.battleReward();
        assertEquals(3000, reward.getInt("gold"));
        assertEquals(3000, reward.getInt("exp"));
        assertEquals(1, reward.getInt("cardAmount"));
        assertEquals(1, reward.getInt("equipmentAmount"));
    }
}
