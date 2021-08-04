package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.entity.staticEntity.equipment.Equipment;
import unsw.loopmania.entity.staticEntity.equipment.Sword;
import unsw.loopmania.util.PathPosition;

import org.javatuples.Pair;

public class TestCharacter {
    @Test
    public void testBasic() {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(1, 2);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        Slug s = new Slug(null);
        orderedPath.add(pair);
        Character character = new Character(new PathPosition(0, orderedPath));
        assertEquals(character.getHP(), 1000);
        assertEquals(character.getAttack(), 100);
        assertEquals(character.getX(), 1);
        assertEquals(character.getY(), 2);

        character.takeDamage(123);
        assertEquals(character.getHP(), 877);

        LoopManiaWorld world = new LoopManiaWorld(1, 1, orderedPath);
        character.doDamage(s, world, null);
        assertEquals(s.getHP(), 50);
    }

    @Test
    public void testAddMinus() {
        Character character = new Character(null);
        character.addAttack(100);
        assertEquals(character.getAttack(), 200);
        character.minusAttack(100);
        assertEquals(character.getAttack(), 100);
        character.addDefense(100);
        assertEquals(character.getDefense(), 100);
        character.minusDefense(100);
        assertEquals(character.getDefense(), 0);
        character.addPerDamageReduce(0.5);
        assertEquals(character.getPerDamageReduce(), 0.5);
        character.minusPerDamageReduce(0.5);
        assertEquals(character.getPerDamageReduce(), 0);
    }

    @Test
    public void testEquipment() {
        Character character = new Character(null);
        Equipment sword = new Sword(null, null);
        Equipment[] list = character.getEquipments();
        for(int i = 0; i < 4; i++) {
            assertEquals(list[i], null);
        }
        character.equipBody(sword);
        character.equipHead(sword);
        character.equipShield(sword);
        character.equipWeapon(sword);
        for(int i = 0; i < 4; i++) {
            assertEquals(list[i], sword);
        }
    }

    @Test
    public void testRestoreHealth() {
        Character character = new Character(null);
        character.takeDamage(500);
        assertEquals(character.getHP(), 500);
        character.restoreHP(300);
        assertEquals(character.getHP(), 800);
        character.restoreHP(10000);
        assertEquals(character.getHP(), 1000);
    }

}
