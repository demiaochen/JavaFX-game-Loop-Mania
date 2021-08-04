package unsw.loopmania.entity.movingEntity.basicEnemy;

import java.util.Random;

import org.json.JSONObject;

import unsw.loopmania.EnemyDoDamage;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.MovingEntity;
import unsw.loopmania.entity.movingEntity.basicAlly.BasicAlly;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicAlly.Soldier;
import unsw.loopmania.util.PathPosition;

/**
 * a basic form of enemy in the world
 */
public abstract class BasicEnemy extends MovingEntity implements EnemyDoDamage{
    protected int health;
    protected int attack;
    protected int battleRadius;
    protected int supportRadius;
    protected int maxHP;
    //protected Image image;
    /**
     * 
     * @param position
     * @param health
     * @param attack
     * @param battleRadius
     * @param supportRadius
     * @param speed
     */
    public BasicEnemy(PathPosition position, int health, int attack, int battleRadius, int supportRadius) {
        super(position);
        this.health = health;
        this.attack = attack;
        this.battleRadius = battleRadius;
        this.supportRadius = supportRadius;
        this.maxHP = health;
    }


    /**
     * move anti-clockwise for 1 tile by default
     */
    public void move(LoopManiaWorld world){
        moveUpPath();
    }

    /**
     * @return the battle radius of the enemy
     */
    public int getBattleRadius() {return battleRadius;}

    /**
     * @return the support radius of the enemy
     */
    public int getSupportRadius() {return supportRadius;}

    /**
     * @return enemy's hp
     */
    public int getHP() {return this.health;}

    /**
     * @return enemy's attack(the damage that the enemy can cause)
     */
    public int getAttack() {return this.attack;}

    /**
     * 
     * @return JSONObject contains rewards of defeating this enemy
     */
    public abstract JSONObject battleReward();

    /**
     * take damage from an ally
     * @param damage
     */
    public void takeDamage(int damage) {
        health -= damage;
    }

    /**
     * use special move if has one
     */
    @Override
    public void specialAttack(BasicAlly ally, LoopManiaWorld world, Random r) {
    }
    /**
     * do damage to the a BasicAlly
     */
    @Override
    public void doDamage(BasicAlly ally) {
        if(ally instanceof Character) {
            Character c = (Character)ally;
            c.takeDamage(attack);
        }
        else if(ally instanceof Soldier) {
            Soldier s = (Soldier)ally;
            s.takeDamage(attack);
        }
    }

    /**
     * heals self for flat amount
     */
    public void heal() {
        health += 100;
        if(health > maxHP) {
            health = maxHP;
        }
    }
}
