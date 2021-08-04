package unsw.loopmania.entity.staticEntity.staticEntityOnPath;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.staticEntity.StaticEntity;

public abstract class StaticEntityOnPath extends StaticEntity{

    public StaticEntityOnPath(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public abstract void effect(LoopManiaWorld world);
}
