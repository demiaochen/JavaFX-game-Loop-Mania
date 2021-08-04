package unsw.loopmania.entity.staticEntity.equipment;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;

public class Helmet extends Equipment{
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public void equip(Character character) {
        character.addDefense(50);
        character.addPerDamageReduce(0.3);
        character.minusAttack(30);
        character.equipHead(this);
    }

    @Override
    public void unequip(Character character) {
        character.minusDefense(50);
        character.minusPerDamageReduce(0.3);
        character.addAttack(30);
    }
}