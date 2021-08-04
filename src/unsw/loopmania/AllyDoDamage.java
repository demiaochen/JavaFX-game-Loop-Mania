package unsw.loopmania;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;

import java.util.Random;


public interface AllyDoDamage {
    /**
     * do damage to given enemy
     * @param e
     * pre: enemy is able to attack (alive and within attack radius)
     * post: enemy will lost health based on attack
     */
    public void doDamage(BasicEnemy e);

    /**
     * do damage to given enemy with special ability
     * @param e
     * @param world
     * @param r
     * pre: enemy is able to attack (alive and within attack radius)
     * post: enemy will lost health based on attack, special ability will take effect due to different conditions
     */
    public void doDamage(BasicEnemy e, LoopManiaWorld world, Random r);
}
