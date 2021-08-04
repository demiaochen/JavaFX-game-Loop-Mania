package unsw.loopmania.ui.modeSelection;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.ui.MenuSwitcher;


public class ModeSelectionController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;
    private LoopManiaWorld world;
    private Stage primaryStage;

    public ModeSelectionController(LoopManiaWorld world, Stage primaryStage) {
        this.world = world;
        this.primaryStage = primaryStage;
    }


    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGameStandard() throws IOException {
        world.setupMode("standard");
        primaryStage.setTitle("Loop Mania: Standard");
        gameSwitcher.switchMenu();
    }

    @FXML
    private void switchToGameSurvival() throws IOException {
        world.setupMode("survival");
        primaryStage.setTitle("Loop Mania: Survival");
        gameSwitcher.switchMenu();
    }

    @FXML
    private void switchToGameBerserker() throws IOException {
        world.setupMode("berserker");
        primaryStage.setTitle("Loop Mania: Berserker");
        gameSwitcher.switchMenu();
    }

    @FXML
    private void switchToGameConfusing() throws IOException {
        world.setupMode("confusing");
        primaryStage.setTitle("Loop Mania: Confusing");
        gameSwitcher.switchMenu();
    }
}
