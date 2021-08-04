package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;

import org.javatuples.Pair;

import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicAlly.Soldier;
import unsw.loopmania.entity.movingEntity.basicEnemy.Zombie;
import unsw.loopmania.util.PathPosition;

import org.json.JSONObject;

public class TestZombie {
    @Test
    public void testCritical() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        orderedPath.add(pair3);
        LoopManiaWorld world1 = new LoopManiaWorld(1000, 1000, orderedPath);
        LoopManiaWorld world2 = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(0, orderedPath));
        world1.setCharacter(character);
        world2.setCharacter(character);

        Zombie zombie1 = new Zombie(new PathPosition(1, orderedPath));

        Random r1 = new Random(1);
        Random r2 = new Random(1);
        Random r3 = new Random(1);
        Random r4 = new Random(1);

        Soldier s = new Soldier(null, -1, null);
        world1.addSoldier(s);
        zombie1.convertBite(s, world1, r1);

        if(r2.nextInt(10) < 3) {
            assertEquals(world1.getSoldierNum(), 0);
        }
        else {
            assertEquals(world1.getSoldierNum(), 1);
        }

        world2.addSoldier(s);
        zombie1.convertBite(s, world2, r3);

        if(r4.nextInt(10) < 3) {
            assertEquals(world2.getSoldierNum(), 0);
        }
        else {
            assertEquals(world2.getSoldierNum(), 1);
        }
    }

    @Test
    public void testSpecialAttack() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        orderedPath.add(pair3);
        LoopManiaWorld world1 = new LoopManiaWorld(1000, 1000, orderedPath);
        LoopManiaWorld world2 = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(0, orderedPath));
        world1.setCharacter(character);
        world2.setCharacter(character);

        Zombie zombie1 = new Zombie(new PathPosition(1, orderedPath));

        Random r1 = new Random(1);
        Random r2 = new Random(1);
        Random r3 = new Random(1);
        Random r4 = new Random(1);

        Soldier s = new Soldier(null, -1, null);
        world1.addSoldier(s);
        zombie1.specialAttack(s, world1, r1);

        if(r2.nextInt(10) < 3) {
            assertEquals(world1.getSoldierNum(), 0);
        }
        else {
            assertEquals(world1.getSoldierNum(), 1);
        }

        world2.addSoldier(s);
        zombie1.specialAttack(s, world2, r3);

        if(r4.nextInt(10) < 3) {
            assertEquals(world2.getSoldierNum(), 0);
        }
        else {
            assertEquals(world2.getSoldierNum(), 1);
        }
    }

    @Test
    public void testReward() {
        Zombie zombie = new Zombie(null);
        JSONObject obj = zombie.battleReward();
        assertEquals(obj.getInt("gold"), 500);
        assertEquals(obj.getInt("exp"), 500);
        assertEquals(obj.getInt("cardAmount"), 1);
        assertEquals(obj.getInt("equipmentAmount"), 0);
    }
}
