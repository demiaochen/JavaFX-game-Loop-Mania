package unsw.loopmania.entity.movingEntity.basicAlly;

import java.util.*;

import unsw.loopmania.AllyTakeDamage;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;
import unsw.loopmania.entity.movingEntity.basicEnemy.Doggie;
import unsw.loopmania.entity.movingEntity.basicEnemy.ElanMuske;
import unsw.loopmania.entity.movingEntity.basicEnemy.Vampire;
import unsw.loopmania.entity.staticEntity.equipment.Equipment;
import unsw.loopmania.entity.staticEntity.equipment.Staff;
import unsw.loopmania.entity.staticEntity.equipment.Stake;
import unsw.loopmania.util.PathPosition;
import unsw.loopmania.util.Sound;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends BasicAlly implements AllyTakeDamage{
    private Equipment[] equipmentList; //index 0: weapon, 1: body(armour), 2: shield, 3: head(helmet)
    private double percentageDamageReduce; //percentage of reduce of enemy's damage/attack
    private int stunnedRound;
    private int reviveTime; //rest chance of revive
    private double percentageDamageToBoss; //percentage of more damage to bosses
    private double percentageDamageReduceBoss; //percentage of reduce of boss's damage/attack

    /**
     * 
     * @param position
     */
    public Character(PathPosition position) {
        super(position, 1000, 100 ,0);
        maxHP = 1000;
        equipmentList = new Equipment[4];
        percentageDamageReduce = 0.0;
        for(int i = 0; i < equipmentList.length; i++) {
            equipmentList[i] = null;
        }
        stunnedRound = 0;
        reviveTime = 0;
        percentageDamageToBoss = 1;
        percentageDamageReduceBoss = 0;
    }

    public int getReviveTime() {
        return reviveTime;
    }

    public void setStunnedRound(int rounds) {
        stunnedRound = rounds;
    }

    public void reduceStunnedRound() {
        stunnedRound --;
    }

    public int getStunnedRound() {
        return stunnedRound;
    }

    public double getPerDamageReduce() {
        return percentageDamageReduce;
    }

    public double getPercentageDamageToBoss() {
        return percentageDamageToBoss;
    }

    public double getPercentageDamageReduceBoss() {
        return percentageDamageReduceBoss;
    }

    public Equipment[] getEquipments() {
        return equipmentList;
    }
    public void addAttack(int amount) {
        attack += amount;
    }

    public void minusAttack(int amount) {
        attack -= amount;
    }

    public void addDefense(int amount) {
        defense += amount;
    }
    
    public void minusDefense(int amount) {
        defense -= amount;
    }

    public void addPerDamageReduce(double amount) {
        percentageDamageReduce += amount;
        if(percentageDamageReduce > 1) {
            percentageDamageReduce = 1;
        }
    }

    public void minusPerDamageReduce(double amount) {
        percentageDamageReduce -= amount;
        if(percentageDamageReduce < 0) {
            percentageDamageReduce = 0;
        }
    }

    public void addPercentageDamageToBoss(double amount) {
        percentageDamageToBoss += amount;
    }

    public void minusPercentageDamageToBoss(double amount) {
        percentageDamageToBoss -= amount;
        if(percentageDamageToBoss < 0) {
            percentageDamageToBoss = 0;
        }
    }

    public void addPercentageDamageReduceBoss(double amount) {
        percentageDamageReduceBoss += amount;
        if(percentageDamageReduceBoss > 1) {
            percentageDamageReduceBoss = 1;
        }
    }

    public void minusPercentageDamageReduceBoss(double amount) {
        percentageDamageReduceBoss -= amount;
        if(percentageDamageReduceBoss < 0) {
            percentageDamageReduceBoss = 0;
        }
    }

    public void addReviveTime(int amount) {
        reviveTime += amount;
    }

    public void minusReviveTime(int amount) {
        reviveTime -= amount;
        if(reviveTime < 0) {
            reviveTime = 0;
        }
    }

    public void addMaxHP(int amount) {
        maxHP += amount;
    }

    public void minusMaxHP(int amount) {
        maxHP -= amount;
        if(maxHP <= 0) {
            maxHP = 1;
        }
    }

    public void equipWeapon(Equipment equipment) {
        if(equipmentList[0] != null) {
            equipmentList[0].unequip(this);
        }
        equipmentList[0] = equipment;
    }

    public void equipBody(Equipment equipment) {
        if(equipmentList[1] != null) {
            equipmentList[1].unequip(this);
        }
        equipmentList[1] = equipment;
    }

    public void equipShield(Equipment equipment) {
        if(equipmentList[2] != null) {
            equipmentList[2].unequip(this);
        }
        equipmentList[2] = equipment;
    }

    public void equipHead(Equipment equipment) {
        if(equipmentList[3] != null) {
            equipmentList[3].unequip(this);
        }
        equipmentList[3] = equipment;
    }

    public void restoreHP(int amount) {
        hp += amount;
        if(hp > maxHP) {
            hp = maxHP;
        }
    }

    @Override
    /**
     * do damage to the given enemy
     * @param enemy
     */
    public void doDamage(BasicEnemy e, LoopManiaWorld world, Random r) {
        if(stunnedRound == 0) {
            int damage = attack;
            if(world.withinCampfireRadius(this)) {
                damage *= 2; //double damage within campfire radius
            }

            e.takeDamage(damage);
            if((e instanceof Vampire) && (equipmentList[0] instanceof Stake)) {
                e.takeDamage(damage); //double damage to vampire using stake
            }

            if(!(equipmentList[0] instanceof Staff) || e instanceof Doggie || e instanceof ElanMuske) {return;}

            //30% chance of cause a trance using staff
            if(r.nextInt(10) < 3) {
                world.removeFromAliveEnemies(e);
                Soldier s = new Soldier(null, 3, e);
                world.addSoldier(s);
            }
        }
    }

    @Override
    public void takeDamage(int damage) {
        int realDamage = damage - defense;
        if(realDamage < 0) {
            realDamage = 0;
        }
        hp = (int)(hp - realDamage * (1-percentageDamageReduce));
    }

    @Override
    public void destroy() {
        if (reviveTime >=1) {
            Sound.playSound("revive.wav", 0.3);
            hp = maxHP;
        }
        reviveTime--;
    }


}
