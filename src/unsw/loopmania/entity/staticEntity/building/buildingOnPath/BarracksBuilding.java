package unsw.loopmania.entity.staticEntity.building.buildingOnPath;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicAlly.Soldier;
import unsw.loopmania.util.PathPosition;


public class BarracksBuilding extends BuildingOnPath{

    /**
     * Creates BarracksBuilding
     * @param x
     * @param y
     */
    public BarracksBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        //image = new Image((new File("src/images/barracks.png")).toURI().toString());
    }

    /**
     * Effect Spawns allies for the main character
     */
    @Override
    public void effect(LoopManiaWorld world) {
        Character c = world.getCharacter();
        if(getX() == c.getX() && getY() == c.getY()) {
            
            Soldier s = new Soldier(new PathPosition(c.getPathPosition().getPositionOnPath(), c.getPathPosition().getOrderedPath()), -1, null);
            world.addSoldier(s);
        }
    }
}
