package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.VillageBuilding;
import unsw.loopmania.util.PathPosition;

public class TestPassThroughVillage {
    @Test
    public void testPass() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(0, 0);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pair1);
        LoopManiaWorld world = new LoopManiaWorld(1000, 1000, orderedPath);
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);

        VillageBuilding village = new VillageBuilding(x, y);
        world.addBuilding(village);

        Character character = new Character(new PathPosition(0, orderedPath));
        world.setCharacter(character);

        // Character will not have HP more than maxHP
        village.effect(world);
        assertEquals(character.getMaxHP(), character.getHP());

        // get 20% of maxHP
        character.setHP(500);
        village.effect(world);
        assertEquals(700, character.getHP());
    }
}
