package unsw.loopmania.entity.movingEntity.basicAlly;

import unsw.loopmania.AllyTakeDamage;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;
import unsw.loopmania.util.PathPosition;

/**
 * represents the main character in the backend of the game world
 */
public class Soldier extends BasicAlly implements AllyTakeDamage{

    private int tranceRound; //when tranceRound = 0, soldier will return to an enemy
    private BasicEnemy e;
    /**
     * Create Character object
     * @param position
     */
    public Soldier(PathPosition position, int tranceRound, BasicEnemy e) {
        super(position, 200, 50, 0);
        super.setMaxHP(200);
        this.tranceRound = tranceRound;
        this.e = e;
        // hp = 200;
        // attack = 50;
        // defense = 0;
    }

    /**
     * move clockwise for 1 tile to follow character 
     */
    public void move() {
        moveDownPath();
    }

    public void nextRound() {
        tranceRound--;
    }

    public int getTranceRount() {
        return tranceRound;
    }
    
    public BasicEnemy getEnemy() {
        return e;
    }

    @Override
    public void takeDamage(int damage) {
        int realDamage = damage - defense;
        if(realDamage < 0) {
            realDamage = 0;
        }
        hp = hp - realDamage;
    }


}
