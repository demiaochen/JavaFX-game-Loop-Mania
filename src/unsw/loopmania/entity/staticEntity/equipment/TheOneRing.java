package unsw.loopmania.entity.staticEntity.equipment;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;

public class TheOneRing extends RareItem{

    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        andurilEffect = false;
        treeStumpEffect = false;
        theOneRingEffect = true;
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
    }
    
}