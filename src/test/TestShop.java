package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.entity.movingEntity.basicEnemy.Doggie;
import unsw.loopmania.entity.staticEntity.equipment.Armour;
import unsw.loopmania.entity.staticEntity.equipment.Helmet;
import unsw.loopmania.entity.staticEntity.equipment.Shield;
import unsw.loopmania.entity.staticEntity.equipment.Staff;
import unsw.loopmania.entity.staticEntity.equipment.Stake;
import unsw.loopmania.entity.staticEntity.equipment.Sword;
import unsw.loopmania.ui.shop.Shop;
import unsw.loopmania.util.PathPosition;
import unsw.loopmania.LoopManiaWorld;

import org.javatuples.Pair;
import java.util.ArrayList;
import java.util.List;

public class TestShop {
    @Test
    public void testSelectX() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        // If one item is selected, the other two will be unselected
        Shop shop = new Shop(world);
        assertEquals(false, shop.item1Property().get());
        assertEquals(false, shop.item2Property().get());
        assertEquals(false, shop.item3Property().get());

        shop.select1("trivial");
        assertEquals(true, shop.item1Property().get());
        assertEquals(false, shop.item2Property().get());
        assertEquals(false, shop.item3Property().get());

        shop.select2("trivial");
        assertEquals(false, shop.item1Property().get());
        assertEquals(true, shop.item2Property().get());
        assertEquals(false, shop.item3Property().get());

