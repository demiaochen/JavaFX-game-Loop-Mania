package unsw.loopmania;

import java.lang.Math;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;


public class Level {
    private StringProperty currentLevel;
    /**
     * No params passed in as will always start from Level 1
     */
    public Level() {
        currentLevel = new SimpleStringProperty("0");
    }

    public void setLevel(int level) {
        int currentAmount = this.getLevel();
        currentAmount = level;
        this.currentLevel.set(String.valueOf(currentAmount));
    }

    public int getLevel() {
        return Integer.parseInt(currentLevel.get());
    }

    public StringProperty getPropertyLevel() {
        return this.currentLevel;
    }
    /**
     * Updates the level of the character at any given time, takes in the world and character
     * magic numbers used which can be tuned to change the game
     * @param world
     * @param playerCharacter
     */
    public void updateLevel(LoopManiaWorld world, Character playerCharacter) {
        int currentExp = world.getExp();
        double level = 0.1 * Math.sqrt(currentExp);
        int newLevel = (int)Math.floor(level);
        int currentLvl = getLevel();
        this.setLevel(newLevel);
        if (newLevel > currentLvl) {
            int diff = newLevel - currentLvl;
            /*magic numbers, will be tuned later*/
            playerCharacter.addAttack(diff*3);
            playerCharacter.addDefense(diff*2);
            playerCharacter.addMaxHP(diff*5);
        }

    }
}
