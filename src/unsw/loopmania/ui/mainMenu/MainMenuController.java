package unsw.loopmania.ui.mainMenu;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import unsw.loopmania.LoopManiaApplication;
import unsw.loopmania.ui.MenuSwitcher;
import javafx.event.ActionEvent;

/**
 * controller for the main menu.
 * TODO = you could extend this, for example with a settings menu, or a menu to load particular maps.
 */
public class MainMenuController {
    @FXML
    private Button musicToggle;

    LoopManiaApplication app;

    public MainMenuController (LoopManiaApplication app) {
        this.app = app;
    }

    @FXML
    public void musicToggleAction(ActionEvent event) {
        app.toggleBGM();
    }

    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }
}
