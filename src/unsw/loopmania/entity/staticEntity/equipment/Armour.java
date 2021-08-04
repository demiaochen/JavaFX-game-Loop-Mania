package unsw.loopmania.entity.staticEntity.equipment;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;

public class Armour extends Equipment{
    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public void equip(Character character) {
        character.addDefense(20);
        character.addPerDamageReduce(0.5);
        character.equipBody(this);
    }

    @Override
    public void unequip(Character character) {
        character.minusDefense(20);
        character.minusPerDamageReduce(0.5);
    }
}
