package unsw.loopmania.entity.staticEntity.building;

import javafx.beans.property.SimpleIntegerProperty;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import unsw.loopmania.AllyDoDamage;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;
import unsw.loopmania.entity.staticEntity.StaticEntity;

public abstract class Building extends StaticEntity implements AllyDoDamage{
    protected int radius;
    
    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        radius = 0;
    }

    /**
     * 
     * @return effect radius
     */
    public int getEffectRadius() {
        return radius;
    }

    /**
     * 
     * @param x index from 0 to width-1 of building to be added
     * @param y index from 0 to height-1 of building to be added
     * @param orderedPath: path tiles location
     * @return whether the building can be built on given location
     */
    public abstract boolean canBuild(int x, int y, List<Pair<Integer, Integer>> orderedPath);

    public BasicEnemy spawnEnemy(List<Pair<Integer, Integer>> orderedPath, int loop, boolean completeLoop) {
        return null;
    }

    /**
     * effect when character pass through
     * @param world
     */
    public void effect(LoopManiaWorld world){
    }
    

    @Override
    /**
     * attack given enemy if possible
     * @param enemy
     */
    public void doDamage(BasicEnemy enemy) {
    }

    @Override
    /**
     * attack given enemy if possible (with some effects)
     * @param enemy
     * @param world
     * @param r
     */
    public void doDamage(BasicEnemy enemy, LoopManiaWorld world, Random r) {
    }
}