package unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.entity.staticEntity.building.Building;

import java.util.List;
import org.javatuples.Pair;

public abstract class BuildingAdjacentPath extends Building{
    public BuildingAdjacentPath(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public boolean canBuild(int x, int y, List<Pair<Integer, Integer>> orderedPath) {
        //check whether position(x, y) is a path tile
        for(int i = 0; i < orderedPath.size(); i++) {
            if(x == orderedPath.get(i).getValue0() && y == orderedPath.get(i).getValue1()) {
                return false;
            }
        }

        //check whether there is a path tile adjacent to position (4 directions)
        for(int i = 0; i < orderedPath.size(); i++) {
            if(x+1 == orderedPath.get(i).getValue0() && y == orderedPath.get(i).getValue1()) {
                return true;
            }
            if(x-1 == orderedPath.get(i).getValue0() && y == orderedPath.get(i).getValue1()) {
                return true;
            }
            if(x == orderedPath.get(i).getValue0() && y+1 == orderedPath.get(i).getValue1()) {
                return true;
            }
            if(x == orderedPath.get(i).getValue0() && y-1 == orderedPath.get(i).getValue1()) {
                return true;
            }
        }
        
        return false;

    }
}
