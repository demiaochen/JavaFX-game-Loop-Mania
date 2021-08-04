package unsw.loopmania.entity.staticEntity.equipment;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;

public class TreeStump extends RareItem{

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        andurilEffect = false;
        treeStumpEffect = true;
        theOneRingEffect = false;
    }

    @Override
    public void equip(Character character) {
        if(andurilEffect) {
            character.addAttack(300);
            character.addPercentageDamageToBoss(2.0);
        }
        if(treeStumpEffect) {
            character.addDefense(200);
            character.addPercentageDamageReduceBoss(0.5);
        }
        if(theOneRingEffect) {
            character.addReviveTime(1);
        }
        character.equipShield(this);
    }

    @Override
    public void unequip(Character character) {
        if(andurilEffect) {
            character.minusAttack(300);
            character.minusPercentageDamageToBoss(2.0);
        }
        if(treeStumpEffect) {
            character.minusDefense(200);
            character.minusPercentageDamageReduceBoss(0.5);
        }
        if(theOneRingEffect) {
            character.minusReviveTime(1);
        }
    }
}
