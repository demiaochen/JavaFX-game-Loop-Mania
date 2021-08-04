package test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.util.PathPosition;
import unsw.loopmania.ui.game.LoopManiaWorldController;

public class TestLevel {
    @Test
    public void testLevelUp() {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> p1 = new Pair<Integer, Integer>(1, 3);
        Pair<Integer, Integer> p2 = new Pair<Integer, Integer>(1, 4);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        orderedPath.add(pair);
        orderedPath.add(p1);
        orderedPath.add(p2);
        Slug s = new Slug(new PathPosition(1, orderedPath));
        Slug s1 = new Slug(new PathPosition(1, orderedPath));
        Character character = new Character(new PathPosition(1, orderedPath));
        world.addEnemy(s);
        world.addEnemy(s1);
        world.setCharacter(character);
        assertEquals(character.getHP(), 1000);
        assertEquals(character.getAttack(), 100);


        world.runBattles();
        world.addExp(200);
        world.runTickMoves();
        assertEquals(103, character.getAttack());
        assertEquals(2, character.getDefense());
        assertEquals(1, world.getLevel());
        assertEquals(1005, character.getMaxHP());
    }
}
