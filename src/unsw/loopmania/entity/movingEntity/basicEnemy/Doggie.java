package unsw.loopmania.entity.movingEntity.basicEnemy;

import java.util.Random;

import org.json.JSONObject;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.BasicAlly;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.util.PathPosition;

public class Doggie extends BasicEnemy{
    private int stunTimes;

    /**
     * Create Doggie object
     * @param position
     */
    public Doggie(PathPosition position) {
        super(position, 5000, 200, 2, 2);
        stunTimes = 0;
    }

    @Override
    public void specialAttack(BasicAlly ally, LoopManiaWorld world, Random r) {
        if(stunTimes < 1 && ally instanceof Character) {
            world.getCharacter().setStunnedRound(3);
            stunTimes += 1;
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
        Random r = new Random();
        int coinAmount = r.nextInt(9) + 1;
        obj.put("gold", 1000);
        obj.put("exp", 1000);
        obj.put("cardAmount", 1);
        obj.put("equipmentAmount", 1);
        obj.put("doggieCoin", coinAmount);
        return obj;
    }
}
