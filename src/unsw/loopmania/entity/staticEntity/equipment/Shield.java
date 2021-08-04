package unsw.loopmania.entity.staticEntity.equipment;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;

public class Shield extends Equipment{
    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public void equip(Character character) {
        character.addDefense(50);
        character.equipShield(this);
    }

    @Override
    public void unequip(Character character) {
        character.minusDefense(50);
    }
}