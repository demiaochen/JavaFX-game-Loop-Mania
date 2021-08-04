package unsw.loopmania;

import java.util.Random;

import unsw.loopmania.entity.movingEntity.basicAlly.BasicAlly;

public interface EnemyDoDamage {
    /**
     * do damage to given ally
     * @param ally
     * pre: ally is able to attack (alive and within attack radius)
     * post: ally will lost health based on attack
     */
    public void doDamage(BasicAlly ally);

    /**
     * apply special ability to given ally
     * @param ally
     * @param world
     * @param r
     * pre: ally is able to attack (alive and within attack radius)
     * post: ally will lost health based on attack, special ability will take effect due to different conditions
     */
    public void specialAttack(BasicAlly ally, LoopManiaWorld world, Random r);
}
