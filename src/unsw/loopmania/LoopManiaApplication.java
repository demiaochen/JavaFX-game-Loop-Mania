package unsw.loopmania;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.loopmania.ui.achievement.AchievementController;
import unsw.loopmania.ui.game.LoopManiaWorldController;
import unsw.loopmania.ui.game.LoopManiaWorldControllerLoader;
import unsw.loopmania.ui.mainMenu.MainMenuController;
import unsw.loopmania.ui.modeSelection.ModeSelectionController;
import unsw.loopmania.ui.shop.ShopController;
import unsw.loopmania.util.Sound;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {
    /**
     * the controller for the game. Stored as a field so can terminate it when click exit button
     */
    private LoopManiaWorldController mainController;

    // bgm player
    private Sound bgmPlayer = new Sound("main_menu_once_upon_a_time.mp3", 0.1);

    @Override
    public void start(Stage primaryStage) throws IOException {
        //play background music
        bgmPlayer.play();

        // set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);
        
        // load the main game
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json");
        mainController = loopManiaLoader.loadController();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        Parent gameRoot = gameLoader.load();

        // load the main menu
        MainMenuController mainMenuController = new MainMenuController(this);
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

        // load mode selection 
        ModeSelectionController modeSelectionController = new ModeSelectionController(mainController.getWorld(), primaryStage);
        FXMLLoader modeSelectionLoader = new FXMLLoader(getClass().getResource("ModeSelectionView.fxml"));
        modeSelectionLoader.setController(modeSelectionController); 
        Parent modeSelectionRoot = modeSelectionLoader.load();


        // create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(mainMenuRoot);
        
        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        mainController.setMainMenuSwitcher(() -> {
            Sound.playSound("mouse_click.mp3");
            switchToRoot(scene, mainMenuRoot, primaryStage);
            bgmPlayer.switchSong("main_menu_once_upon_a_time.mp3");
        });

        mainController.setShopSwitcher(() -> {
            bgmPlayer.switchSong("shop_money.mp3");
            // load shop interface 
            ShopController shopController = new ShopController(mainController.getWorld());
            FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("ShopView.fxml"));
            shopLoader.setController(shopController); 
            Parent shopRoot = null;
            try {
                shopRoot = shopLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            switchToRoot(scene, shopRoot, primaryStage);
            
            shopController.setGameSwitcher(() -> {
                Sound.playSound("mouse_click.mp3");
                bgmPlayer.switchSong(mainController.music());
                switchToRoot(scene, gameRoot, primaryStage);
                mainController.startTimer();
            });
        });

        mainController.setAchievementSwitcher(() -> {
            bgmPlayer.switchSong("achievement_fate.mp3");
            // load achievement interface
            AchievementController achievementController = new AchievementController(mainController.getWorld().getAchievement());
            FXMLLoader achievementLoader = new FXMLLoader(getClass().getResource("AchievementView.fxml"));
            achievementLoader.setController(achievementController); 
            Parent achievementRoot = null;
            try {
                achievementRoot = achievementLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            switchToRoot(scene, achievementRoot, primaryStage);
            
            achievementController.setGameSwitcher(() -> {
                Sound.playSound("mouse_click.mp3");
                bgmPlayer.switchSong(mainController.music());
                switchToRoot(scene, gameRoot, primaryStage);
                mainController.startTimer();
            });
        });

        mainMenuController.setGameSwitcher(() -> {
            Sound.playSound("mouse_click.mp3");
            switchToRoot(scene, modeSelectionRoot, primaryStage);
        });

        modeSelectionController.setGameSwitcher(() -> {
            Sound.playSound("mouse_click.mp3");
            switchToRoot(scene, gameRoot, primaryStage);
            bgmPlayer.switchSong(mainController.music());
            mainController.startTimer();
        });

        // deploy the main onto the stage
        gameRoot.requestFocus();
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop(){
        // wrap up activities when exit program
        mainController.terminate();
    }


    public void toggleBGM() {
        bgmPlayer.toggleSound();
    }

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage){
        scene.setRoot(root);
        root.requestFocus();
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
