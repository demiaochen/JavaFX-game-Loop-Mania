package test;


import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicEnemy.ElanMuske;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.ui.achievement.Achievement;

import org.javatuples.Pair;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class TestAchievement {


    @Test
    public void TestAchievementKill() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        world.setupMode("confusing");

        // before achieving
        Achievement achievement = world.getAchievement();
        assertEquals(false, achievement.achievementKill);

        for (int i = 0; i < Achievement.KILL_NUM_ACHIEVE; i++) {
            Slug e = new Slug(null);
            world.addEnemy(e);
            world.killEnemy(e);
        }

        // after achieving 
        achievement.checkAchievement();
        assertEquals(true, achievement.achievementKill);
    }

    @Test
    public void TestGold() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        world.setupMode("confusing");

        // before achieving
        Achievement achievement = world.getAchievement();
        assertEquals(false, achievement.achievementGold);

        world.addGold(Achievement.GOLD_ACHIEVE);

        // after achieving 
        achievement.checkAchievement();
        assertEquals(true, achievement.achievementGold);
    }

    @Test
    public void TestLoops() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        world.setupMode("confusing");

        // before achieving
        Achievement achievement = world.getAchievement();
        assertEquals(false, achievement.achievementLoops);


        for (int i = 0; i < Achievement.LOOPS_ACHIEVE; i++) {
            world.addLoop();
        }

        // after achieving 
        achievement.checkAchievement();
        assertEquals(true, achievement.achievementLoops);
    }


    @Test
    public void Testboss() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        world.setupMode("confusing");

        // before achieving
        Achievement achievement = world.getAchievement();
        assertEquals(false, achievement.achievementBoss);

        ElanMuske elan = new ElanMuske(null);

        world.addEnemy(elan);
        world.killEnemy(elan);

        // after achieving 
        achievement.checkAchievement();
        assertEquals(true, achievement.achievementBoss);
    }

    @Test
    public void TestDoggieCoin() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        world.setupMode("confusing");

        // before achieving
        Achievement achievement = world.getAchievement();
        assertEquals(false, achievement.achievementDoggie);

        world.addCoinAmount(Achievement.DOGGIE_ACHIEVE);

        // after achieving 
        achievement.checkAchievement();
        assertEquals(true, achievement.achievementDoggie);
    }
}
