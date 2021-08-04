package unsw.loopmania.entity.staticEntity.building.buildingOnPath;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;

import java.util.List;

public class TrapBuilding extends BuildingOnPath {
    /*
    *Builds Trap
    */
    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Effect kills enemies when they step on the tile
     */
    @Override
    public void effect(LoopManiaWorld world) {
        List<BasicEnemy> enemies = world.getEnemies();
        for(BasicEnemy e: enemies) {
            if(getX() == e.getX() && getY() == e.getY()) {
                e.takeDamage(200);  
                if(e.getHP() <= 0) {
                    world.killEnemy(e);
                }
                world.removeBuilding(this);
                break;
            }
        }
    }

}
