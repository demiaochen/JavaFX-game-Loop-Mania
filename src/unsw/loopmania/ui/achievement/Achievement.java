package unsw.loopmania.ui.achievement;

import unsw.loopmania.LoopManiaWorld;

public class Achievement {
    
    private LoopManiaWorld world;

    private int numOfKill = 0;
    private int loop = 0;
    private int goldHold = 0;
    private int doggieCoinHold = 0;
    private boolean isBossKilled = false;

    public boolean achievementKill = false; 
    public boolean achievementBoss = false; 
    public boolean achievementGold = false; 
    public boolean achievementLoops = false; 
    public boolean achievementDoggie = false; 

    static public final int KILL_NUM_ACHIEVE = 30;
    static public final int LOOPS_ACHIEVE = 30;
    static public final int GOLD_ACHIEVE = 50_000;
    static public final int DOGGIE_ACHIEVE = 10;
    
    public Achievement(LoopManiaWorld world) {
        this.world = world;
    }

    public boolean checkAchievement () {
        goldHold = world.getGold();
        doggieCoinHold = world.getCoinAmount();
        loop = world.getLoop();
        
        if (
            thirtyKillsAchievement() ||
            bossKillAchievement() ||
            hundredThousandGoldAchievement() ||
            thirtyLoopsAchievement() ||
            tenDoggieCoinAchievement()
        ) {
            return true;
        } else {
            return false;
        }
    }

    public void incrementNumOfKill() {
        numOfKill++;
    }

    public void setIsBossKilled(Boolean b) {
        isBossKilled = b;
    }

    private boolean thirtyKillsAchievement() {
        if (achievementKill) return false;
        if (numOfKill >= KILL_NUM_ACHIEVE) {
            achievementKill = true;
            return true;
        }
        return false;
    }

    private boolean bossKillAchievement() {
        if (achievementBoss) return false;
        if (isBossKilled) {
            achievementBoss = true;
            return true;
        }
        return false;
    }

    private boolean hundredThousandGoldAchievement() {
        if (achievementGold) return false;
        if (goldHold >= GOLD_ACHIEVE) {
            achievementGold = true;
            return true;
        }
        return false;
    }
    
    private boolean thirtyLoopsAchievement() {
        if (achievementLoops) return false;
        if (loop >= LOOPS_ACHIEVE) {
            achievementLoops = true;
            return true;
        }
        return false;
    }

    private boolean tenDoggieCoinAchievement() {
        if (achievementDoggie) return false;
        if (doggieCoinHold >= DOGGIE_ACHIEVE) {
            achievementDoggie = true;
            return true;
        }
        return false;
    }
}
