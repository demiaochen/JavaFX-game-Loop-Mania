package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicEnemy.Doggie;
import unsw.loopmania.util.PathPosition;

public class TestDoggie {
    @Test
    public void testMovement() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(0, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        orderedPath.add(pair3);
        Doggie doggie = new Doggie(new PathPosition(0, orderedPath));
        LoopManiaWorld world = new LoopManiaWorld(1, 1, orderedPath);
        doggie.move(world);
        assertEquals(doggie.getX(), 0);
        assertEquals(doggie.getY(), 1);
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
        world.setCharacter(character);
        Doggie doggie = new Doggie(new PathPosition(0, orderedPath));
        world.addEnemy(doggie);
        doggie.specialAttack(character, world, new Random());
        assertEquals(3, character.getStunnedRound());
    }

    @Test
    public void testReward() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        Doggie doggie = new Doggie(new PathPosition(0, orderedPath));
        JSONObject reward = doggie.battleReward();
        assertEquals(1000, reward.getInt("gold"));
        assertEquals(1000, reward.getInt("exp"));
        assertEquals(1, reward.getInt("cardAmount"));
        assertEquals(1, reward.getInt("equipmentAmount"));
        // randomly generates 1~10 coins, check the amount if is within the range
        assertTrue(reward.getInt("doggieCoin") > 0);
        assertTrue(reward.getInt("doggieCoin") <= 10);
    }
}
