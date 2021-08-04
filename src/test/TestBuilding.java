package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.entity.movingEntity.basicEnemy.Vampire;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.TowerBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.VampireCastleBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.VillageBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.ZombiePitBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingOnPath.BarracksBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingOnPath.TrapBuilding;
import unsw.loopmania.util.PathPosition;

public class TestBuilding {

    @Test
    public void testBarrackBuilding() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        Character character = new Character(new PathPosition(0, orderedPath));
    
        world.setCharacter(character);
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(2);

        BarracksBuilding barracks = new BarracksBuilding(x, y);

        barracks.effect(world);

        assertEquals(world.getSoldierNum(), 1);
    }

    @Test
    public void testCampfireBuilding() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
                
        Vampire vampire = new Vampire(new PathPosition(0, orderedPath));
        world.addEnemy(vampire);
        Character character = new Character(new PathPosition(0, orderedPath));
        
        character.doDamage(vampire, world, null);

        assertEquals(vampire.getHP(), 100);

    }

    @Test
    public void testTrapBuilding() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
    
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(2);
    
    
        TrapBuilding trap = new TrapBuilding(x, y);
        Vampire vampire = new Vampire(new PathPosition(0, orderedPath));
        world.addEnemy(vampire);
        assertEquals(1, world.getEnemies().size());
        trap.effect(world);

        assertEquals(0, world.getEnemies().size());

    }

    @Test
    public void testVillageCard() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(2);

        VillageBuilding village = new VillageBuilding(x, y);

        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);
        character.setHP(50);

        village.effect(world);

        assertEquals(250, character.getHP());

    }

    @Test
    public void testZombiePitCard() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        SimpleIntegerProperty x = new SimpleIntegerProperty(2);
        SimpleIntegerProperty y = new SimpleIntegerProperty(2);

        ZombiePitBuilding zombiePit = new ZombiePitBuilding(x, y);

        world.addEnemy(zombiePit.spawnEnemy(orderedPath, 1, true));

        assertEquals(world.getEnemies().size(), 1);

        assertEquals(null, zombiePit.spawnEnemy(orderedPath, 1, false));


    }

    @Test
    public void testVampireCastleCard() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        SimpleIntegerProperty x = new SimpleIntegerProperty(2);
        SimpleIntegerProperty y = new SimpleIntegerProperty(2);

        VampireCastleBuilding vampireCastle = new VampireCastleBuilding(x, y);

        world.addEnemy(vampireCastle.spawnEnemy(orderedPath, 5, true));
        assertEquals(1, world.getEnemies().size());

        assertEquals(null, vampireCastle.spawnEnemy(orderedPath, 1, true));
        assertEquals(null, vampireCastle.spawnEnemy(orderedPath, 5, false));
    }
    
    @Test
    public void testTower() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(2);

        TowerBuilding tower = new TowerBuilding(x, y);

        Slug slug = new Slug(new PathPosition(1, orderedPath));
        tower.doDamage(slug);

        assertEquals(50, slug.getHP());
    }
}
