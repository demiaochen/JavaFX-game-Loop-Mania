package unsw.loopmania.ui.achievement;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.ui.MenuSwitcher;

public class AchievementController {

    Achievement achievement;

    @FXML
    private ImageView trophyKillImage;

    @FXML
    private ImageView trophyBossImage;

    @FXML
    private ImageView trophyGoldImage;

    @FXML
    private ImageView trophyLoopsImage;

    @FXML
    private ImageView trophyDoggieImage;

    public AchievementController(Achievement achievement) {
        this.achievement = achievement;
    }

    @FXML
    public void initialize() {
        if (achievement.achievementKill) {
            trophyKillImage.setImage(new Image((new File("src/images/angel_achievement.gif")).toURI().toString()));
        } else {
            trophyKillImage.setImage(new Image((new File("src/images/grey_trophy.png")).toURI().toString()));
        }

        if (achievement.achievementBoss) {
            trophyBossImage.setImage(new Image((new File("src/images/trophy_elan.jpg")).toURI().toString()));
        } else {
            trophyBossImage.setImage(new Image((new File("src/images/grey_trophy.png")).toURI().toString()));
        }

        if (achievement.achievementGold) {
            trophyGoldImage.setImage(new Image((new File("src/images/trophy_gold.png")).toURI().toString()));
        } else {
            trophyGoldImage.setImage(new Image((new File("src/images/grey_trophy.png")).toURI().toString()));
        }

        if (achievement.achievementLoops)     {
            trophyLoopsImage.setImage(new Image((new File("src/images/trophy_loop.jpeg")).toURI().toString()));
        } else {
            trophyLoopsImage.setImage(new Image((new File("src/images/grey_trophy.png")).toURI().toString()));
        }

        if (achievement.achievementDoggie) {
            trophyDoggieImage.setImage(new Image((new File("src/images/trophy_doggie.jpg")).toURI().toString()));
        } else {
            trophyDoggieImage.setImage(new Image((new File("src/images/grey_trophy.png")).toURI().toString()));      
        }   
    }

    private MenuSwitcher gameSwitcher;
    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }
    
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }
}
