package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.entity.staticEntity.building.Building;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.VillageBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingNotOnPath.CampfireBuilding;
import unsw.loopmania.entity.staticEntity.card.Card;
import unsw.loopmania.entity.staticEntity.card.ZombiePitCard;
import unsw.loopmania.entity.staticEntity.equipment.Armour;
import unsw.loopmania.entity.staticEntity.equipment.Sword;
import unsw.loopmania.entity.staticEntity.staticEntityOnPath.StaticEntityOnPath;
import unsw.loopmania.util.PathPosition;

public class TestLoopManiaWorld {
    @Test
    public void testAddUnequippedEquip() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        // add equipment 20 times
        for(int i = 0; i < 20; i++) {
            world.addUnequippedEquip();
        }

        // maximum slots of backpack is 16
        assertEquals(16, world.getUnequipped().size());
    }

    @Test
    public void testPossiblySpawnEntity() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        for(int i = 0; i < 20; i++) {
            List<StaticEntityOnPath> entities =  world.possiblySpawnEntity();
            assertTrue(entities.size() >= 0 && entities.size() <= 1);
        }
    }

    @Test
    public void testCompleteLoop() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        assertTrue(world.completeLoop());

        // leave original location
        character.moveDownPath();
        assertFalse(world.completeLoop());
    }

    @Test
    public void testConvertCardToBuildingByCoordinates() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Card card;
        while(true) {
            // make sure the card is zombie pit
            Card temp = world.loadCard();
            if(temp instanceof ZombiePitCard) {
                card = temp;
                break;
            }
        }
        Building building = world.convertCardToBuildingByCoordinates(card.getX(), card.getY(), 1, 1);

        assertEquals(building.getX(), 1);
        assertEquals(building.getY(), 1);
    }

    @Test
    public void testPossiblySpawnEnemies() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        List<BasicEnemy> enemies = world.possiblySpawnEnemies();

        assertEquals(world.getEnemies().size(), enemies.size());
    }

    @Test
    public void testRunTickMoves() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        // 70 steps, 35 loops
        for(int i = 0; i < 70; i++) {
            world.runTickMoves();
        }

        assertEquals(35, world.getLoop());
    }

    @Test
    public void testIsReachGoal() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        world.addExp(1);

        world.setGoal(50, 100, true, "OR", "OR");
        for(int i = 0; i < 70; i++) {
            world.runTickMoves();
        }

        assertFalse(world.isReachGoal());

        for(int i = 0; i < 30; i++) {
            world.runTickMoves();
        }

        assertTrue(world.isReachGoal());

        world.setGoal(50, 100, true, "OR", "AND");
        assertTrue(world.isReachGoal());

        world.setGoal(50, 100, true, "AND", "AND");
        assertFalse(world.isReachGoal());

        world.setGoal(50, 100, true, "AND", "OR");
        assertFalse(world.isReachGoal());
    }

    @Test
    public void testWithinCampfireRadius() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);

        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        CampfireBuilding campfire = new CampfireBuilding(x, y);
        world.addBuilding(campfire);

        assertTrue(world.withinCampfireRadius(character));
    }

    @Test
    public void testLoadCardAndDiscardCard() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(8, 1000, orderedPath);

        for(int i = 0; i < 10; i++) {
            world.loadCard();
        }

        assertEquals(8, world.getCardEntities().size());
    }

    @Test
    public void testUsePotion() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(8, 1000, orderedPath);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        world.addHealthPotion();
        character.setHP(500);
        world.usePotion();

        assertEquals(1000, character.getHP());
    }

    @Test
    public void testIsItemProtectiveGear() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(8, 1000, orderedPath);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        SimpleIntegerProperty x2 = new SimpleIntegerProperty(2);
        SimpleIntegerProperty y2 = new SimpleIntegerProperty(2);

        Armour armour = new Armour(x, y);
        Sword sword = new Sword(x2, y2);

        assertTrue(world.isItemProtectiveGear(armour));
        assertFalse(world.isItemProtectiveGear(sword));
    }

    @Test
    public void testPassThroughBuilding() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(8, 1000, orderedPath);
        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);

        VillageBuilding village = new VillageBuilding(x, y);
        village.effect(world);
        assertEquals(1000, character.getHP());
    }

    @Test
    public void testKillAllEnemies() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(8, 1000, orderedPath);
        for(int i = 0; i < 5; i++) {
            Slug slug = new Slug(new PathPosition(0, orderedPath));
            world.addEnemy(slug);
        }

        assertEquals(5, world.killAllNonBossEnemy().size());
    }

    @Test
    public void testRemoveUnequippedInventoryItemByCoordinates() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(8, 1000, orderedPath);
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        Sword s = new Sword(x, y);
        world.addToUnequipped(s);
        world.removeUnequippedInventoryItemByCoordinates(0, 0);
        assertEquals(0, world.getUnequipped().size());
    }
}
