package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.staticEntity.card.BarracksCard;
import unsw.loopmania.entity.staticEntity.card.CampfireCard;
import unsw.loopmania.entity.staticEntity.card.TrapCard;
import unsw.loopmania.entity.staticEntity.card.VampireCastleCard;
import unsw.loopmania.entity.staticEntity.card.VillageCard;
import unsw.loopmania.entity.staticEntity.card.ZombiePitCard;
import unsw.loopmania.entity.staticEntity.card.Card;
import unsw.loopmania.entity.staticEntity.card.TowerCard;

public class TestCard {

    @Test
    public void testCard() {
    Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
    Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
    List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
    orderedPath.add(pair1);
    orderedPath.add(pair2);
    LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

    SimpleIntegerProperty x = new SimpleIntegerProperty(2);
    SimpleIntegerProperty y = new SimpleIntegerProperty(3);

    Card card = world.loadCard();

    world.addBuilding(card.createBuilding(x, y));

    assertEquals(1, world.getBuildings().size());
    
    }

    @Test
    public void testBarrackCard() {
    Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
    Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
    List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
    orderedPath.add(pair1);
    orderedPath.add(pair2);
    LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

    SimpleIntegerProperty x = new SimpleIntegerProperty(1);
    SimpleIntegerProperty y = new SimpleIntegerProperty(2);

    BarracksCard card = new BarracksCard(x, y);
    world.addBuilding(card.createBuilding(x, y));
    assertEquals(1, world.getBuildings().size());
    }

    @Test
    public void testCampfireCard() {
    Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
    Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
    List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
    orderedPath.add(pair1);
    orderedPath.add(pair2);
    LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

    SimpleIntegerProperty x = new SimpleIntegerProperty(2);
    SimpleIntegerProperty y = new SimpleIntegerProperty(3);

    CampfireCard card = new CampfireCard(x, y);
    world.addBuilding(card.createBuilding(x, y));

    assertEquals(1, world.getBuildings().size());

    }

    @Test
    public void testTrapCard() {
    Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
    Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
    List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
    orderedPath.add(pair1);
    orderedPath.add(pair2);
    LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

    SimpleIntegerProperty x = new SimpleIntegerProperty(1);
    SimpleIntegerProperty y = new SimpleIntegerProperty(3);

    TrapCard card = new TrapCard(x, y);

    world.addBuilding(card.createBuilding(x, y));

    assertEquals(1, world.getBuildings().size());

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

    VillageCard card = new VillageCard(x, y);
    
    world.addBuilding(card.createBuilding(x, y));

    assertEquals(1, world.getBuildings().size());

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

    ZombiePitCard card = new ZombiePitCard(x, y);

    world.addBuilding(card.createBuilding(x, y));

    assertEquals(1, world.getBuildings().size());

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

    VampireCastleCard card = new VampireCastleCard(x, y);

    world.addBuilding(card.createBuilding(x, y));

    assertEquals(1, world.getBuildings().size());

    }

    @Test
    public void testTowerCard() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        SimpleIntegerProperty x = new SimpleIntegerProperty(2);
        SimpleIntegerProperty y = new SimpleIntegerProperty(2);

        TowerCard card = new TowerCard(x, y);

        world.addBuilding(card.createBuilding(x, y));

        assertEquals(1, world.getBuildings().size());
    }
}
