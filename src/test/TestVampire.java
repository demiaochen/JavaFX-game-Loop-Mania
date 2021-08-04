package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicEnemy.Vampire;
import unsw.loopmania.util.PathPosition;

import org.javatuples.Pair;
import org.json.JSONObject;

public class TestVampire {
    @Test
    public void testBasic() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        Vampire vampire1 = new Vampire(new PathPosition(0, orderedPath));
        Vampire vampire2 = new Vampire(new PathPosition(1, orderedPath));
        assertEquals(vampire1.getBattleRadius(), vampire2.getBattleRadius());
        assertEquals(vampire1.getSupportRadius(), vampire2.getSupportRadius());
        assertEquals(vampire1.getHP(), vampire2.getHP());
        assertEquals(vampire1.getAttack(), vampire2.getAttack());
    }

    @Test
    public void testMovement() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(0, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        orderedPath.add(pair3);
        Vampire vampire = new Vampire(new PathPosition(0, orderedPath));
        LoopManiaWorld world = new LoopManiaWorld(1, 1, orderedPath);
        vampire.move(world);
        assertEquals(vampire.getX(), 0);
        assertEquals(vampire.getY(), 1);
    }
    @Test
    public void testAttack() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        Character character = new Character(new PathPosition(0, orderedPath));
        Vampire vampire = new Vampire(new PathPosition(0, orderedPath));
        vampire.doDamage(character);
        assertTrue(character.getHP() < 1000);
    }

    @Test
    public void testCritical() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        orderedPath.add(pair3);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(0, orderedPath));
        Character character2 = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        Vampire vampire1 = new Vampire(new PathPosition(1, orderedPath));
        Vampire vampire2 = new Vampire(new PathPosition(1, orderedPath));

        Random r1 = new Random(1);
        Random r2 = new Random(1);
        Random r3 = new Random(1);
        Random r4 = new Random(1);

        vampire1.criticalBite(character, r1);
        
        r2.nextInt(5);
        r2.nextInt(5);
        vampire1.doDamage(character);
        assertEquals(character.getHP(), character.getMaxHP() - (vampire1.getAttack() + r2.nextInt(100)));

        vampire2.specialAttack(character, world, r3);
        
        r4.nextInt(5);
        r4.nextInt(5);
        vampire2.doDamage(character2);
        assertEquals(character2.getHP(), character2.getMaxHP() - (vampire2.getAttack() + r4.nextInt(100)));
    }

    @Test
    public void testReward() {
        Vampire vampire = new Vampire(null);
        JSONObject obj = vampire.battleReward();
        assertEquals(obj.getInt("gold"), 1000);
        assertEquals(obj.getInt("exp"), 500);
    }
}
