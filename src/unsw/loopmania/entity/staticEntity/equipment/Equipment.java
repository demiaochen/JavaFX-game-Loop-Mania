package unsw.loopmania.entity.staticEntity.equipment;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.staticEntity.StaticEntity;

/**
 * represents an equipped or unequipped equipment in the backend world
 */
public abstract class Equipment extends StaticEntity {
    public Equipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public abstract void equip(Character character);

    public abstract void unequip(Character character);
}
