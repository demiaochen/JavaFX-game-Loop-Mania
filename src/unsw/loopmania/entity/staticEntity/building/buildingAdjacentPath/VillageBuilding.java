package unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.staticEntity.building.buildingOnPath.BuildingOnPath;

public class VillageBuilding extends BuildingOnPath {
    /**
     * Creates Village Building
     * @param x
     * @param y
     */
    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        //image = new Image((new File("src/images/village.png")).toURI().toString());
    }

    /**
     * Heals characters in range
     */
    @Override
    public void effect(LoopManiaWorld world) {
        Character c = world.getCharacter();
        if(getX() == c.getX() && getY() == c.getY()) {
            int amount = c.getMaxHP()/5;
            c.restoreHP(amount);
        }
    }
}
