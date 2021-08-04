package unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;
import org.javatuples.Pair;

import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;
import unsw.loopmania.entity.movingEntity.basicEnemy.Zombie;
import unsw.loopmania.util.PathPosition;

public class ZombiePitBuilding extends BuildingAdjacentPath{

    /**
     * Creates Zombie pit
     * @param x
     * @param y
     */
    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        //image = new Image((new File("src/images/zombie_pit.png")).toURI().toString());
    }

    /**
     * Spawns zombies based on completed loops
     */
    @Override
    public BasicEnemy spawnEnemy(List<Pair<Integer, Integer>> orderedPath, int loop, boolean completeLoop) {
        if(!completeLoop) {return null;}

        int buildingX = x.get();
        int buildingY = y.get();

        int index = 0;

        for(int i = 0; i < orderedPath.size(); i++) {
            int tileX = orderedPath.get(i).getValue0();
            int tileY = orderedPath.get(i).getValue1();
            if(tileX == buildingX + 1 && tileY == buildingY) {
                index = i;
                break;
            }
            if(tileX == buildingX - 1 && tileY == buildingY) {
                index = i;
                break;
            }
            if(tileX == buildingX && tileY == buildingY + 1) {
                index = i;
                break;
            }
            if(tileX == buildingX && tileY == buildingY - 1) {
                index = i;
                break;
            }
        }

        Zombie z = new Zombie(new PathPosition(index, orderedPath));

        return z;
    }
}
