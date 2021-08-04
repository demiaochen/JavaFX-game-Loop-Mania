package unsw.loopmania.entity.staticEntity.card;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.entity.staticEntity.StaticEntity;
import unsw.loopmania.entity.staticEntity.building.Building;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {
    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public abstract Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY);
}
