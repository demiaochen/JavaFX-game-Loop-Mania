package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.util.PathPosition;

public class TestBasicEnemy {
    @Test
    public void testBasic() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 3);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        BasicEnemy enemy = new Slug(new PathPosition(0, orderedPath));
        world.addEnemy(enemy);

        enemy.move(world);
        assertEquals(1, enemy.getX());
        assertEquals(2, enemy.getY());

        enemy.doDamage(character);
        enemy.takeDamage(character.getAttack());

        assertEquals(950, character.getHP());
        assertEquals(50, enemy.getHP());
    }
}
