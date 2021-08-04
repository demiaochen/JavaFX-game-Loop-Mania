package unsw.loopmania.entity.staticEntity.staticEntityOnPath;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

public class HealthPotion extends StaticEntityOnPath{
    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public void effect(LoopManiaWorld world) {
        world.addHealthPotion();
    }
}
