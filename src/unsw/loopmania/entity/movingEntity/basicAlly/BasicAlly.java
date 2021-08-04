package unsw.loopmania.entity.movingEntity.basicAlly;

import java.util.Random;

import unsw.loopmania.AllyDoDamage;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.MovingEntity;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;
import unsw.loopmania.util.PathPosition;

/**
 * represents the allies (hero and soldiers) in the backend of the game world
 */

public abstract class BasicAlly extends MovingEntity implements AllyDoDamage{
    protected int maxHP;
    protected int hp;
    protected int attack;
    protected int defense;

    /**
     * Create an ally
     * @param position
     */
    public BasicAlly(PathPosition position, int hp, int attack, int defense) {
        super(position);
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
    }


    public int getHP() {
        return this.hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setHP(int amount) {
        hp = amount;
        if(hp > maxHP) {
            hp = maxHP;
        }
    }
    
    public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
    
    @Override
    public void doDamage(BasicEnemy e) {
        e.takeDamage(attack);
    }

    @Override
    /**
     * do damage to the given enemy
     * @param enemy
     */
    public void doDamage(BasicEnemy e, LoopManiaWorld world, Random r) {
        e.takeDamage(attack);
    }
    
}