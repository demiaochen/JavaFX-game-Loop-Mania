package unsw.loopmania;

import java.util.Random;

import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;
import unsw.loopmania.entity.movingEntity.basicEnemy.Doggie;
import unsw.loopmania.entity.movingEntity.basicEnemy.ElanMuske;

public class CharacterAttackBoss implements AllyDoDamage{
    private final Character character;

    public CharacterAttackBoss(Character character) {
        this.character = character;
    }

    @Override
    public void doDamage(BasicEnemy e) {
        //triple damage to bosses
        if(e instanceof Doggie || e instanceof ElanMuske) {
            for(int i = (int)character.getPercentageDamageToBoss(); i > 0; i--) {
                character.doDamage(e);
                character.doDamage(e);
                character.doDamage(e);
            }
        }
        else {
            character.doDamage(e);
        }
    }

    @Override
    public void doDamage(BasicEnemy e, LoopManiaWorld world, Random r) {
        //triple damage to bosses
        if(e instanceof Doggie || e instanceof ElanMuske) {
            for(int i = (int)character.getPercentageDamageToBoss(); i > 0; i--) {
                character.doDamage(e, world, r);
                character.doDamage(e, world, r);
                character.doDamage(e, world, r);
            }
        }
        else {
            character.doDamage(e, world, r);
        }
    }
}
