package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.junit.Test;

import unsw.loopmania.BossAttackCharacter;
import unsw.loopmania.CharacterAttackBoss;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.BasicAlly;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicAlly.DoggiePet;
import unsw.loopmania.entity.movingEntity.basicAlly.Soldier;
import unsw.loopmania.entity.movingEntity.basicEnemy.Doggie;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.entity.staticEntity.equipment.Anduril;
import unsw.loopmania.entity.staticEntity.equipment.TreeStump;
import unsw.loopmania.util.PathPosition;

public class TestBossCharacterBattle {
    @Test
    public void testAttack() {
        Character character = new Character(null);
        LoopManiaWorld world = new LoopManiaWorld(1, 1, null);
        world.setCharacter(character);
        Anduril anduril = new Anduril(null, null);
        anduril.equip(character);
        assertEquals(400, character.getAttack());

        Doggie doggie1 = new Doggie(null);
        Doggie doggie2 = new Doggie(null);

        CharacterAttackBoss attackBoss = new CharacterAttackBoss(character);
        attackBoss.doDamage(doggie1);
        assertEquals(1400, doggie1.getHP());
        attackBoss.doDamage(doggie2, world, new Random());
        assertEquals(1400, doggie2.getHP());

        anduril.unequip(character);
        assertEquals(100, character.getAttack());
    }

    @Test
    public void testDefense() {
        Character character = new Character(null);
        LoopManiaWorld world = new LoopManiaWorld(1, 1, null);
        world.setCharacter(character);
        TreeStump treeStump = new TreeStump(null, null);
        treeStump.equip(character);
        assertEquals(200, character.getDefense());

        Doggie doggie = new Doggie(null);
        Soldier soldier = new Soldier(null, -1, null);

        BossAttackCharacter attackCharacter = new BossAttackCharacter(doggie);
        attackCharacter.doDamage(character);
        assertEquals(1000, character.getHP());

        attackCharacter.doDamage(soldier);
        assertEquals(0, soldier.getHP());

        attackCharacter.specialAttack(character, world, new Random());
        assertEquals(3, character.getStunnedRound());

        treeStump.unequip(character);
        assertEquals(0, character.getDefense());
    }
    
}
