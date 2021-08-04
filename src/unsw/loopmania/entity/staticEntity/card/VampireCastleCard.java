package unsw.loopmania.entity.staticEntity.card;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.entity.staticEntity.building.Building;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.VampireCastleBuilding;


/**
 * represents a vampire castle card in the backend game world
 */
public class VampireCastleCard extends Card {

    public VampireCastleCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        return new VampireCastleBuilding(buildingNodeX, buildingNodeY);
    }
}
