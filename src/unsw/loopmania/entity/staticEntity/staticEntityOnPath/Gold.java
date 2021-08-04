package unsw.loopmania.entity.staticEntity.staticEntityOnPath;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

public class Gold extends StaticEntityOnPath{
    public Gold(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public void effect(LoopManiaWorld world) {
        world.addGold(1000);
    }
}
