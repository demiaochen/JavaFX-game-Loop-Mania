package unsw.loopmania.entity.staticEntity.equipment;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;

public abstract class RareItem extends Equipment{
    protected boolean andurilEffect;
    protected boolean treeStumpEffect;
    protected boolean theOneRingEffect;

    public RareItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public void equip(Character character) {
    }

    @Override
    public void unequip(Character character) {
    }

    public void setAndurilEffect(boolean b) {
        andurilEffect = b;
    }

    public void setTreeStumpEffect(boolean b) {
        treeStumpEffect = b;
    }

    public void setTheOneRingEffect(boolean b) {
        theOneRingEffect = b;
    }
}
