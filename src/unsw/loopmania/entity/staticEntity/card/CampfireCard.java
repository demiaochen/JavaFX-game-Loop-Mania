package unsw.loopmania.entity.staticEntity.card;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.entity.staticEntity.building.Building;
import unsw.loopmania.entity.staticEntity.building.buildingNotOnPath.CampfireBuilding;

public class CampfireCard extends Card{
    public CampfireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        return new CampfireBuilding(buildingNodeX, buildingNodeY);
    }
}
