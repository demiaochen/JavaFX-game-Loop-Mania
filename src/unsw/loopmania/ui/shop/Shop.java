package unsw.loopmania.ui.shop;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.entity.staticEntity.equipment.Armour;
import unsw.loopmania.entity.staticEntity.equipment.Equipment;
import unsw.loopmania.entity.staticEntity.equipment.Helmet;
import unsw.loopmania.entity.staticEntity.equipment.Shield;
import unsw.loopmania.entity.staticEntity.equipment.Staff;
import unsw.loopmania.entity.staticEntity.equipment.Stake;
import unsw.loopmania.entity.staticEntity.equipment.Sword;

public class Shop {
    private LoopManiaWorld world;

    private BooleanProperty item1;
    private BooleanProperty item2;
    private BooleanProperty item3;

    private String item1Name;
    private String item2Name;

    private int availablePotion;
    private int availableEquipment;

    public Shop(LoopManiaWorld world) {
        this.world = world;
        BooleanProperty temp1 = new SimpleBooleanProperty();
        temp1.set(false);
        BooleanProperty temp2 = new SimpleBooleanProperty();
        temp2.set(false);
        BooleanProperty temp3 = new SimpleBooleanProperty();
        temp3.set(false);
        this.item1 = temp1;
        this.item2 = temp2;
        this.item3 = temp3;
        this.availablePotion = world.getHealthPotionBuyLimit();
        this.availableEquipment = world.getProtectiveGearBuyLimit();
    }

    public int getCoinPrice() {
        return world.getCoinValue();
    }

    public int getCoinAmount() {
        return world.getCoinAmount();
    }

    /**
     * @return number of gold in the world
     */
    public int getGold() {
        return this.world.getGold();
    }

    /**
     * series of actions when buying
     */
    public void buy() {
        if(item1.get()) {
            Pair <Integer, Integer> pair = this.world.getAvailableSlot();
            SimpleIntegerProperty x = new SimpleIntegerProperty(pair.getValue0());
            SimpleIntegerProperty y = new SimpleIntegerProperty(pair.getValue1());
            Equipment equipment = null;

            if(item1Name.equals("Sword") && this.world.getGold() >= 1500) {
                this.world.reduceGold(1500);
                equipment = new Sword(x, y);
            } else if(item1Name.equals("Stake") && this.world.getGold() >= 1500) {
                this.world.reduceGold(1500);
                equipment = new Stake(x, y);
            } else if(item1Name.equals("Staff") && this.world.getGold() >= 2000) {
                this.world.reduceGold(2000);
                equipment = new Staff(x, y);
            } else if(item1Name.equals("Armour") && this.world.getGold() >= 3000) {
                this.world.reduceGold(3000);
                equipment = new Armour(x, y);
            } else if(item1Name.equals("Shield") && this.world.getGold() >= 3000) {
                this.world.reduceGold(3000);
                equipment = new Shield(x, y);
            } else if(item1Name.equals("Helmet") && this.world.getGold() >= 3000) {
                this.world.reduceGold(3000);
                equipment = new Helmet(x, y);
            }
            availableEquipment--;
            this.world.addToUnequipped(equipment);
        } else if(item2.get()) {
            Pair <Integer, Integer> pair = this.world.getAvailableSlot();
            SimpleIntegerProperty x = new SimpleIntegerProperty(pair.getValue0());
            SimpleIntegerProperty y = new SimpleIntegerProperty(pair.getValue1());
            Equipment equipment = null;

            if(item2Name.equals("Sword") && this.world.getGold() >= 1500) {
                this.world.reduceGold(1500);
                equipment = new Sword(x, y);
            } else if(item2Name.equals("Stake") && this.world.getGold() >= 1500) {
                this.world.reduceGold(1500);
                equipment = new Stake(x, y);
            } else if(item2Name.equals("Staff") && this.world.getGold() >= 2000) {
                this.world.reduceGold(2000);
                equipment = new Staff(x, y);
            } else if(item2Name.equals("Armour") && this.world.getGold() >= 3000) {
                this.world.reduceGold(3000);
                equipment = new Armour(x, y);
            } else if(item2Name.equals("Shield") && this.world.getGold() >= 3000) {
                this.world.reduceGold(3000);
                equipment = new Shield(x, y);
            } else if(item2Name.equals("Helmet") && this.world.getGold() >= 3000) {
                this.world.reduceGold(3000);
                equipment = new Helmet(x, y);
            }
            availableEquipment--;
            this.world.addToUnequipped(equipment);
        } else if(item3.get() && this.availablePotion > 0) {
            if(this.world.getGold() >= 1000) {
                this.world.reduceGold(1000);
                this.world.addHealthPotion();
                this.availablePotion --;
            }
        }
    }

