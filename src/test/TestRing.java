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
import unsw.loopmania.entity.movingEntity.basicEnemy.Doggie;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.entity.staticEntity.equipment.Anduril;
import unsw.loopmania.entity.staticEntity.equipment.TheOneRing;
import unsw.loopmania.entity.staticEntity.equipment.TreeStump;
import unsw.loopmania.util.PathPosition;


public class TestRing {
    @Test
    public void testRing() {
        Character character = new Character(null);
        LoopManiaWorld world = new LoopManiaWorld(1, 1, null);
        world.setCharacter(character);
        TheOneRing oneRing = new TheOneRing(null, null);
        oneRing.equip(character);
        assertEquals(1, character.getReviveTime());
    }
}
