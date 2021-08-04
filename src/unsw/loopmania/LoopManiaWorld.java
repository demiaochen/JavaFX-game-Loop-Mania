package unsw.loopmania;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.entity.movingEntity.basicAlly.BasicAlly;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.movingEntity.basicAlly.DoggiePet;
import unsw.loopmania.entity.movingEntity.basicAlly.Soldier;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;
import unsw.loopmania.entity.movingEntity.basicEnemy.Doggie;
import unsw.loopmania.entity.movingEntity.basicEnemy.ElanMuske;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.entity.staticEntity.building.Building;
import unsw.loopmania.entity.staticEntity.building.buildingNotOnPath.CampfireBuilding;
import unsw.loopmania.entity.staticEntity.card.BarracksCard;
import unsw.loopmania.entity.staticEntity.card.CampfireCard;
import unsw.loopmania.entity.staticEntity.card.Card;
import unsw.loopmania.entity.staticEntity.card.TowerCard;
import unsw.loopmania.entity.staticEntity.card.TrapCard;
import unsw.loopmania.entity.staticEntity.card.VampireCastleCard;
import unsw.loopmania.entity.staticEntity.card.VillageCard;
import unsw.loopmania.entity.staticEntity.card.ZombiePitCard;
import unsw.loopmania.entity.staticEntity.equipment.Anduril;
import unsw.loopmania.entity.staticEntity.equipment.Armour;
import unsw.loopmania.entity.staticEntity.equipment.Equipment;
import unsw.loopmania.entity.staticEntity.equipment.Helmet;
import unsw.loopmania.entity.staticEntity.equipment.RareItem;
import unsw.loopmania.entity.staticEntity.equipment.Shield;
import unsw.loopmania.entity.staticEntity.equipment.Staff;
import unsw.loopmania.entity.staticEntity.equipment.Stake;
import unsw.loopmania.entity.staticEntity.equipment.Sword;
import unsw.loopmania.entity.staticEntity.equipment.TreeStump;
import unsw.loopmania.entity.staticEntity.staticEntityOnPath.Gold;
import unsw.loopmania.entity.staticEntity.staticEntityOnPath.HealthPotion;
import unsw.loopmania.entity.staticEntity.staticEntityOnPath.StaticEntityOnPath;
import unsw.loopmania.ui.achievement.Achievement;
import unsw.loopmania.ui.game.LoopManiaWorldController;
import unsw.loopmania.util.PathPosition;
import unsw.loopmania.util.Sound;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {

    private Achievement achievement;

    // each time visit a shop there are buying limitations determined by mode
    private int healthPotionBuyLimit;
    private int protectiveGearBuyLimit;
    // confusing mode control
    private boolean confusingMode;

    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;

    // goal to complete
    private int finalCycles;
    private int finalExperience;
    private boolean killDoggie = false;
    private boolean killElan = false;
    // condition is either "AND" or "OR"
    private String condition1;
    private String condition2;

    private boolean oneRingAvailable; //whether "the one ring" is available in this game
    private boolean andurilAvailable; //whether "Anduril, Flame of the West" is available in this game
    private boolean treeStumpAvailable; //whether "Tree Stump" is available in this game

    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;


    private StringProperty gold;
    private StringProperty exp;
    private StringProperty healthPotionNum;
    private StringProperty loop; //loop character completes
    private StringProperty coinAmount;
    private int coinValue;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private Character character;

    private DoggiePet doggiePet;

    // TODO = add more lists for other entities, for equipped inventory items, etc...

    // TODO = expand the range of enemies
    private List<BasicEnemy> enemies;

    // TODO = expand the range of cards
    private List<Card> cardEntities;

    // TODO = expand the range of items
    private List<Equipment> unequippedInventoryItems;

    // TODO = expand the range of buildings
    private List<Building> buildingEntities;
    private List<BasicAlly> allies; //character at index 0, follows by soldiers
    private List<StaticEntityOnPath> entitiesOnPath;
    private List<BasicEnemy> battleEnemies; //enemies in one battle
    private List<BasicEnemy> aliveEnemies; //alive enemies in that battle

    private boolean isLose;

    private Level playerLevel;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    /**
     * create the world (constructor)
     * 
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        setupMode("standard");
        this.width = width;
        this.height = height;
        nonSpecifiedEntities = new ArrayList<>();
        enemies = new ArrayList<>();
        allies = new ArrayList<>();
        cardEntities = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        entitiesOnPath = new ArrayList<>();
        battleEnemies = new ArrayList<BasicEnemy>();
        aliveEnemies = new ArrayList<BasicEnemy>();
        gold = new SimpleStringProperty("0");
        exp = new SimpleStringProperty("0");
        healthPotionNum = new SimpleStringProperty("0");
        loop = new SimpleStringProperty("0");
        coinAmount = new SimpleStringProperty("0");
        coinValue = 0;
        confusingMode = false;
        oneRingAvailable = false;
        andurilAvailable = false;
        treeStumpAvailable = false;
        doggiePet = null;
        achievement = new Achievement(this);
        isLose = false;
        playerLevel = new Level();
    }

    public void setKillBoss(BasicEnemy e) {
        if(e instanceof Doggie) {
            killDoggie = true;
        } else if(e instanceof ElanMuske) {
            killElan = true;
        }
    }

	public Achievement getAchievement() {
		return this.achievement;
	}
    
    public List<Pair<Integer, Integer>> getOrderedPath() {
        return this.orderedPath;
    }

    public int getCoinAmount() {
        return Integer.parseInt(coinAmount.get());
    }

    public void addCoinAmount(int amount) {
        int currentAmount = Integer.parseInt(coinAmount.get());
        currentAmount += amount;
        coinAmount.set(String.valueOf(currentAmount));
    }

    public int getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(int value) {
        coinValue = value;
    }

    public int getLevel() {
        return playerLevel.getLevel();
    }

    /**
     * @return width of pane
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return height of pane
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return amount of gold Character has
     */
    public int getGold() {
        return Integer.parseInt(gold.get());
    }

    /**
     * @return amount of experience Character has
     */
    public int getExp() {
        return Integer.parseInt(exp.get());
    }

    /**
     * @return number of loops have completed
     */
    public int getLoop() {
        return Integer.parseInt(loop.get());
    }

    /**
     * 
     * @return whether "the_one_ring" is available in this game
     */
    public boolean getOneRingAvailable() {
        return this.oneRingAvailable;
    }

    public boolean getAndurilAvailable() {
        return andurilAvailable;
    }

    public boolean getTreeStumpAvailable() {
        return treeStumpAvailable;
    }

    /** 
     * @return int
     */
    public int getHealthPotionBuyLimit() {
		return this.healthPotionBuyLimit;
	}

    /** 
     * @return int
     */
    public int getProtectiveGearBuyLimit() {
		return this.protectiveGearBuyLimit;
	}

    /**
     * @return number of potions
     */
    public int getHealthPotionNum() {
        return Integer.parseInt(healthPotionNum.get());
    }

    public Character getCharacter() {
        return this.character;
    }

    /**
     * @return Character's hp
     */
    public int getCharHP() {
        return character.getHP();
    }

    /**
     * @return Character's max hp
     */
    public int getCharMaxHP() {
        return character.getMaxHP();
    }

    /**
     * @return character's attack
     */
    public int getCharAttack() {
        return character.getAttack();
    }

    /**
     * @return character's defense
     */
    public int getCharDefense() {
        return character.getDefense();
    }

    // Following 3 methods were added for testing, subject to change, may not be necessary.
    public List<BasicEnemy> getEnemies() {
        return enemies;
    }

    public List<Soldier> getSoldiers() {
        List<Soldier> soldiers = new ArrayList<>();

        for(int i = 1; i < allies.size(); i++) {
            soldiers.add((Soldier)allies.get(i));
        }

        return soldiers;
    }

    public int getSoldierNum() {
        return allies.size() - 1;
    }

    /**
     * @return list of cards
     */
    public List<Card> getCardEntities() {
        return this.cardEntities;
    }

    /**
     * @return unequippedInventoryItems list
     */
    public List<Equipment> getUnequipped() {
        return this.unequippedInventoryItems;
    }

    /**
     * 
     * @return whether the mode is confusing mode
     */
    public boolean getConfusingMode() {
        return confusingMode;
    }

    public DoggiePet getDoggiePet() {
        return doggiePet;
    }

    public StringProperty getLevelProperty() {
        return playerLevel.getPropertyLevel();
    }

    public StringProperty getGoldProperty() {
        return gold;
    }

    public StringProperty getExpProperty() {
        return exp;
    }

    public StringProperty getHealthPotionNumProperty() {
        return healthPotionNum;
    }

    public StringProperty getLoopProperty() {
        return loop;
    }

    public StringProperty getCoinAmountProperty() {
        return coinAmount;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
        allies.add(character);
    }
    
    /**
     * Set the goal of the game
     * @param finalCycle
     * @param finalExp
     * @param condition
     */
    public void setGoal(int finalCycle, int finalExp, boolean killAllBosses, String condition1, String condition2) {
        this.finalCycles = finalCycle;
        this.finalExperience = finalExp;
        this.condition1 = condition1;
        this.condition2 = condition2;
        if(!killAllBosses) {
            this.killDoggie = true;
            this.killElan = true;
        }
    }

    /**
     * Set whether the_one_ring is available in this world
     * @param available
     */
    public void setOneRingAvailable(boolean available) {
        oneRingAvailable = available;
    }

    public void setAndurilAvailable(boolean available) {
        andurilAvailable = available;
    }

    public void setTreeStump(boolean available) {
        treeStumpAvailable = available;
    }
    
    /** 
     * Three mode available: survival, berserker, standard 
     * each mode places different limitations on buying quantity 
     * @param String mode, the mode of World
    */
    public void setupMode(String mode) {
        // no buy limitations by default
        healthPotionBuyLimit = Integer.MAX_VALUE;
        protectiveGearBuyLimit =  Integer.MAX_VALUE;
        confusingMode = false;
        switch(mode) 
        {
            case "standard":
                break;
            case "survival":
                healthPotionBuyLimit = 1;
                break;
            case "berserker":
                protectiveGearBuyLimit = 1;
                break;
            case "confusing":
                confusingMode = true;
                break;
            default:
                break;
        }
    }

    /**
     * doggie pet will at position 1 path tile behind character
     */
    public void setDoggiePet() {
        if(doggiePet != null) {return;}
        PathPosition position = character.getPathPosition();
        int currentPosition = position.getPositionOnPath();
        if(currentPosition == 0) {
            currentPosition = orderedPath.size() - 1;
        }
        else {
            currentPosition--;
        }
        doggiePet = new DoggiePet(new PathPosition(currentPosition, orderedPath));
    }

    /**
     * add gold
     * @param amount
     */
    public void addGold(int amount) {
        int currentAmount = Integer.parseInt(gold.get());
        currentAmount += amount;
        gold.set(String.valueOf(currentAmount));
    }

    /**
     * reduce the amount of the gold
     * @param price
     */
    public void reduceGold(int price) {
        int currentAmount = Integer.parseInt(gold.get());
        currentAmount -= price;
        gold.set(String.valueOf(currentAmount));
    }

    /**
     * add experience
     * @param amount
     */
    public void addExp(int amount) {
        int currentAmount = Integer.parseInt(exp.get());
        currentAmount += amount;
        exp.set(String.valueOf(currentAmount));
    }

    /**
     * @return list of buildings
     */
    public List<Building> getBuildings() {
        return this.buildingEntities;
    }

    /**
     * @return list of allies
     */
    public List<BasicAlly> getAllies() {
        return this.allies;
    }

    /**
     * Increase number of potions
     */
    public void addHealthPotion() {
        int currentAmount = Integer.parseInt(healthPotionNum.get());
        currentAmount++;
        healthPotionNum.set(String.valueOf(currentAmount));
    }

    /**
     * Reduce number of potions
     */
    public void reduceHealthPotion() {
        int currentAmount = Integer.parseInt(healthPotionNum.get());
        currentAmount--;
        healthPotionNum.set(String.valueOf(currentAmount));
    }

    /**
     * add loop number by 1
     */
    public void addLoop() {
        int currentAmount = Integer.parseInt(loop.get());
        currentAmount++;
        loop.set(String.valueOf(currentAmount));
    }

    /**
     * add enemies to the world
     * @param e
     */
    public void addEnemy(BasicEnemy e) {
        enemies.add(e);
    }

    /**
     * add alive enemies to the world
     * @param e
     */
    public void addAliveEnemy(BasicEnemy e) {
        aliveEnemies.add(e);
    }

    /**
     * add soldier to the world
     * @param s
     */
    public void addSoldier (Soldier s) {
        allies.add(s);
    }

    public void addCharacterMaxHP(int amount) {
        character.addMaxHP(amount);
    }

    public void minusCharacterMaxHP(int amount) {
        character.minusMaxHP(amount);
    }

    public void addCharacterAttack(int amount) {
        character.addAttack(amount);
    }

    public void minusCharacterAttack(int amount) {
        character.minusAttack(amount);
    }

    public void addCharacterDefense(int amount) {
        character.addDefense(amount);
    }

    public void minusCharacterDefense(int amount) {
        character.minusDefense(amount);
    }

    public void addCharacterReviveTime(int amount) {
        character.addReviveTime(amount);
    }

    public void minusCharacterReviveTime(int amount) {
        character.minusReviveTime(amount);
    }

    /**
     * Regain hp when using potion
     */
    public void usePotion() {
        int currentAmount = Integer.parseInt(healthPotionNum.get());
        if(currentAmount <= 0) {return;}
        currentAmount--;
        healthPotionNum.set(String.valueOf(currentAmount));
        character.restoreHP(character.getMaxHP());
    }

    /** 
     * Check if a item is protective gear (protective gear includes armour, helmets, and shields)
     * @param e
     * @return boolean
     */
    public boolean isItemProtectiveGear(Entity e) {
        if (e instanceof Armour || e instanceof Helmet || e instanceof Shield) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        // TODO = if more specialised types being added from main menu, add more methods like this with specific input types...
        nonSpecifiedEntities.add(entity);
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<BasicEnemy> possiblySpawnEnemies(){
        List<BasicEnemy> spawningEnemies = new ArrayList<>();
        for(Building b: buildingEntities) {
            BasicEnemy e = b.spawnEnemy(orderedPath, getLoop(), completeLoop());
            if(e != null) {
                enemies.add(e);
                spawningEnemies.add(e);
            }
        }

        List<BasicEnemy> basicSpawningEnemies = basicEnemySpawn();
        for(BasicEnemy e: basicSpawningEnemies) {
            enemies.add(e);
            spawningEnemies.add(e);
        }

        return spawningEnemies;
    }

    /**
     * Randomly generate entity on the path
     */
    public List<StaticEntityOnPath> possiblySpawnEntity() {
        List<StaticEntityOnPath> l = new ArrayList<>();

        Random r = new Random();
        if(r.nextInt(10) != 0) {return l;} // 1/10 chance to respawn sth. on path

        int index = r.nextInt(orderedPath.size());
        int x = orderedPath.get(index).getValue0();
        int y = orderedPath.get(index).getValue1();

        switch(r.nextInt(10)) {
            case 0:
                Gold e = new Gold(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                l.add(e);
                entitiesOnPath.add(e);
                break;
            case 1:
                HealthPotion h = new HealthPotion(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
                l.add(h);
                entitiesOnPath.add(h);
                break;
        }

        return l;
    }

    /**
     * Series of actions when Character pass through the entity on the path
     */
    public void passThroughPathEntity() {
        for(int i = 0; i < entitiesOnPath.size(); i++) {
            StaticEntityOnPath entity = entitiesOnPath.get(i);
            if(entity.getX() == character.getX() && entity.getY() == character.getY()) {
                Sound.playSound("pick_up_gold.mp3", 0.03);
                entity.effect(this);
                entity.destroy();
                entitiesOnPath.remove(i);
                i--;
            }
        }
    }

    /**
     * Series of actions when Character pass through building on the path
     */
    public void passThroughBuilding() {
        for(int i = buildingEntities.size() - 1; i >= 0; i--) {
            Building b = buildingEntities.get(i);
            b.effect(this);
        }
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    public void killEnemy(BasicEnemy enemy) {
        achievement.incrementNumOfKill();
        if (enemy instanceof ElanMuske) {
            achievement.setIsBossKilled(true);
        }
        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * kill all enemies except bosses in enemies list
     * @return killed enemies
     */
    public List<BasicEnemy> killAllNonBossEnemy() {
        List<BasicEnemy> list = new ArrayList<>();
        for(int i = 0; i < enemies.size(); i++) {
            BasicEnemy e = enemies.get(i);
            if(e instanceof Doggie || e instanceof ElanMuske) {continue;}
            list.add(e);
            killEnemy(e);
            i--;
        }
        return list;
    }

    
    /**
     * kill the given ally in allies List
     */
    public void killAlly(BasicAlly ally) {
        ally.destroy();
        if(!(ally instanceof Character)) {
            allies.remove(ally);
        }
    }

    /**
     * remove given building from buildingEntities
     * @param Building b
     */
    public void removeBuilding(Building b) {
        b.destroy();
        buildingEntities.remove(b);
    }

    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<BasicEnemy> runBattles() {
        // Adding enemies
        // Enemies in suitable range will be added to battleEnemies
        for (BasicEnemy e: enemies){
            // Find an enemy to start a battle, only one battle will start in each world state
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius
            if ((Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) <= Math.pow(e.getBattleRadius(), 2))){
                battleEnemies.add(e); // Add this enemy to battle ArrayList
                // Find all support enemies
                for (BasicEnemy eSup: enemies) {
                    if ((Math.pow((character.getX()-eSup.getX()), 2) +  Math.pow((character.getY()-eSup.getY()), 2) 
                    <= Math.pow(eSup.getSupportRadius(), 2)) && eSup != e) {
                        battleEnemies.add(eSup);
                    }
                }
                break;
            }
        }
        
        copyEnemies();

        while(aliveEnemies.size() > 0) {
            //buildings attack first
            for (int i = 0; i < buildingEntities.size(); i++) {
                if(aliveEnemies.size() == 0) {break;}
                Building b = buildingEntities.get(i);
                BasicEnemy e = aliveEnemies.get(0);
                if(Math.pow((b.getX()-e.getX()), 2) + Math.pow((b.getY()-e.getY()), 2) <= Math.pow(b.getEffectRadius(), 2)) {
                    b.doDamage(e); //building do damage
                }
                if(e.getHP() <= 0) {
                    setKillBoss(e);
                    removeFromAliveEnemies(e);
                }
            }

            //doggie pet attacks then
            if(aliveEnemies.size() > 0 && doggiePet != null) {
                BasicEnemy e = aliveEnemies.get(0);
                doggiePet.doDamage(e);
                if(e.getHP() <= 0) {
                    setKillBoss(e);
                    removeFromAliveEnemies(e);
                }
            }

            // alive allies do attack then
            for (int i = 0; i < allies.size(); i++) {
                BasicAlly ally = allies.get(i);
                if(aliveEnemies.size() == 0) {break;}
                BasicEnemy e = aliveEnemies.get(0);
                if(ally instanceof Character) {
                    CharacterAttackBoss characterAttackBoss = new CharacterAttackBoss(character);
                    characterAttackBoss.doDamage(e, this, new Random());
                }
                else {
                    ally.doDamage(e, this, new Random());
                }
                
                if(e.getHP() <= 0) {
                    setKillBoss(e);
                    removeFromAliveEnemies(e);
                }
            }

            // alive enemies do attack
            for (int i = 0; i < aliveEnemies.size(); i++) {
                BasicEnemy e = aliveEnemies.get(i);
                BasicAlly ally = allies.get(allies.size()-1); // get last added soldier or character (if no soldier)
                if(e instanceof Doggie || e instanceof ElanMuske) {
                    BossAttackCharacter bossAttackCharacter = new BossAttackCharacter(e);
                    bossAttackCharacter.doDamage(ally);
                    if(ally.getHP() <= 0) {
                        killAlly(ally);
                    }
                    else{
                        bossAttackCharacter.specialAttack(ally, this, new Random());
                    }
                }
                else {
                    e.doDamage(ally);
                    if(ally.getHP() <= 0) {
                        killAlly(ally);
                        if (ally instanceof Character) {
                            if (character.getReviveTime() < 0) {
                                isLose = true;
                            }
                            break;
                        }
                    }
                    else{
                        e.specialAttack(ally, this, new Random());
                    }
                }
            }

            //check for soldiers which end the trance and turn back to enemies
            for (int i = 1; i < allies.size(); i++) {
                Soldier s = (Soldier)allies.get(i);
                s.nextRound();
                if(s.getTranceRount() == 0) {
                    aliveEnemies.add(s.getEnemy());
                    killAlly(s);
                    i--;
                }
            }

            if(character.getStunnedRound() > 0) {
                character.reduceStunnedRound();
            }
        }

        // doggie shouldn't disappear if hp > 0
        for(BasicEnemy e: battleEnemies) {
            if(e instanceof Doggie && e.getHP() > 0) {
                battleEnemies.remove(e);
            }
        }

        //all battle enemies should be killed after the battle
        //(include enemies which are soldiers currently)
        //otherwise game will lose already
        for(BasicEnemy e: battleEnemies) {
            killEnemy(e);
        }

        List<BasicEnemy> list = new ArrayList<BasicEnemy>(battleEnemies);
        emptyEnemyList(battleEnemies);
        emptyEnemyList(aliveEnemies);
        removeSoldierFromEnemy();

        return list;
        
    }

    /**
     * copy all enemies in battleEnemies to aliveEnemies
     */
    public void copyEnemies() {
        for(BasicEnemy e: battleEnemies) {
            aliveEnemies.add(e);
        }
    }

    /**
     * empty a list contains basicEnemy
     * @param list
     */
    public void emptyEnemyList(List<BasicEnemy> list) {
        while(list.size() > 0) {
            list.remove(0);
        }
    }

    /**
     * remove given enemy from aliveEnemies
     * @param e
     */
    public void removeFromAliveEnemies(BasicEnemy e) {
        aliveEnemies.remove(e);
    }

    /**
     * remove all soldiers in allies which is created by a trance
     * (i.e. tranceRound >= 0)
     */
    public void removeSoldierFromEnemy() {
        for(int i = 1; i < allies.size(); i++) {
            Soldier s = (Soldier)allies.get(i);
            if(s.getTranceRount() >= 0 || s.getEnemy() != null) {
                killAlly(s);
                i--;
            }
        }
    }

    /**
     * spawn a random card in the world and return the card entity
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public Card loadCard(){
        discardCard();

        Card card;
        Random r = new Random();
        switch(r.nextInt(7)) {
            case 0:
                card = new VampireCastleCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
                break;
            case 1:
                card = new ZombiePitCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
                break;
            case 2:
                card = new TowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
                break;
            case 3:
                card = new VillageCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
                break;
            case 4:
                card = new BarracksCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
                break;
            case 5:
                card = new TrapCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
                break;
            default:
                card = new CampfireCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
                break;
        }
        cardEntities.add(card);
        return card;
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index){
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * if adding more cards than have, remove the oldest card
     * give some cash/experience/item rewards for the discarding of the oldest card
     */
    private void discardCard() {
        if (cardEntities.size() >= getWidth()){
            addGold(200);
            addExp(200);
            removeCard(0);
        }
    }

    /**
     * spawn a random equipment in the world and return the entity
     * @return an equipment to be spawned in the controller as a JavaFX node
     */
    public Equipment addUnequippedEquip(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            addGold(200);
            addExp(200);
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        SimpleIntegerProperty x = new SimpleIntegerProperty(firstAvailableSlot.getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(firstAvailableSlot.getValue1());

        Random r = new Random();
        
        // 1/4 chance of getting a rare item
        if(r.nextInt(5) == 0 && andurilAvailable && treeStumpAvailable) {
            RareItem equipment;
            switch(r.nextInt(2)) {
                case 0:
                    equipment = new Anduril(x, y);
                    if(getConfusingMode()) {
                        if(r.nextInt(2) == 0) {
                            equipment.setTheOneRingEffect(true);
                        }
                        else{
                            equipment.setTreeStumpEffect(true);
                        }
                    }
                    break;
                default:
                    equipment = new TreeStump(x, y);
                    if(getConfusingMode()) {
                        if(r.nextInt(2) == 0) {
                            equipment.setTheOneRingEffect(true);
                        }
                        else{
                            equipment.setAndurilEffect(true);
                        }
                    }
                    break;
            }
            if(this.unequippedInventoryItems.size() == 17) {
                removeItemByPositionInUnequippedInventoryItems(0);
            }
            unequippedInventoryItems.add(equipment);
            return equipment;
        }

        Equipment equipment;
        // now we insert a new equipment, as we know we h  ave at least made a slot available...
        switch(r.nextInt(6)) {
            case 0:
                equipment = new Sword(x, y);
                break;
            case 1:
                equipment = new Stake(x, y);
                break;
            case 2:
                equipment = new Staff(x, y);
                break;
            case 3:
                equipment = new Armour(x, y);
                break;
            case 4:
                equipment = new Shield(x, y);
                break;
            default:
                equipment = new Helmet(x, y);
                break;
        }

        if(this.unequippedInventoryItems.size() == 17) {
            removeItemByPositionInUnequippedInventoryItems(0);
        }
        unequippedInventoryItems.add(equipment);
        return equipment;
    }

    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public void runTickMoves(){
        character.moveDownPath();
        moveSoldiers();

        if(doggiePet != null) {
            doggiePet.moveDownPath();
        }

        moveBasicEnemies();
        if(character.getX() == orderedPath.get(0).getValue0() && character.getY() == orderedPath.get(0).getValue1()) {
            addLoop();
            if (getLoop() % 5 == 0 && getLoop()!= 0) {  // level up enemies every 5 loops
                // extension to level up enemies 
            }
        }

        playerLevel.updateLevel(this, character);
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    private void removeUnequippedInventoryItem(Entity item){
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * remove selled item
     * @param item
     */
    public void removeSelledItem(Entity item) {
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    private Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Entity e: unequippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the unequipped inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index){
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<unequippedInventoryHeight; y++){
            for (int x=0; x<unequippedInventoryWidth; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * @return first available slot
     */
    public Pair<Integer, Integer> getAvailableSlot() {
        return getFirstAvailableSlotForItem();
    }

    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x){
        for (Card c: cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    /**
     * move all enemies
     */
    private void moveBasicEnemies() {
        for (BasicEnemy e: enemies){
            e.move(this);
        }
    }

    /**
     * move all soldiers
     */
    private void moveSoldiers() {
        if(allies.size() <= 1) {return;}
        for(int i = 1; i < allies.size(); i++) {
            Soldier s = (Soldier)allies.get(i);
            s.move();
        }
    }

    private int chanceDoggie = 100;
    private int chanceElan = 100;
    /**
     * 
     * @return list of enemies which spawn in world (only Slug currently)
     */
    private List<BasicEnemy> basicEnemySpawn(){
        List<BasicEnemy> spawningEnemies = new ArrayList<>();
        Random r = new Random();

        if(r.nextInt(10) != 0) {return spawningEnemies;}

        
        int index = r.nextInt(orderedPath.size());
        Slug s = new Slug(new PathPosition(index, orderedPath));
        if(getLoop() > 20) {
            index = r.nextInt(orderedPath.size());
            Random spawn = new Random();
            if(spawn.nextInt(chanceDoggie) == 0) {
                Doggie d = new Doggie(new PathPosition(index, orderedPath));
                Random randomValue = new Random();
                coinValue = randomValue.nextInt(49999) + 1;
                spawningEnemies.add(d);
                chanceDoggie = 100;
            } else {
                if (chanceDoggie >= 5) chanceDoggie -= 3;
            }
        }
        if(getLoop() > 40 && Integer.parseInt(exp.get()) >= 10000) {
            index = r.nextInt(orderedPath.size());
            Random spawn = new Random();
            if(spawn.nextInt(chanceElan) == 0) {
                ElanMuske e = new ElanMuske(new PathPosition(index, orderedPath)); 
                Random randomValue = new Random();
                coinValue = randomValue.nextInt(20000) + 30000;
                spawningEnemies.add(e);
                chanceElan = 100;
            } else {
                if(chanceElan >= 5) chanceElan -= 3;
            }
        }
        
        spawningEnemies.add(s);
        return spawningEnemies;  
    }

    /**
     * remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {        
        // start by getting card
        Card card = null;
        for (Card c: cardEntities){
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)){
                card = c;
                break;
            }
        }
        
        // now spawn building
        Building newBuilding = card.createBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));

        if(!newBuilding.canBuild(buildingNodeX, buildingNodeY, orderedPath)) {
            return null;
        }
        buildingEntities.add(newBuilding);

        // destroy the card
        card.destroy();
        cardEntities.remove(card);
        shiftCardsDownFromXCoordinate(cardNodeX);

        return newBuilding;
    }

    /**
     * Series of actions after Character reach hero's castle
     * @param controller
     * @throws IOException
     */
    public void characterReachCastle(LoopManiaWorldController controller) throws IOException {
        Integer[] shopCycle = {1,3,6,10,15,21,28,36,45};
        if(completeLoop()) {
            // process shop function
            boolean canShop = Arrays.asList(shopCycle).contains(getLoop());
            if(canShop) {
                controller.switchToShop();
            }
        }
    }

    /**
     * Check wheather Character completed the loop
     * @return
     */
    public boolean completeLoop() {
        if(character.getX() == orderedPath.get(0).getValue0() && character.getY() == orderedPath.get(0).getValue1()) {
            return true;
        }
        return false;
    }

  
    /**
     * Add new equiment to the world
     * @param e
     */
    public void addToUnequipped(Equipment e) {
        if(this.unequippedInventoryItems.size() == 17) {
            removeItemByPositionInUnequippedInventoryItems(0);
        }
        this.unequippedInventoryItems.add(e);
    }

    /**
     * Add new building to the world
     * @param b
     */
    public void addBuilding(Building b) {
        this.buildingEntities.add(b);
    }

    /**
     * 
     * @param e
     * @return whether Entity e is within radius of any campfire
     */
    public boolean withinCampfireRadius(Entity e) {
        for(Building b: buildingEntities) {
            if(b instanceof CampfireBuilding) {
                if(Math.pow((b.getX()-e.getX()), 2) + Math.pow((b.getY()-e.getY()), 2) <= Math.pow(b.getEffectRadius(), 2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return whether player reaches the goal
     */
    public boolean isReachGoal() {
        int expAmount = Integer.parseInt(exp.get());
        if(condition1.equals("AND")) {
            if(condition2.equals("AND")) {
                return getLoop() == finalCycles && (expAmount == finalExperience && (killDoggie && killElan));
            } else {
                return getLoop() == finalCycles && (expAmount == finalExperience || (killDoggie && killElan));
            }
        } else if(condition1.equals("OR")) {
            if(condition2.equals("AND")) {
                return getLoop() == finalCycles || (expAmount == finalExperience && (killDoggie && killElan));
            } else {
                return getLoop() == finalCycles || (expAmount == finalExperience || (killDoggie && killElan));
            }
        }
        return false;
    }


    public boolean getIsLose() {
        return isLose;
    }

   
}