        shop.select3();
        assertEquals(false, shop.item1Property().get());
        assertEquals(false, shop.item2Property().get());
        assertEquals(true, shop.item3Property().get());
    }

    @Test
    public void testCountHold() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y1 = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y2 = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y3 = new SimpleIntegerProperty(2);
        SimpleIntegerProperty y4 = new SimpleIntegerProperty(3);
        SimpleIntegerProperty y5 = new SimpleIntegerProperty(4);
        SimpleIntegerProperty y6 = new SimpleIntegerProperty(5);

        Sword sword = new Sword(x, y1);
        Stake stake = new Stake(x, y2);
        Staff staff = new Staff(x, y3);
        Armour armour = new Armour(x, y4);
        Shield shield = new Shield(x, y5);
        Helmet helmet = new Helmet(x, y6);
        world.addToUnequipped(sword);
        world.addToUnequipped(stake);
        world.addToUnequipped(staff);
        world.addToUnequipped(armour);
        world.addToUnequipped(shield);
        world.addToUnequipped(helmet);

        Shop shop = new Shop(world);
        assertEquals(1, shop.countHold("Sword"));
        assertEquals(1, shop.countHold("Stake"));
        assertEquals(1, shop.countHold("Staff"));
        assertEquals(1, shop.countHold("Shield"));
        assertEquals(1, shop.countHold("Helmet"));
        assertEquals(1, shop.countHold("Armour"));
    }

    @Test
    public void testStandardBuy() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        // no limitation on buying
        world.addGold(16000);

        Shop shop = new Shop(world);
        shop.select1("Sword");
        shop.buy();
        shop.select1("Stake");
        shop.buy();
        shop.select1("Staff");
        shop.buy();
        shop.select1("Armour");
        shop.buy();
        shop.select1("Shield");
        shop.buy();
        shop.select1("Helmet");
        shop.buy();

        assertEquals(2000, world.getGold());
        assertEquals(1, shop.countHold("Sword"));
        assertEquals(1, shop.countHold("Stake"));
        assertEquals(1, shop.countHold("Staff"));
        assertEquals(1, shop.countHold("Armour"));
        assertEquals(1, shop.countHold("Shield"));
        assertEquals(1, shop.countHold("Helmet"));

        shop.select3();
        shop.buy();
        shop.buy();
        assertEquals(0, world.getGold());
        assertEquals(2, world.getHealthPotionNum());
    }

    @Test
    public void testStandardBuy2() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        // no limitation on buying
        world.addGold(16000);

        Shop shop = new Shop(world);
        shop.select2("Sword");
        shop.buy();
        shop.select2("Stake");
        shop.buy();
        shop.select2("Staff");
        shop.buy();
        shop.select2("Armour");
        shop.buy();
        shop.select2("Shield");
        shop.buy();
        shop.select2("Helmet");
        shop.buy();

        assertEquals(2000, world.getGold());
        assertEquals(1, shop.countHold("Sword"));
        assertEquals(1, shop.countHold("Stake"));
        assertEquals(1, shop.countHold("Staff"));
        assertEquals(1, shop.countHold("Armour"));
        assertEquals(1, shop.countHold("Shield"));
        assertEquals(1, shop.countHold("Helmet"));

        shop.select3();
        shop.buy();
        shop.buy();
        assertEquals(0, world.getGold());
        assertEquals(2, world.getHealthPotionNum());
    }

    @Test
    public void testSurvivalBuy() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        world.setupMode("survival");

        // can buy potion only once
        world.addGold(16000);

        Shop shop = new Shop(world);
        shop.select1("Sword");
        shop.buy();
        shop.select1("Stake");
        shop.buy();
        shop.select1("Staff");
        shop.buy();
        shop.select1("Armour");
        shop.buy();
        shop.select1("Shield");
        shop.buy();
        shop.select1("Helmet");
        shop.buy();

        assertEquals(2000, world.getGold());
        assertEquals(1, shop.countHold("Sword"));
        assertEquals(1, shop.countHold("Stake"));
        assertEquals(1, shop.countHold("Staff"));
        assertEquals(1, shop.countHold("Armour"));
        assertEquals(1, shop.countHold("Shield"));
        assertEquals(1, shop.countHold("Helmet"));

        shop.select3();
        shop.buy();
        shop.buy();
        assertEquals(1000, world.getGold());
        assertEquals(1, world.getHealthPotionNum());
    }

    @Test
    public void testSurvivalBuy2() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        world.setupMode("survival");

        // can buy potion only once
        world.addGold(16000);

        Shop shop = new Shop(world);
        shop.select2("Sword");
        shop.buy();
        shop.select2("Stake");
        shop.buy();
        shop.select2("Staff");
        shop.buy();
        shop.select2("Armour");
        shop.buy();
        shop.select2("Shield");
        shop.buy();
        shop.select2("Helmet");
        shop.buy();

        assertEquals(2000, world.getGold());
        assertEquals(1, shop.countHold("Sword"));
        assertEquals(1, shop.countHold("Stake"));
        assertEquals(1, shop.countHold("Staff"));
        assertEquals(1, shop.countHold("Armour"));
        assertEquals(1, shop.countHold("Shield"));
        assertEquals(1, shop.countHold("Helmet"));

        shop.select3();
        shop.buy();
        shop.buy();
        assertEquals(1000, world.getGold());
        assertEquals(1, world.getHealthPotionNum());
    }

    @Test
    public void testSell() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y1 = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y2 = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y3 = new SimpleIntegerProperty(2);
        SimpleIntegerProperty y4 = new SimpleIntegerProperty(3);
        SimpleIntegerProperty y5 = new SimpleIntegerProperty(4);
        SimpleIntegerProperty y6 = new SimpleIntegerProperty(5);

        Sword sword = new Sword(x, y1);
        Stake stake = new Stake(x, y2);
        Staff staff = new Staff(x, y3);
        Armour armour = new Armour(x, y4);
        Shield shield = new Shield(x, y5);
        Helmet helmet = new Helmet(x, y6);
        world.addToUnequipped(sword);
        world.addToUnequipped(stake);
        world.addToUnequipped(staff);
        world.addToUnequipped(armour);
        world.addToUnequipped(shield);
        world.addToUnequipped(helmet);

        Shop shop = new Shop(world);

        shop.select1("Sword");
        shop.sell();
        shop.select1("Stake");
        shop.sell();
        shop.select1("Staff");
        shop.sell();
        shop.select1("Armour");
        shop.sell();
        shop.select1("Shield");
        shop.sell();
        shop.select1("Helmet");
        shop.sell();
        assertEquals(14000, world.getGold());
        assertEquals(0, shop.countHold("Sword"));
        assertEquals(0, shop.countHold("Stake"));
        assertEquals(0, shop.countHold("Staff"));
        assertEquals(0, shop.countHold("Armour"));
        assertEquals(0, shop.countHold("Shield"));
        assertEquals(0, shop.countHold("Helmet"));

        shop.select3();
        shop.sell();
        assertEquals(14000, world.getGold());
        assertEquals(0, world.getHealthPotionNum());

        world.addHealthPotion();
        shop.sell();
        assertEquals(15000, world.getGold());
        assertEquals(0, world.getHealthPotionNum());
    }

    @Test
    public void testSell2() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y1 = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y2 = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y3 = new SimpleIntegerProperty(2);
        SimpleIntegerProperty y4 = new SimpleIntegerProperty(3);
        SimpleIntegerProperty y5 = new SimpleIntegerProperty(4);
        SimpleIntegerProperty y6 = new SimpleIntegerProperty(5);

        Sword sword = new Sword(x, y1);
        Stake stake = new Stake(x, y2);
        Staff staff = new Staff(x, y3);
        Armour armour = new Armour(x, y4);
        Shield shield = new Shield(x, y5);
        Helmet helmet = new Helmet(x, y6);
        world.addToUnequipped(sword);
        world.addToUnequipped(stake);
        world.addToUnequipped(staff);
        world.addToUnequipped(armour);
        world.addToUnequipped(shield);
        world.addToUnequipped(helmet);

        Shop shop = new Shop(world);

        shop.select2("Sword");
        shop.sell();
        shop.select2("Stake");
        shop.sell();
        shop.select2("Staff");
        shop.sell();
        shop.select2("Armour");
        shop.sell();
        shop.select2("Shield");
        shop.sell();
        shop.select2("Helmet");
        shop.sell();
        assertEquals(14000, world.getGold());
        assertEquals(0, shop.countHold("Sword"));
        assertEquals(0, shop.countHold("Stake"));
        assertEquals(0, shop.countHold("Staff"));
        assertEquals(0, shop.countHold("Armour"));
        assertEquals(0, shop.countHold("Shield"));
        assertEquals(0, shop.countHold("Helmet"));

        shop.select3();
        shop.sell();
        assertEquals(14000, world.getGold());
        assertEquals(0, world.getHealthPotionNum());

        world.addHealthPotion();
        shop.sell();
        assertEquals(15000, world.getGold());
        assertEquals(0, world.getHealthPotionNum());
    }

    @Test
    public void testSellCoin() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Doggie doggie = new Doggie(new PathPosition(0, orderedPath));
        world.addEnemy(doggie);
        world.addCoinAmount(1);

        Shop shop = new Shop(world);
        shop.sellCoin();
        shop.sellCoin();

        assertEquals(0, world.getCoinAmount());
        assertEquals(world.getCoinValue(), world.getGold());
    }
    @Test
    public void testBerserkerBuy() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        world.setupMode("berserker");

        world.addGold(16000);

        Shop shop = new Shop(world);
        shop.select1("Sword");
        shop.buy();
        shop.select1("Stake");
        shop.buy();
        shop.select1("Staff");
        shop.buy();
        shop.select1("Armour");
        shop.buy();
        shop.select1("Shield");
        shop.buy();
        shop.select1("Helmet");
        shop.buy();

        assertEquals(2000, world.getGold());
        assertEquals(1, shop.countHold("Sword"));
        assertEquals(1, shop.countHold("Stake"));
        assertEquals(1, shop.countHold("Staff"));
        assertEquals(1, shop.countHold("Armour"));
        assertEquals(1, shop.countHold("Shield"));
        assertEquals(1, shop.countHold("Helmet"));

        shop.select3();
        shop.buy();
        shop.buy();
        assertEquals(0, world.getGold());
        assertEquals(2, world.getHealthPotionNum());
    }
    
}
