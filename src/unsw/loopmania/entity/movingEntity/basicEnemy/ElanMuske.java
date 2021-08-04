package unsw.loopmania.entity.movingEntity.basicEnemy;

import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.BasicAlly;
import unsw.loopmania.util.PathPosition;

public class ElanMuske extends BasicEnemy{

    /**
     * Create ElanMuske object
     * @param position
     */
    public ElanMuske(PathPosition position) {
        super(position, 10000, 500, 2, 2);
    }

    @Override
    public void specialAttack(BasicAlly ally, LoopManiaWorld world, Random r) {
        // heal enemies within support range
        List<BasicEnemy> enemies = world.getEnemies();
        for(BasicEnemy enemy: enemies) {
            // within support range, heal other enemies
            if(Math.pow(this.getX()-enemy.getX(), 2) + Math.pow(this.getY()-enemy.getY(), 2) <= Math.pow(this.getSupportRadius(), 2)) {
                enemy.heal();
            }
        }
    }

    @Override
    public void move(LoopManiaWorld world){
        moveUpPath();
        moveUpPath();
    }

    @Override
    public JSONObject battleReward() {
        JSONObject obj = new JSONObject();
        obj.put("gold", 3000);
        obj.put("exp", 3000);
        obj.put("cardAmount", 1);
        obj.put("equipmentAmount", 1);
        return obj;
    }
}
