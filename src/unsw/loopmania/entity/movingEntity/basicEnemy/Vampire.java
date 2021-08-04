package unsw.loopmania.entity.movingEntity.basicEnemy;

import java.util.*;

import org.json.JSONObject;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.BasicAlly;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicAlly.Soldier;
import unsw.loopmania.util.PathPosition;

public class Vampire extends BasicEnemy {
    private int additionalDamage;
    private int numAttack; //number of attacks do additional damage
    
    /**
     * Create Vampire object
     * @param position
     */
    public Vampire(PathPosition position) {
        super(position, 200, 250, 5, 7);
        additionalDamage = 0;
        numAttack = 0;
    }

    @Override
    public void move(LoopManiaWorld world) {
        moveUpPath();
        moveUpPath();
        //run away from campfire
        while(world.withinCampfireRadius(this)) {
            moveUpPath();
            moveUpPath();
        }
    }

    @Override
    public void doDamage(BasicAlly ally) {
        int damage = attack;
        if(numAttack > 0) {
            numAttack--;
            damage += additionalDamage;
        }
        if(ally instanceof Character) {
            Character c = (Character)ally;
            c.takeDamage(damage);
        }
        else if(ally instanceof Soldier) {
            Soldier s = (Soldier)ally;
            s.takeDamage(damage);
        }
    }

    @Override
    public void specialAttack(BasicAlly ally, LoopManiaWorld world, Random r) {
        criticalBite(ally, r);
        return;
    }

    /**
     * Vampire make critical bites with 20%
     * @param ally
     */
    public void criticalBite(BasicAlly ally, Random r) {
        //20% chance
        if(r.nextInt(5) == 0) {
            numAttack = r.nextInt(5); //0 - 5 additional damage attacks
            additionalDamage = r.nextInt(100); //0 - 100 additional damage
        }

    }

    @Override
    public JSONObject battleReward() {
        JSONObject obj = new JSONObject();
        obj.put("gold", 1000);
        obj.put("exp", 500);
        boolean r0 = new Random().nextInt(2) == 0;
        boolean r1 = new Random().nextInt(10) == 0;
        if (r0)
            obj.put("cardAmount", 1);   // 1/2 chance to get 
        else 
            obj.put("cardAmount", 0);
        if (r1)
            obj.put("equipmentAmount", 1);   // 1/10 chance to get 
        else 
            obj.put("equipmentAmount", 0);
        return obj;
    }
}
