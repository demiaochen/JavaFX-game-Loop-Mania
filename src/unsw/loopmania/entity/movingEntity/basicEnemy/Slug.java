package unsw.loopmania.entity.movingEntity.basicEnemy;

import java.util.Random;

import org.json.JSONObject;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.BasicAlly;
import unsw.loopmania.util.PathPosition;
public class Slug extends BasicEnemy {

    /**
     * Create Slug object
     * @param position
     */
    public Slug(PathPosition position) {
        super(position, 150, 50, 2, 2);
        //image = new Image((new File("src/images/slug.png")).toURI().toString());
    }

    @Override
    public void specialAttack(BasicAlly ally, LoopManiaWorld world, Random r) {
    }

    @Override
    public void move(LoopManiaWorld world){
        moveUpPath();
        moveUpPath();
    }


    @Override
    public JSONObject battleReward() {
        JSONObject obj = new JSONObject();
        obj.put("gold", 300);
        obj.put("exp", 100);
        boolean r0 = new Random().nextInt(5) == 0;
        boolean r1 = new Random().nextInt(30) == 0;
        if (r0)
            obj.put("cardAmount", 1);   // 1/5 chance to get 
        else 
            obj.put("cardAmount", 0);
        if (r1)
            obj.put("equipmentAmount", 1);   // 1/30 chance to get 
        else 
            obj.put("equipmentAmount", 0);
        return obj;
    }
}