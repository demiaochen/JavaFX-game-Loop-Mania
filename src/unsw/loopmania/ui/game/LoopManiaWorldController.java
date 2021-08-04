package unsw.loopmania.ui.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;
import org.javatuples.Pair;
import org.json.JSONObject;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.entity.movingEntity.basicEnemy.BasicEnemy;
import unsw.loopmania.entity.movingEntity.basicEnemy.Doggie;
import unsw.loopmania.entity.movingEntity.basicEnemy.ElanMuske;
import unsw.loopmania.entity.movingEntity.basicEnemy.Slug;
import unsw.loopmania.entity.movingEntity.basicEnemy.Vampire;
import unsw.loopmania.entity.movingEntity.basicEnemy.Zombie;
import unsw.loopmania.entity.staticEntity.building.Building;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.TowerBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.VampireCastleBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.VillageBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingAdjacentPath.ZombiePitBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingNotOnPath.CampfireBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingOnPath.BarracksBuilding;
import unsw.loopmania.entity.staticEntity.building.buildingOnPath.TrapBuilding;
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
import unsw.loopmania.entity.staticEntity.equipment.Shield;
import unsw.loopmania.entity.staticEntity.equipment.Staff;
import unsw.loopmania.entity.staticEntity.equipment.Stake;
import unsw.loopmania.entity.staticEntity.equipment.Sword;
import unsw.loopmania.entity.staticEntity.equipment.TheOneRing;
import unsw.loopmania.entity.staticEntity.equipment.TreeStump;
import unsw.loopmania.entity.staticEntity.staticEntityOnPath.Gold;
import unsw.loopmania.entity.staticEntity.staticEntityOnPath.HealthPotion;
import unsw.loopmania.entity.staticEntity.staticEntityOnPath.StaticEntityOnPath;
import unsw.loopmania.ui.DragIcon;
import unsw.loopmania.ui.MenuSwitcher;
import unsw.loopmania.util.Sound;

import java.util.EnumMap;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;


/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */

enum DRAGGABLE_TYPE{
    BARRACKS,
    CAMPFIRE,
    TOWER,
    TRAP,
    VAMPIRECASTLE,
    VILLAGE,
    ZOMBIEPIT,
    ITEM,
    SWORD,
    STAKE,
    STAFF,
    ARMOUR,
    SHIELD,
    HELMET,
    ANDURIL,
    TREESTUMP
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application thread:
 *     https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 *     Note in https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html under heading "Threading", it specifies animation timelines are run in the application thread.
 * This means that the starter code does not need locks (mutexes) for resources shared between the timeline KeyFrame, and all of the  event handlers (including between different event handlers).
 * This will make the game easier for you to implement. However, if you add time-consuming processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend:
 *     using Task https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by itself or within a Service https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 *     Tasks ensure that any changes to public properties, change notifications for errors or cancellation, event handlers, and states occur on the JavaFX Application thread,
 *         so is a better alternative to using a basic Java Thread: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
 *     The Service class is used for executing/reusing tasks. You can run tasks without Service, however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need to implement locks on resources shared with the application thread (i.e. Timeline KeyFrame and drag Event handlers).
 * You can check whether code is running on the JavaFX application thread by running the helper method printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using Platform.runLater https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 *     This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass, buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot stretches over the entire game world,
     * so we can detect dragging of cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

    @FXML
    private Text gold;

    @FXML
    private Text exp;

    @FXML
    private Text level;

    @FXML
    private Text healthPotionNum;

    @FXML
    private Text attack;

    @FXML
    private Text defense;

    @FXML
    private Text cycle;

    @FXML
    private Text soldierNum;

    @FXML
    private Text coinAmount;

    @FXML
    private Rectangle health;

    @FXML
    private Rectangle maxHealth;

    @FXML
    private Button useHealthPotion;

    @FXML
    private Text oneRingText;

    @FXML
    private ImageView ringImage;

    @FXML
    private Text techPointText;

    @FXML
    private Button winButton;

    @FXML
    private ImageView heartImage;

    @FXML
    private ImageView coinImage;

    @FXML 
    private ImageView background1;

    @FXML 
    private ImageView background2;

    @FXML 
    private ImageView background3;

    @FXML 
    private ImageView background4;

    @FXML 
    private ImageView background5;

    @FXML 
    private ImageView background6;

    @FXML 
    private ImageView background7;

    @FXML 
    private Button learn1;
    
    @FXML 
    private Button learn2;
    
    @FXML 
    private Button learn3;
    
    @FXML 
    private Button learn4;
    
    @FXML 
    private Button learn5;
    
    @FXML 
    private Button learn6;
    
    @FXML 
    private Button learn7;