    /**
     * series of actions when selling
     */
    public void sell() {
        if(item1.get()) {
            List<Equipment> unequippedList = this.world.getUnequipped();
            if(item1Name.equals("Sword") && countHold("Sword") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Sword) {
                        this.world.addGold(1500);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            } else if(item1Name.equals("Stake") && countHold("Stake") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Stake) {
                        this.world.addGold(1500);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            } else if(item1Name.equals("Staff") && countHold("Staff") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Staff) {
                        this.world.addGold(2000);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            } else if(item1Name.equals("Armour") && countHold("Armour") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Armour) {
                        this.world.addGold(3000);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            } else if(item1Name.equals("Shield") && countHold("Shield") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Shield) {
                        this.world.addGold(3000);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            } else if(item1Name.equals("Helmet") && countHold("Helmet") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Helmet) {
                        this.world.addGold(3000);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            }
        } else if(item2.get()) {
            List<Equipment> unequippedList = this.world.getUnequipped();
            if(item2Name.equals("Sword") && countHold("Sword") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Sword) {
                        this.world.addGold(1500);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            } else if(item2Name.equals("Stake") && countHold("Stake") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Stake) {
                        this.world.addGold(1500);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            } else if(item2Name.equals("Staff") && countHold("Staff") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Staff) {
                        this.world.addGold(2000);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            } else if(item2Name.equals("Armour") && countHold("Armour") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Armour) {
                        this.world.addGold(3000);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            } else if(item2Name.equals("Shield") && countHold("Shield") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Shield) {
                        this.world.addGold(3000);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            } else if(item2Name.equals("Helmet") && countHold("Helmet") > 0) {
                for(Entity e: unequippedList) {
                    if(e instanceof Helmet) {
                        this.world.addGold(3000);
                        this.world.removeSelledItem(e);
                        break;
                    }
                }
            }
        } else if(item3.get()) {
            if(this.world.getHealthPotionNum() > 0) {
                this.world.reduceGold(-1000);
                this.world.reduceHealthPotion();
            }
        }
    }

    public BooleanProperty item1Property() {
        return this.item1;
    }

    public BooleanProperty item2Property() {
        return this.item2;
    }

    public BooleanProperty item3Property() {
        return this.item3;
    }

    /**
     * @return number of available potions
     */
    public int getHealthPotionNum() {
        return this.world.getHealthPotionNum();
    }

    /**
     * actions when select first item in the shop
     * @param name
     */
    public void select1(String name) {
        this.item1Name = name;
        this.item1.set(true);
        this.item2.set(false);
        this.item3.set(false);
    }

    /**
     * actions when select second item in the shop
     * @param name
     */
    public void select2(String name) {
        this.item2Name = name;
        this.item1.set(false);
        this.item2.set(true);
        this.item3.set(false);
    }

    /**
     * actions when select third item in the shop
     * @param name
     */
    public void select3() {
        this.item1.set(false);
        this.item2.set(false);
        this.item3.set(true);
    }

    /**
     * set first item is not chosen
     */
    public void set1False() {
        this.item1.set(false);
    }

    /**
     * set second item is not chosen
     */
    public void set2False() {
        this.item2.set(false);
    }

    /**
     * set third item is not chosen
     */
    public void set3False() {
        this.item3.set(false);
    }

    /**
     * count number of equipment Character is hoiding
     * @param s
     * @return number of equipment Character is hoiding
     */
    public int countHold(String s) {
        int count = 0;
        if(s.equals("Sword")) {
            for(Entity e: this.world.getUnequipped()) {
                if(e instanceof Sword) {
                    count += 1;
                }
            }
        } else if(s.equals("Stake")) {
            for(Entity e: this.world.getUnequipped()) {
                if(e instanceof Stake) {
                    count += 1;
                }
            }
        } else if(s.equals("Staff")) {
            for(Entity e: this.world.getUnequipped()) {
                if(e instanceof Staff) {
                    count += 1;
                }
            }
        } else if(s.equals("Armour")) {
            for(Entity e: this.world.getUnequipped()) {
                if(e instanceof Armour) {
                    count += 1;
                }
            }
        } else if(s.equals("Shield")) {
            for(Entity e: this.world.getUnequipped()) {
                if(e instanceof Shield) {
                    count += 1;
                }
            }
        } else if(s.equals("Helmet")) {
            for(Entity e: this.world.getUnequipped()) {
                if(e instanceof Helmet) {
                    count += 1;
                }
            }
        }

        return count;
    }

    /**
     * @return number of available potions
     */
    public int getAvailablePotion() {
        return this.availablePotion;
    }

    /**
     * @return number of available equipments
     */
    public int getAvailableEquipment() {
        return this.availableEquipment;
    }

    /**
     * @return number of equipments in the backpack
     */
    public int getNumberOfUnequipped() {
        return this.world.getUnequipped().size();
    }

    public void sellCoin() {
        if(world.getCoinAmount() > 0) {
            world.addCoinAmount(-1);
            world.addGold(world.getCoinValue());
        }
    }
}
