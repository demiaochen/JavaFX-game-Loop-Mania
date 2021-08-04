package unsw.loopmania.entity.staticEntity.card;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.entity.staticEntity.building.Building;
import unsw.loopmania.entity.staticEntity.building.buildingOnPath.TrapBuilding;

public class TrapCard extends Card{
    public TrapCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        return new TrapBuilding(buildingNodeX, buildingNodeY);
    }
}
