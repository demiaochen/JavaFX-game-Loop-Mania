package test;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.javatuples.Pair;

import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicAlly.Soldier;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.util.PathPosition;
import unsw.loopmania.LoopManiaWorld;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestRunBattle {
    @Test
    public void noEnemies() {
        
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        assertEquals(world.getCharHP(),  1000);
        assertTrue(world.getEnemies().isEmpty());
        world.runBattles();
        assertEquals(world.getCharHP(),  1000);
        assertTrue(world.getEnemies().isEmpty());
        
    }

    @Test
    public void threeSlugsInRange() {
        
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(2, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(2, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        assertTrue(world.getEnemies().isEmpty());

        Slug slug1 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug1);
        Slug slug2 = new Slug(new PathPosition(1, orderedPath));
        world.addEnemy(slug2);
        Slug slug3 = new Slug(new PathPosition(1, orderedPath));
        world.addEnemy(slug3);

        // before battle
        assertTrue(world.getEnemies().size() == 3);
        assertEquals(world.getCharHP(),  1000);

        List<BasicEnemy> battleEnemies =  world.runBattles();
        assertEquals(battleEnemies.size(), 3);

        // after battle
        assertTrue(world.getEnemies().size() == 0);
        assertEquals(world.getCharHP(),  550); // 150 + 100*2 + 50*2 damage taken 

    }

    @Test
    public void allOutOfRange() {
        
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(7, 7);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(2, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(1, orderedPath));
        world.setCharacter(character);

        assertTrue(world.getEnemies().isEmpty());

        Slug slug1 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug1);
        Slug slug2 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug2);
        Slug slug3 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug3);

        // before battle
        assertTrue(world.getEnemies().size() == 3);
        assertEquals(world.getCharHP(),  1000);

        List<BasicEnemy> battleEnemies =  world.runBattles();
        assertEquals(battleEnemies.size(), 0);

        // after battle
        assertTrue(world.getEnemies().size() == 3);
        assertEquals(world.getCharHP(),  1000);

    }


    @Test
    public void partialInRange() {
        
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(7, 7);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(2, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(1, orderedPath));
        world.setCharacter(character);

        assertTrue(world.getEnemies().isEmpty());

        Slug slug1 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug1);
        Slug slug2 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug2);
        Slug slug3 = new Slug(new PathPosition(1, orderedPath));
        world.addEnemy(slug3);

        // before battle
        assertTrue(world.getEnemies().size() == 3);
        assertEquals(world.getCharHP(),  1000);

        List<BasicEnemy> battleEnemies =  world.runBattles();
        assertEquals(battleEnemies.size(), 1);

        // after battle
        assertTrue(world.getEnemies().size() == 2);
        assertEquals(world.getCharHP(),  950);
    }


    @Test
    public void oneSoldierOneSlug() {
        
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(2, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(2, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(1, orderedPath));
        world.setCharacter(character);

        assertTrue(world.getEnemies().isEmpty());

        Soldier soldier = new Soldier(new PathPosition(0, orderedPath), -1, null);
        world.addSoldier(soldier);
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug);


        // before battle
        assertTrue(world.getEnemies().size() == 1);
        assertEquals(world.getCharHP(),  1000);
        assertTrue(world.getSoldiers().size() == 1);

        List<BasicEnemy> battleEnemies =  world.runBattles();
        assertEquals(battleEnemies.size(), 1);

        // after battle
        assertTrue(world.getSoldiers().size() == 1);
        assertTrue(world.getEnemies().size() == 0);
        assertEquals(world.getCharHP(),  1000);
    }

    @Test
    public void oneSoldierThreeSlugs() {
        
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(2, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(2, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(1, orderedPath));
        world.setCharacter(character);

        assertTrue(world.getEnemies().isEmpty());

        Soldier soldier = new Soldier(new PathPosition(0, orderedPath), -1, null);
        world.addSoldier(soldier);
        Slug slug1 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug1);
        Slug slug2 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug2);
        Slug slug3 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug3);


        // before battle
        assertTrue(world.getEnemies().size() == 3);
        assertEquals(world.getCharHP(),  1000);
        assertTrue(world.getSoldiers().size() == 1);

        List<BasicEnemy> battleEnemies =  world.runBattles();
        assertEquals(battleEnemies.size(), 3);

        // after battle
        assertTrue(world.getSoldiers().size() == 1);
        assertTrue(world.getEnemies().size() == 0);
        assertEquals(world.getCharHP(),  1000);
    }

    @Test
    public void oneSoldierFiveSlugs() {
        
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(2, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(2, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(1, orderedPath));
        world.setCharacter(character);

        assertTrue(world.getEnemies().isEmpty());

        Soldier soldier = new Soldier(new PathPosition(0, orderedPath), -1, null);
        world.addSoldier(soldier);
        Slug slug1 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug1);
        Slug slug2 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug2);
        Slug slug3 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug3);
        Slug slug4 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug4);
        Slug slug5 = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(slug5);


        // before battle
        assertTrue(world.getEnemies().size() == 5);
        assertEquals(world.getCharHP(),  1000);
        assertTrue(world.getSoldiers().size() == 1);

        List<BasicEnemy> battleEnemies =  world.runBattles();
        assertEquals(battleEnemies.size(), 5);

        // after battle
        assertTrue(world.getSoldiers().size() == 0);
        assertTrue(world.getEnemies().size() == 0);
        assertEquals(world.getCharHP(),  200);  // do math here
    }

}