    @FXML
    private Button mainMenu;

    @FXML
    private ImageView winImage;

    @FXML
    private ImageView loseImage;

    @FXML
    private AnchorPane winPane;

    @FXML
    private  Button trophies;

    @FXML
    private  Button resetSpeedButton;

    @FXML
    private  Button accelerateButton;

    // all image views including tiles, character, enemies, cards... even though cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;

    /**
     * runs the periodic game logic - second-by-second moving of character through maze, as well as enemies, and running of battles
     */
    private Timeline timeline;
    
    private Image barracksCardImage;
    private Image campfireCardImage;
    private Image towerCardImage;
    private Image trapCardImage;
    private Image vampireCastleCardImage;
    private Image villageCardImage;
    private Image zombiePitCardImage;

    private Image barracksImage;
    private Image campfireImage;
    private Image towerImage;
    private Image trapImage;
    private Image vampireCastleImage;
    private Image villageImage;
    private Image zombiePitImage;

    private Image swordImage;
    private Image stakeImage;
    private Image staffImage;
    private Image armourImage;
    private Image shieldImage;
    private Image helmetImage;
    private Image goldImage;
    private Image potionImage;
    private Image andurilImage;
    private Image treeStumpImage;

    private Image angelImage;

    private Image purpleBackGround;
    private Image whiteBackGround;

    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */
    // TODO = it would be a good idea for you to instead replace this with the building/item which should be dropped
    private ImageView currentlyDraggedImage;
    
    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged into the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged outside of the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;

    /**
     * object handling switching to the shop
     */
    private MenuSwitcher shopSwitcher;

    /**
     * object handling switching to the achievement
     */
    private MenuSwitcher achievementSwitcher;

    private Sound bgmPlayer;
    
    /**
     * the game speed in the game, the lower the faster.
     */
    private final double DEFAULT_GAME_SPEED = 0.3; 
    private Double gameSpeed;

    private int techPoint; //point to upgrade tech-tree
    private boolean magic; //whether have learnt the magic

    private boolean blinkTrophies; // whether blink button trophies
    

    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        gameSpeed = DEFAULT_GAME_SPEED;
        entityImages = new ArrayList<>(initialEntities);
        
        barracksCardImage = new Image((new File("src/images/barracks_card.png")).toURI().toString());
        campfireCardImage = new Image((new File("src/images/campfire_card.png")).toURI().toString());
        towerCardImage = new Image((new File("src/images/tower_card.png")).toURI().toString());
        trapCardImage = new Image((new File("src/images/trap_card.png")).toURI().toString());
        vampireCastleCardImage = new Image((new File("src/images/vampire_castle_card.png")).toURI().toString());
        villageCardImage = new Image((new File("src/images/village_card.png")).toURI().toString());
        zombiePitCardImage = new Image((new File("src/images/zombie_pit_card.png")).toURI().toString());

