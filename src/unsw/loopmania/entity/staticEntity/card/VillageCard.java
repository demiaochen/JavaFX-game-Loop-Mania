package unsw.loopmania.entity.staticEntity.card;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.entity.staticEntity.building.Building;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.VillageBuilding;

public class VillageCard extends Card{
    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        return new VillageBuilding(buildingNodeX, buildingNodeY);
    }
}
