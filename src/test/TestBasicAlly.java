package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.BasicAlly;
import unsw.loopmania.entity.movingEntity.basicAlly.DoggiePet;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.util.PathPosition;


public class TestBasicAlly {
    @Test
    public void testBasic() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);

        Slug slug = new Slug(new PathPosition(1, orderedPath));
        world.addEnemy(slug);

        BasicAlly ally1 = new BasicAlly(new PathPosition(0, orderedPath), 1000, 100, 50){};
                
        ally1.doDamage(slug, world, null);
        assertEquals(50, slug.getHP());

        DoggiePet pet = new DoggiePet(new PathPosition(0, orderedPath));
        assertEquals(100, pet.getHP());
        assertEquals(5, pet.getAttack());
        assertEquals(0, pet.getDefense());
    }
}
