package unsw.loopmania.entity.staticEntity.building.buildingNotOnPath;

import javafx.beans.property.SimpleIntegerProperty;

public class CampfireBuilding extends BuildingNotOnPath {

    /**
     * Builds Campfire
     * @param x
     * @param y
     */
    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        radius = 4;
    }
}
