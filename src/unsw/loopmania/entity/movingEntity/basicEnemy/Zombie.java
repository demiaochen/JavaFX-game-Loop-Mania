package unsw.loopmania.entity.movingEntity.basicEnemy;

import java.util.Random;

import org.json.JSONObject;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.BasicAlly;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.util.PathPosition;


public class Zombie extends BasicEnemy {

    /**
     * Create Zombie Object
     * @param position
     */
    public Zombie(PathPosition position) {
        super(position, 100, 150, 4, 2);
        //image = new Image((new File("src/images/zombie.png")).toURI().toString());
    }

    @Override
    public void specialAttack(BasicAlly ally, LoopManiaWorld world, Random r) {
        convertBite(ally, world, r);
    }

    /**
     * Zombie make convert bites with 30%
     * @param ally
     */
    public void convertBite(BasicAlly ally, LoopManiaWorld world, Random r) {
        if(ally instanceof Character) {return;}
        //30% chance
        if (r.nextInt(10) < 3) {
            world.killAlly(ally);
            Zombie z = new Zombie(null);
            world.addAliveEnemy(z);
            world.addEnemy(z);
        }
    }

    @Override
    public JSONObject battleReward() {
        JSONObject obj = new JSONObject();
        obj.put("gold", 500);
        obj.put("exp", 500);
        obj.put("cardAmount", 1);
        obj.put("equipmentAmount", 0);
        return obj;
    }
}
