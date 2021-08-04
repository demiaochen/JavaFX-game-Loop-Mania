package unsw.loopmania.entity.staticEntity.equipment;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;

public class Staff extends Equipment{
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public void equip(Character character) {
        character.addAttack(35);
        character.equipWeapon(this);
    }

    @Override
    public void unequip(Character character) {
        character.minusAttack(35);
    }
}