        barracksImage = new Image((new File("src/images/barracks.png")).toURI().toString());
        campfireImage = new Image((new File("src/images/campfire.png")).toURI().toString());
        towerImage = new Image((new File("src/images/tower.png")).toURI().toString());
        trapImage = new Image((new File("src/images/trap.png")).toURI().toString());
        vampireCastleImage = new Image((new File("src/images/vampire_castle_building_purple_background.png")).toURI().toString());
        villageImage = new Image((new File("src/images/village.png")).toURI().toString());
        zombiePitImage = new Image((new File("src/images/zombie_pit.png")).toURI().toString());

        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        goldImage = new Image((new File("src/images/gold_pile.png")).toURI().toString());
        potionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        andurilImage = new Image((new File("src/images/anduril_flame_of_the_west.jpg")).toURI().toString());
        treeStumpImage = new Image((new File("src/images/tree_stump.png")).toURI().toString());
        angelImage = new Image((new File("src/images/angel.gif")).toURI().toString());
        purpleBackGround = new Image((new File("src/images/purple_background.png")).toURI().toString());
        whiteBackGround = new Image((new File("src/images/white_background.png")).toURI().toString());

        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);

        gold = new Text();
        exp = new Text();
        healthPotionNum = new Text();
        cycle = new Text();
        coinAmount = new Text();
        level = new Text();

        bgmPlayer = Sound.soundList.get(0); // get the bgm player in static list soundList

        techPoint = 0;
        magic = false;

        blinkTrophies = false;
    }

    public void musicToggleAction() {
        bgmPlayer.toggleSound();
    }

    @FXML
    public void initialize() {
        // TODO = load more images/entities during initialization
        
        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // Add the ground first so it is below all other entities (inculding all the twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages){
            squares.getChildren().add(entity);
        }
        
        // add the ground underneath the cards
        for (int x=0; x<world.getWidth(); x++){
            ImageView groundView = new ImageView(pathTilesImage);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x=0; x<LoopManiaWorld.unequippedInventoryWidth; x++){
            for (int y=0; y<LoopManiaWorld.unequippedInventoryHeight; y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);

        if(world.getOneRingAvailable()) {
            ringImage.setVisible(true);
        }

        // animation of heart
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(heartImage);
        scale.setDuration(Duration.millis(700));
        scale.setCycleCount(ScaleTransition.INDEFINITE);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(0.15);
        scale.setByY(0.15);
        scale.setAutoReverse(true);
        scale.play();

        // animation of coin
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(coinImage);
        rotate.setDuration(Duration.millis(2500));
        rotate.setCycleCount(ScaleTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.play();

        gold.textProperty().bindBidirectional(world.getGoldProperty());
        exp.textProperty().bindBidirectional(world.getExpProperty());
        healthPotionNum.textProperty().bindBidirectional(world.getHealthPotionNumProperty());
        cycle.textProperty().bindBidirectional(world.getLoopProperty());
        coinAmount.textProperty().bindBidirectional(world.getCoinAmountProperty());
        level.textProperty().bindBidirectional(world.getLevelProperty());
    }

    /**
     * create and run the timer
     */
    public void startTimer(){
        System.out.println("starting timer");
        isPaused = false;
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(gameSpeed), event -> {
            world.runTickMoves();
            
            if (world.getAchievement().checkAchievement()) {
                Sound.playSound("achievement_received.wav", 0.1);
                blinkTrophies = true;
            }
            blinkButton(trophies, blinkTrophies);

            // update health potion button 
            if (world.getHealthPotionNum() == 0 ||
             world.getCharacter().getHP() == world.getCharacter().getMaxHP()) {
                useHealthPotion.setDisable(true);
            } else {
                useHealthPotion.setDisable(false);
            }

            if(world.getIsLose()) {
                pause();
                bgmPlayer.switchSong("game_lost_fallen_down.mp3");
                mainMenu.setDisable(true);
                useHealthPotion.setDisable(true);
                accelerateButton.setDisable(true);
                resetSpeedButton.setDisable(true);
                winPane.setDisable(false);
                loseImage.setVisible(true);
                winButton.setText("EXIT");
                winButton.setVisible(true);
                squares.setOpacity(0.5);
            }
            if(world.isReachGoal()) {
                pause();
                bgmPlayer.switchSong("game_win_megazone23.mp3");
                winPane.setDisable(false);
                mainMenu.setDisable(true);
                useHealthPotion.setDisable(true);
                accelerateButton.setDisable(true);
                resetSpeedButton.setDisable(true);
                squares.setOpacity(0.5);
                winButton.setText("EXIT");
                winButton.setVisible(true);
                winImage.setVisible(true);
            }
            try {
                world.characterReachCastle(this);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            List<BasicEnemy> defeatedEnemies = world.runBattles();
            for (BasicEnemy e: defeatedEnemies){
                reactToEnemyDefeat(e);
            }

            world.passThroughPathEntity();
            world.passThroughBuilding();

            // update slot
            List<Equipment> uneqippedList = new ArrayList<Equipment>();
            for(Equipment e: world.getUnequipped()) {
                uneqippedList.add(e);
            }
            world.getUnequipped().clear();
            for(Equipment e: uneqippedList) {
                world.getUnequipped().add(e);
                onLoad(e);
            }
            
            List<BasicEnemy> newEnemies = world.possiblySpawnEnemies();
            for (BasicEnemy newEnemy: newEnemies){
                onLoad(newEnemy);
            }

            List<StaticEntityOnPath> newEntitiesOnPath = world.possiblySpawnEntity();
            for(StaticEntityOnPath s: newEntitiesOnPath) {
                onLoad(s);
            }

            printThreadingNotes("HANDLED TIMER");
            health.setWidth(maxHealth.getWidth() * world.getCharHP() / world.getCharMaxHP());
            attack.setText(Integer.toString(world.getCharAttack()));
            defense.setText(Integer.toString(world.getCharDefense()));
            soldierNum.setText(Integer.toString(world.getSoldierNum()));
            techPointText.setText(String.valueOf(techPoint));

            useMagic();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * pause the execution of the game loop
     * the human player can still drag and drop items during the game pause
     */
    public void pause(){
        isPaused = true;
        System.out.println("pausing");
        timeline.stop();
    }

    public void terminate(){
        pause();
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * @param entity backend entity to be paired with view
     * @param view frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * load a random card from the world, and pair it with an image in the GUI
     */
    private void loadCard() {
        // TODO = load more types of card
        Card card = world.loadCard();
        onLoad(card);
    }

    /**
     * load an equipment from the world, and pair it with an image in the GUI
     */
    private void loadEquipment(){
        // TODO = load more types of weapon
        // start by getting first available coordinates
        Equipment equipment = world.addUnequippedEquip();
        onLoad(equipment);
    }

    private void useMagic() {
        if((!magic) || (new Random().nextInt(10) != 0)) {return;}
        ImageView image = new ImageView(new Image((new File("src/images/lightning.gif")).toURI().toString()));
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        translate.setDuration(Duration.millis(2500));
        translate.setByY(-64);
        squares.add(image, world.getCharacter().getX(), world.getCharacter().getY());
        translate.play();
        translate.setOnFinished((ActionEvent event) -> squares.getChildren().remove(image));

        //kill all enemies in world
        List<BasicEnemy> list = world.killAllNonBossEnemy();
        for(BasicEnemy e: list) {
            reactToEnemyDefeat(e);
        }
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */ 
    private void reactToEnemyDefeat(BasicEnemy enemy){
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...
        // TODO = provide different benefits to defeating the enemy based on the type of enemy

        // play sound 
        Sound.playSound("run_battle.wav");

        // animation when defeating enemies
        ImageView angelView = new ImageView(angelImage);
        FadeTransition fade = new FadeTransition();
        fade.setNode(angelView);
        fade.setDuration(Duration.millis(2500));
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(2);
        fade.setToValue(0);
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(angelView);
        translate.setDuration(Duration.millis(2500));
        translate.setByY(-64);
        squares.add(angelView, enemy.getX(), enemy.getY());
        fade.play();
        translate.play();

        JSONObject obj = enemy.battleReward();
        world.addGold(obj.getInt("gold"));
        world.addExp(obj.getInt("exp"));
        if(enemy instanceof Doggie) {
            world.addCoinAmount(obj.getInt("doggieCoin"));
            world.setDoggiePet();
            ImageView view = new ImageView(new Image((new File("src/images/doggie_pat.png")).toURI().toString()));
            addEntity(world.getDoggiePet(), view);
            squares.getChildren().add(view);
        }
        if(enemy instanceof ElanMuske) {
            Random value = new Random();
            // value is 0 ~ 1000
            world.setCoinValue(value.nextInt(1001));
        }
        int equipmentAmount = obj.getInt("equipmentAmount");
        int cardAmount = obj.getInt("cardAmount");
        for(int i = 0; i < equipmentAmount; i++) {
            loadEquipment();
        }
        for(int i = 0; i < cardAmount; i++) {
            loadCard();
        }

        // 1% can get a "the one ring"
        //"the one ring" is different from other rare items(can't be equipped in equipment list), so treated separately here
        Random r = new Random();
        if(world.getOneRingAvailable() && r.nextInt(100) == 0) {
            TheOneRing ring = new TheOneRing(null, null);
            if(world.getConfusingMode()) {
                if(r.nextInt(2) == 0) {
                    ring.setAndurilEffect(true);
                }
                else{
                    ring.setTreeStumpEffect(true);
                }
            }
            ring.equip(world.getCharacter()); //equip automatically
            oneRingText.setVisible(true);
            world.setOneRingAvailable(false);  // only one The One Ring can be get in a game
        }

        //get 1 tech point
        techPoint++;

        fade.setOnFinished((ActionEvent event) -> squares.getChildren().remove(angelView));
        translate.setOnFinished((ActionEvent event) -> squares.getChildren().remove(angelView));
    }

    /**
     * load a vampire castle card into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the cards GridPane.
     * @param vampireCastleCard
     */
    private void onLoad(Card card) {
        ImageView view = null;

        if(card instanceof BarracksCard) {
            view = new ImageView(barracksCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.BARRACKS, cards, squares);
        }
        else if(card instanceof CampfireCard) {
            view = new ImageView(campfireCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.CAMPFIRE, cards, squares);
        }
        else if(card instanceof TowerCard) {
            view = new ImageView(towerCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.TOWER, cards, squares);
        }
        else if(card instanceof TrapCard) {
            view = new ImageView(trapCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.TRAP, cards, squares);
        }
        else if(card instanceof VampireCastleCard) {
            view = new ImageView(vampireCastleCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.VAMPIRECASTLE, cards, squares);
        }
        else if(card instanceof VillageCard) {
            view = new ImageView(villageCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.VILLAGE, cards, squares);
        }
        else if(card instanceof ZombiePitCard) {
            view = new ImageView(zombiePitCardImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.ZOMBIEPIT, cards, squares);
        }

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addEntity(card, view);
        cards.getChildren().add(view);
    }

    /**
     * load an equipment into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the unequippedInventory GridPane.
     * @param sword
     */
    private void onLoad(Equipment equipment) {
        ImageView view = null;
        if(equipment instanceof Sword) {
            view = new ImageView(swordImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.SWORD, unequippedInventory, equippedItems);
        }
        else if(equipment instanceof Stake) {
            view = new ImageView(stakeImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.STAKE, unequippedInventory, equippedItems);
        }
        else if(equipment instanceof Staff) {
            view = new ImageView(staffImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.STAFF, unequippedInventory, equippedItems);
        }
        else if(equipment instanceof Armour) {
            view = new ImageView(armourImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.ARMOUR, unequippedInventory, equippedItems);
        }
        else if(equipment instanceof Shield) {
            view = new ImageView(shieldImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.SHIELD, unequippedInventory, equippedItems);
        }
        else if(equipment instanceof Helmet) {
            view = new ImageView(helmetImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.HELMET, unequippedInventory, equippedItems);
        }
        else if(equipment instanceof Anduril) {
            view = new ImageView(andurilImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.ANDURIL, unequippedInventory, equippedItems);
        }
        else if(equipment instanceof TreeStump) {
            view = new ImageView(treeStumpImage);
            addDragEventHandlers(view, DRAGGABLE_TYPE.TREESTUMP, unequippedInventory, equippedItems);
        }

        addEntity(equipment, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * load an enemy into the GUI
     * @param enemy
     */
    private void onLoad(BasicEnemy enemy) {
        ImageView view = null;
        if(enemy instanceof Vampire) {
            view = new ImageView(new Image((new File("src/images/vampire.png")).toURI().toString()));
        } else if(enemy instanceof Slug) {
            view = new ImageView(new Image((new File("src/images/slug.png")).toURI().toString()));
        } else if(enemy instanceof Zombie) {
            view = new ImageView(new Image((new File("src/images/zombie.gif")).toURI().toString()));
        } else if(enemy instanceof Doggie) {
            view = new ImageView(new Image((new File("src/images/doggie.png")).toURI().toString()));
        } else if(enemy instanceof ElanMuske) {
            view = new ImageView(new Image((new File("src/images/ElanMuske.png")).toURI().toString()));
        }
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    /**
     * load a building into the GUI
     * @param building
     */
    private void onLoad(Building building){
        ImageView view = null;
        if(building instanceof BarracksBuilding) {
            view = new ImageView(barracksImage);
        } else if(building instanceof CampfireBuilding) {
            view = new ImageView(campfireImage);
        } else if(building instanceof TowerBuilding) {
            view = new ImageView(towerImage);
        } else if(building instanceof TrapBuilding) {
            view = new ImageView(trapImage);
        } else if(building instanceof VampireCastleBuilding) {
            view = new ImageView(vampireCastleImage);
        } else if(building instanceof VillageBuilding) {
            view = new ImageView(villageImage);
        } else if(building instanceof ZombiePitBuilding) {
            view = new ImageView(zombiePitImage);
        }
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoad(StaticEntityOnPath entityOnPath) {
        ImageView view = null;
        if(entityOnPath instanceof Gold) {
            view = new ImageView(goldImage);
        } else if(entityOnPath instanceof HealthPotion) {
            view = new ImageView(potionImage);
        }
        addEntity(entityOnPath, view);
        squares.getChildren().add(view);
    }

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        // TODO = be more selective about where something can be dropped
        // for example, in the specification, villages can only be dropped on path, whilst vampire castles cannot go on the path

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // TODO = for being more selective about where something can be dropped, consider applying additional if-statement logic
                /*
                 *you might want to design the application so dropping at an invalid location drops at the most recent valid location hovered over,
                 * or simply allow the card/item to return to its slot (the latter is easier, as you won't have to store the last valid drop location!)
                 */
                if (currentlyDraggedType == draggableType){
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != targetGridPane && db.hasImage()){

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());

                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);

                        switch (draggableType){
                            case BARRACKS:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Building barracks = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                if(barracks == null) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                onLoad(barracks);
                                break;

                            case CAMPFIRE:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Building campfire = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                if(campfire == null) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                onLoad(campfire);
                                break;

                            case TOWER:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Building tower = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                if(tower == null) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                onLoad(tower);
                                break;

                            case TRAP:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Building trap = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                if(trap == null) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                onLoad(trap);
                                break;

                            case VAMPIRECASTLE:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Building vampireCastle = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                if(vampireCastle == null) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                onLoad(vampireCastle);
                                break;

                            case VILLAGE:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Building village = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                if(village == null) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                onLoad(village);
                                break;

                            case ZOMBIEPIT:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Building zombiePit = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                if(zombiePit== null) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                onLoad(zombiePit);
                                break;

                            case SWORD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                if(x!=0 || y!=0) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                Equipment sword = new Sword(null, null);
                                sword.equip(world.getCharacter());
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(image, x, y, 1, 1);
                                
                                break;

                            case STAKE:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                if(x!=0 || y!=0) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                Equipment stake = new Stake(null, null);
                                stake.equip(world.getCharacter());
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(image, x, y, 1, 1);
                                break;

                            case STAFF:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                if(x!=0 || y!=0) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                Equipment staff = new Staff(null, null);
                                staff.equip(world.getCharacter());
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(image, x, y, 1, 1);
                                break;

                            case ANDURIL:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                if(x!=0 || y!=0) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                Equipment anduril = new Anduril(null, null);
                                anduril.equip(world.getCharacter());
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(image, x, y, 1, 1);
                                break;

                            case ARMOUR:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                if(x!=1 || y!=0) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                Equipment armour = new Armour(null, null);
                                armour.equip(world.getCharacter());
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(image, x, y, 1, 1);
                                break;

                            case SHIELD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                if(x!=2 || y!=0) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                Equipment shield = new Shield(null, null);
                                shield.equip(world.getCharacter());
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(image, x, y, 1, 1);
                                break;

                            case TREESTUMP:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                if(x!=2 || y!=0) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                Equipment treeStump = new TreeStump(null, null);
                                treeStump.equip(world.getCharacter());
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(image, x, y, 1, 1);
                                break;

                            case HELMET:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                if(x!=3 || y!=0) {
                                    currentlyDraggedImage.setVisible(true);
                                    break;
                                }
                                Equipment helmet = new Helmet(null, null);
                                helmet.equip(world.getCharacter());
                                removeItemByCoordinates(nodeX, nodeY);
                                targetGridPane.add(image, x, y, 1, 1);
                                break;

                            default:
                                break;
                        }
                        
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>(){
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    if(event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()){
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null){
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != anchorPaneRoot && db.hasImage()){
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);
                        
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * remove the card from the world, and spawn and return a building instead where the card was dropped
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in the unequipped inventory gridpane
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    private void removeItemByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }

    /**
     * add drag event handlers to an ImageView
     * @param view the view to attach drag event handlers to
     * @param draggableType the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be dragged
     * @param targetGridPane the relevant gridpane to which the entity would be dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can detect it...
                currentlyDraggedType = draggableType;
                //Drag was detected, start drap-and-drop gesture
                //Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
    
                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType){
                    case BARRACKS:
                        draggedEntity.setImage(barracksCardImage);
                        break;
                    case CAMPFIRE:
                        draggedEntity.setImage(campfireCardImage);
                        break;
                    case TOWER:
                        draggedEntity.setImage(towerCardImage);
                        break;
                    case TRAP:
                        draggedEntity.setImage(trapCardImage);
                        break;
                    case VAMPIRECASTLE:
                        draggedEntity.setImage(vampireCastleCardImage);
                        break;
                    case VILLAGE:
                        draggedEntity.setImage(villageCardImage);
                        break;
                    case ZOMBIEPIT:
                        draggedEntity.setImage(zombiePitCardImage);
                        break;
                    case SWORD:
                        draggedEntity.setImage(swordImage);
                        break;
                    case STAKE:
                        draggedEntity.setImage(stakeImage);
                        break;
                    case STAFF:
                        draggedEntity.setImage(staffImage);
                        break;
                    case ARMOUR:
                        draggedEntity.setImage(armourImage);
                        break;
                    case SHIELD:
                        draggedEntity.setImage(shieldImage);
                        break;
                    case HELMET:
                        draggedEntity.setImage(helmetImage);
                        break;
                    case ANDURIL:
                        draggedEntity.setImage(andurilImage);
                        break;
                    case TREESTUMP:
                        draggedEntity.setImage(treeStumpImage);
                        break;
                    default:
                        break;
                }
                
                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n: targetGridPane.getChildren()){
                    // events for entering and exiting are attached to squares children because that impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - 
                        // if it cannot be dropped in the location, the location shouldn't be highlighted!
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                            //The drag-and-drop gesture entered the target
                            // show the user that it is an actual gesture target
                                if(isOnPathType(draggableType) && isOnPath(n)) {
                                    n.setOpacity(0.7);
                                }
                                else if(isNotOnPathType(draggableType) && !isOnPath(n)) {
                                    n.setOpacity(0.7);
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = since being more selective about whether highlighting changes, 
                        // you could program the game so if the new highlight location is invalid the highlighting doesn't change, or leave this as-is
                        public void handle(DragEvent event) {
                            for (Node nShade: targetGridPane.getChildren()) {
                                nShade.setOpacity(1);
                            }
                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }
        });
    }

    private boolean isOnPathType(DRAGGABLE_TYPE type) {
        switch (type) {
            case BARRACKS:
                return true;
            case VILLAGE:
                return true;
            case TRAP:
                return true;
            default:
                return false;
        }
    }

    private boolean isNotOnPathType(DRAGGABLE_TYPE type) {
        switch (type) {
            case CAMPFIRE:
                return true;
            case TOWER:
                return true;
            case VAMPIRECASTLE:
                return true;
            case ZOMBIEPIT:
                return true;
            default:
                return false;
        }
    }

    private boolean isOnPath(Node node) {
        List<Pair<Integer, Integer>> orderedPath = world.getOrderedPath();
        for(Pair<Integer, Integer> n: orderedPath) {
            if(GridPane.getColumnIndex(node).equals(n.getValue0()) && GridPane.getRowIndex(node).equals(n.getValue1())) {
                return true;
            }
        }
        return false;
    }

    /**
     * remove drag event handlers so that we don't process redundant events
     * this is particularly important for slower machines such as over VLAB.
     * @param draggableType either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane){
        // remove event handlers from nodes in children squares, from anchorPaneRoot, and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n: targetGridPane.getChildren()){
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    /**
     * handle the pressing of keyboard keys.
     * Specifically, we should pause when pressing SPACE
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        // TODO = handle additional key presses, e.g. for consuming a health potion
        switch (event.getCode()) {
        case P:
            if (isPaused && !world.isReachGoal()){
                startTimer();
            }
            else{
                pause();
            }
            break;
        case H:
            world.usePotion();
            break;
        default:
            break;
        }
    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher){
        // TODO = possibly set other menu switchers
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        // TODO = possibly set other menu switchers
        pause();
        mainMenuSwitcher.switchMenu();
    }

    public void setShopSwitcher(MenuSwitcher shopSwitcher){
        this.shopSwitcher = shopSwitcher;
    }

    /**
     * this method is triggered when click button to go to shop in FXML
     * @throws IOException
     */
    @FXML
    public void switchToShop() throws IOException {
        pause();
        shopSwitcher.switchMenu();
    }


    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we need to track positions of spawned entities such as enemy
     * or items which might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So it is vital this is handled in this Controller class
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                                               .onAttach((o, l) -> o.addListener(xListener))
                                               .onDetach((o, l) -> {
                                                    o.removeListener(xListener);
                                                    entityImages.remove(node);
                                                    squares.getChildren().remove(node);
                                                    cards.getChildren().remove(node);
                                                    equippedItems.getChildren().remove(node);
                                                    unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                                               .onAttach((o, l) -> o.addListener(yListener))
                                               .onDetach((o, l) -> {
                                                   o.removeListener(yListener);
                                                   entityImages.remove(node);
                                                   squares.getChildren().remove(node);
                                                   cards.getChildren().remove(node);
                                                   equippedItems.getChildren().remove(node);
                                                   unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here, position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    /**
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel){
        System.out.println("\n###########################################");
        System.out.println("current method = "+currentMethodLabel);
        System.out.println("In application thread? = "+Platform.isFxApplicationThread());
        System.out.println("Current system time = "+java.time.LocalDateTime.now().toString().replace('T', ' '));
    }

    /**
     * @return the name of music should be played
     */
    public String music() {
        if(world.getLoop() <= 20) {
            return "game_music_strictly_business.mp3";
        } else if(world.getLoop() <= 35) {
            return "game_music_after_20.mp3";
        } else {
            return "game_music_after_36.mp3";
        }
    }

    public LoopManiaWorld getWorld() {
        return this.world;
    }

    @FXML
    public void useAction(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        Sound.playSound("use_potion.mp3", 0.07);
        world.usePotion();
    }

    @FXML
    public void clickOnFinish(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        System.exit(0);
    }

    @FXML
    public void learnAction1(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        if(learn1.getText().equals("Learn") && techPoint >= 5) {
            learn1.setText("Forget");
            background1.setImage(purpleBackGround);
            techPoint -= 5;
            world.addCharacterMaxHP(100);
        }
        else if(learn1.getText().equals("Forget") && learn3.getText().equals("Learn")) {
            learn1.setText("Learn");
            background1.setImage(whiteBackGround);
            techPoint += 5;
            world.minusCharacterMaxHP(100);
        }
    }

    @FXML
    public void learnAction2(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        if(learn2.getText().equals("Learn") && techPoint >= 10) {
            learn2.setText("Forget");
            background2.setImage(purpleBackGround);
            techPoint -= 10;
            world.addCharacterAttack(30);
        }
        else if(learn2.getText().equals("Forget") && learn4.getText().equals("Learn")){
            learn2.setText("Learn");
            background2.setImage(whiteBackGround);
            techPoint += 10;
            world.minusCharacterAttack(30);
        }

    }

    @FXML
    public void learnAction3(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        if(learn3.getText().equals("Learn") && learn1.getText().equals("Forget") && techPoint >= 5) {
            learn3.setText("Forget");
            background3.setImage(purpleBackGround);
            world.addCharacterMaxHP(200);
            techPoint -= 5;
        }
        else if(learn3.getText().equals("Forget") && learn5.getText().equals("Learn")) {
            learn3.setText("Learn");
            background3.setImage(whiteBackGround);
            world.minusCharacterMaxHP(200);
            techPoint += 5;
        }

    }

    @FXML
    public void learnAction4(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        if(learn4.getText().equals("Learn") && learn2.getText().equals("Forget") && techPoint >= 10) {
            learn4.setText("Forget");
            background4.setImage(purpleBackGround);
            techPoint -= 10;
            world.addCharacterDefense(30);
        }
        else if(learn4.getText().equals("Forget") && learn6.getText().equals("Learn")) {
            learn4.setText("Learn");
            background4.setImage(whiteBackGround);
            techPoint += 10;
            world.minusCharacterDefense(30);
        }

    }

    @FXML
    public void learnAction5(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        if(learn5.getText().equals("Learn") && learn3.getText().equals("Forget") && techPoint >= 10) {
            learn5.setText("Forget");
            background5.setImage(purpleBackGround);
            world.addCharacterMaxHP(500);
            techPoint -= 10;
        }
        else if(learn5.getText().equals("Forget") && learn7.getText().equals("Learn")) {
            learn5.setText("Learn");
            background5.setImage(whiteBackGround);
            world.minusCharacterMaxHP(500);
            techPoint += 10;
        }

    }

    @FXML
    public void learnAction6(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        if(learn6.getText().equals("Learn") && learn4.getText().equals("Forget") && techPoint >= 25) {
            learn6.setText("Forget");
            background6.setImage(purpleBackGround);
            techPoint -= 25;
            world.addCharacterReviveTime(1);
        }
        else if(learn6.getText().equals("Forget") && learn7.getText().equals("Learn")) {
            learn6.setText("Learn");
            background6.setImage(whiteBackGround);
            techPoint += 25;
            world.minusCharacterReviveTime(1);
        }

    }

    @FXML
    public void learnAction7(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        if(learn7.getText().equals("Learn") && learn5.getText().equals("Forget") && learn6.getText().equals("Forget") && techPoint >= 100) {
            learn7.setText("Forget");
            background7.setImage(purpleBackGround);
            techPoint -= 100;
            magic = true;
        }
        else if(learn7.getText().equals("Forget")) {
            learn7.setText("Learn");
            background7.setImage(whiteBackGround);
            techPoint += 100;
            magic = false;
        }

    }

    ////////////// Achievement //////////////
    /**
     * this method is triggered when click button to go to shop in FXML
     * @throws IOException
     */
    @FXML
    public void switchToAchievement() throws IOException {
        pause();
        Sound.playSound("mouse_click.mp3");
        blinkTrophies = false;
        achievementSwitcher.switchMenu();
    }

    public void setAchievementSwitcher(MenuSwitcher achievementSwitcher){
        this.achievementSwitcher = achievementSwitcher;
    }

    /**
     * game speed control functions
     */
    @FXML
    public void accelerateGame() {
        Sound.playSound("mouse_click.mp3");
        if (gameSpeed >= 0.05)  // user can only maximum speed up to gameSpeed = 0.0375 (8x speed)
            gameSpeed /= 2;
        timeline.stop();
        startTimer();
    }
    @FXML
    public void resetGameSpeed() {
        Sound.playSound("mouse_click.mp3");
        gameSpeed = DEFAULT_GAME_SPEED;
        timeline.stop();
        startTimer();
    }

    /**
     * blink a given button
     */
    public void blinkButton(Button button, Boolean blink) {
        String DEFAULT_BUTTON_STYLE = mainMenu.getStyle();
        if (blink == false) {
            button.setStyle(DEFAULT_BUTTON_STYLE);
            return;
        }
        if (button.getStyle() == DEFAULT_BUTTON_STYLE) {
            button.setStyle("-fx-background-color: #ffff00; ");  // yellow background 
        } else {
            button.setStyle(DEFAULT_BUTTON_STYLE);
        }
    }
}