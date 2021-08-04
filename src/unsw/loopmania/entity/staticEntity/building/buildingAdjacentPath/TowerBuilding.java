package unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;


public class TowerBuilding extends BuildingAdjacentPath{

    /**
     * Create Tower Building
     * @param x
     * @param y
     */
    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        radius = 5;
    }

    @Override
    public void doDamage(BasicEnemy enemy) {
        enemy.takeDamage(100);
    }
}
