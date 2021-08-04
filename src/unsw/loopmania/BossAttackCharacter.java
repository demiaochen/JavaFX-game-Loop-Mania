package unsw.loopmania;

import java.util.Random;

import unsw.loopmania.entity.movingEntity.basicAlly.BasicAlly;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicAlly.Soldier;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;

public class BossAttackCharacter implements EnemyDoDamage{
    private final BasicEnemy boss;

    public BossAttackCharacter(BasicEnemy boss) {
        this.boss = boss;
    }

    public void doDamage(BasicAlly ally) {
        if(ally instanceof Character) {
            Character character = (Character)ally;
            character.takeDamage((int)((1 - character.getPercentageDamageReduceBoss()) * boss.getAttack()));
        }
        else if(ally instanceof Soldier) {
            Soldier s = (Soldier)ally;
            s.takeDamage(boss.getAttack());
        }
    }

    public void specialAttack(BasicAlly ally, LoopManiaWorld world, Random r) {
        boss.specialAttack(ally, world, r);
    }
}
