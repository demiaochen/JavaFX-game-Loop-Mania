package unsw.loopmania.entity.staticEntity.equipment;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Equipment{
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public void equip(Character character) {
        character.addAttack(65);
        character.equipWeapon(this);
    }

    @Override
    public void unequip(Character character) {
        character.minusAttack(65);
    }
}
