package test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicEnemy.Vampire;
import unsw.loopmania.entity.staticEntity.equipment.Anduril;
import unsw.loopmania.entity.staticEntity.equipment.Armour;
import unsw.loopmania.entity.staticEntity.equipment.Equipment;
import unsw.loopmania.entity.staticEntity.equipment.Helmet;
import unsw.loopmania.entity.staticEntity.equipment.Shield;
import unsw.loopmania.entity.staticEntity.equipment.Staff;
import unsw.loopmania.entity.staticEntity.equipment.Stake;
import unsw.loopmania.entity.staticEntity.equipment.Sword;
import unsw.loopmania.entity.staticEntity.equipment.TheOneRing;
import unsw.loopmania.entity.staticEntity.equipment.TreeStump;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;


public class TestEquipment {
    @Test
    public void testSword() {
        Character character = new Character(null);
        Equipment sword1 = new Sword(null, null);
        Equipment sword2 = new Sword(null, null);
        assertEquals(character.getAttack(), 100);

        sword1.equip(character); //equip
        Equipment[] list;
        list = character.getEquipments();
        assertEquals(list[0], sword1);
        assertEquals(character.getAttack(), 165);

        sword2.equip(character); //equip another one
        list = character.getEquipments();
        assertEquals(list[0], sword2);
        assertEquals(character.getAttack(), 165);
        sword2.unequip(character);
        assertEquals(character.getAttack(), 100);
        list = character.getEquipments();
        assertEquals(list[0], sword2);
    }

    @Test
    public void testStake() {
        Character character = new Character(null);
        Equipment stake1 = new Stake(null, null);
        Equipment stake2 = new Stake(null, null);
        assertEquals(character.getAttack(), 100);

        stake1.equip(character); //equip
        Equipment[] list;
        list = character.getEquipments();
        assertEquals(list[0], stake1);
        assertEquals(character.getAttack(), 150);

        Vampire vampire = new Vampire(null);
        LoopManiaWorld world = new LoopManiaWorld(1, 1, null);
        //double damage to vampire
        character.doDamage(vampire, world, null);
        assertEquals(vampire.getHP(), -100);

        stake2.equip(character); //equip another one
        list = character.getEquipments();
        assertEquals(list[0], stake2);
        assertEquals(character.getAttack(), 150);
        stake2.unequip(character);
        assertEquals(character.getAttack(), 100);
        list = character.getEquipments();
        assertEquals(list[0], stake2);
    }

    @Test
    public void testStaff() {
        Character character = new Character(null);
        Equipment staff1 = new Staff(null, null);
        Equipment staff2 = new Staff(null, null);
        assertEquals(character.getAttack(), 100);

        staff1.equip(character); //equip
        Equipment[] list;
        list = character.getEquipments();
        assertEquals(list[0], staff1);
        assertEquals(character.getAttack(), 135);

        LoopManiaWorld world = new LoopManiaWorld(1, 1, null);
        world.setCharacter(character);
        
        for(int i = 0; i < 10; i++) {
            Vampire vampire = new Vampire(null);
        
            world.addAliveEnemy(vampire);
            Random r1 = new Random(1);
            Random r2 = new Random(1);
            character.doDamage(vampire, world, r1);
            
            if(r2.nextInt(10) < 3) {
                assertEquals(world.getSoldierNum(), 1);
            }
            else {
                assertEquals(world.getSoldierNum(), 0);
            }
        }
        
        
        staff2.equip(character); //equip another one
        list = character.getEquipments();
        assertEquals(list[0], staff2);
        assertEquals(character.getAttack(), 135);
        staff2.unequip(character);
        assertEquals(character.getAttack(), 100);
        list = character.getEquipments();
        assertEquals(list[0], staff2);
    }

    @Test
    public void testArmour() {
        Character character = new Character(null);
        Equipment armour1 = new Armour(null, null);
        Equipment armour2 = new Armour(null, null);
        assertEquals(character.getDefense(), 0);
        assertEquals(character.getPerDamageReduce(), 0);

        armour1.equip(character); //equip
        Equipment[] list;
        list = character.getEquipments();
        assertEquals(list[1], armour1);
        assertEquals(character.getDefense(), 20);
        assertEquals(character.getPerDamageReduce(), 0.5);

        armour2.equip(character); //equip another one
        list = character.getEquipments();
        assertEquals(list[1], armour2);
        assertEquals(character.getDefense(), 20);
        assertEquals(character.getPerDamageReduce(), 0.5);
        armour2.unequip(character);
        assertEquals(character.getDefense(), 0);
        assertEquals(character.getPerDamageReduce(), 0);
        list = character.getEquipments();
        assertEquals(list[1], armour2);
    }

    @Test
    public void testShield() {
        Character character = new Character(null);
        Equipment shield1 = new Shield(null, null);
        Equipment shield2 = new Shield(null, null);
        assertEquals(character.getDefense(), 0);

        shield1.equip(character); //equip
        Equipment[] list;
        list = character.getEquipments();
        assertEquals(list[2], shield1);
        assertEquals(character.getDefense(), 50);

        shield2.equip(character); //equip another one
        list = character.getEquipments();
        assertEquals(list[2], shield2);
        assertEquals(character.getDefense(), 50);
        shield2.unequip(character);
        assertEquals(character.getDefense(), 0);
        list = character.getEquipments();
        assertEquals(list[2], shield2);
    }

    @Test
    public void testHelmet() {
        Character character = new Character(null);
        Equipment helmet1 = new Helmet(null, null);
        Equipment helmet2 = new Helmet(null, null);
        assertEquals(character.getDefense(), 0);
        assertEquals(character.getPerDamageReduce(), 0);
        assertEquals(character.getAttack(), 100);

        helmet1.equip(character); //equip
        Equipment[] list;
        list = character.getEquipments();
        assertEquals(list[3], helmet1);
        assertEquals(character.getDefense(), 50);
        assertEquals(character.getPerDamageReduce(), 0.3);
        assertEquals(character.getAttack(), 70);

        helmet2.equip(character); //equip another one
        list = character.getEquipments();
        assertEquals(list[3], helmet2);
        assertEquals(character.getDefense(), 50);
        assertEquals(character.getPerDamageReduce(), 0.3);
        assertEquals(character.getAttack(), 70);
        helmet2.unequip(character);
        assertEquals(character.getDefense(), 0);
        assertEquals(character.getPerDamageReduce(), 0);
        assertEquals(character.getAttack(), 100);
        list = character.getEquipments();
        assertEquals(list[3], helmet2);
    }

    @Test
    public void testRare() {
        Character character = new Character(null);
        Anduril anduril = new Anduril(null, null);
        TreeStump treeStump = new TreeStump(null, null);
        TheOneRing theOneRing = new TheOneRing(null, null);

        anduril.equip(character);
        treeStump.equip(character);
        theOneRing.equip(character);

        assertEquals(400, character.getAttack());
        assertEquals(200, character.getDefense());
        assertEquals(1, character.getReviveTime());

        anduril.unequip(character);
        treeStump.unequip(character);
        assertEquals(100, character.getAttack());
        assertEquals(0, character.getDefense());
    }
}
