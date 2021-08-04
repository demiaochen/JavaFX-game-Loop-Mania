package unsw.loopmania.ui.shop;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.ui.MenuSwitcher;
import unsw.loopmania.util.Sound;

import java.util.Random;

public class ShopController {

    @FXML
    private Button backButton;

    @FXML
    private Text item1Name;

    @FXML
    private Text item2Name;

    @FXML
    private Text goldInformation;

    @FXML
    private Text itemName;

    @FXML
    private Label howManyHold;

    @FXML
    private Label priceInformation;

    @FXML
    private Button buyButton;

    @FXML
    private Button sellButton;

    @FXML
    private ImageView item2Image;

    @FXML
    private ImageView item1Image;

    @FXML
    private CheckBox item1;

    @FXML
    private CheckBox item2;

    @FXML
    private CheckBox item3;

    @FXML
    private ImageView coinImage;

    @FXML
    private Text coinPrice;

    @FXML
    private Text coinAmount;

    @FXML
    private Button sellCoin;


    private Shop shop;

    private MenuSwitcher gameSwitcher;

    public ShopController(LoopManiaWorld world) {
        this.shop = new Shop(world);

    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    @FXML
    public void clickOnBack(ActionEvent event){
        gameSwitcher.switchMenu();
    }

    @FXML
    public void clickOnBuy(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        if(shop.item1Property().get() && shop.getAvailableEquipment() > 0 && shop.getNumberOfUnequipped() < 16) {
            if(item1Name.getText().equals("Sword") && this.shop.getGold() >= 1500) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Sword")));
            } else if(item1Name.getText().equals("Stake") && this.shop.getGold() >= 1500) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Stake")));
            } else if(item1Name.getText().equals("Staff") && this.shop.getGold() >= 2000) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Staff")));
            } else if(item1Name.getText().equals("Armour") && this.shop.getGold() >= 3000) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Armour")));
            } else if(item1Name.getText().equals("Shield") && this.shop.getGold() >= 3000) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Shield")));
            } else if(item1Name.getText().equals("Helmet") && this.shop.getGold() >= 3000) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Helmet")));
            }
        } else if(shop.item2Property().get() && shop.getAvailableEquipment() > 0 && shop.getNumberOfUnequipped() < 16) {
            if(item2Name.getText().equals("Sword") && this.shop.getGold() >= 1500) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Sword")));
            } else if(item2Name.getText().equals("Stake") && this.shop.getGold() >= 1500) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Stake")));
            } else if(item2Name.getText().equals("Staff") && this.shop.getGold() >= 2000) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Staff")));
            } else if(item2Name.getText().equals("Armour") && this.shop.getGold() >= 3000) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Armour")));
            } else if(item2Name.getText().equals("Shield") && this.shop.getGold() >= 3000) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Shield")));
            } else if(item2Name.getText().equals("Helmet") && this.shop.getGold() >= 3000) {
                this.shop.buy();
                this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Helmet")));
            }
        } else if(shop.item3Property().get() && shop.getAvailablePotion() > 0) {
            this.shop.buy();
            this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
            howManyHold.setText("Hold: " + String.valueOf(shop.getHealthPotionNum()));
        }
    }

    @FXML
    public void clickOnSell(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        this.shop.sell();
        this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
        if(shop.item1Property().get()) {
            if(item1Name.getText().equals("Sword")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Sword")));
            } else if(item1Name.getText().equals("Stake")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Stake")));
            } else if(item1Name.getText().equals("Staff")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Staff")));
            } else if(item1Name.getText().equals("Armour")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Armour")));
            } else if(item1Name.getText().equals("Shield")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Shield")));
            } else if(item1Name.getText().equals("Helmet")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Helmet")));
            }
        } else if(shop.item2Property().get()) {
            if(item2Name.getText().equals("Sword")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Sword")));
            } else if(item2Name.getText().equals("Stake")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Stake")));
            } else if(item2Name.getText().equals("Staff")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Staff")));
            } else if(item2Name.getText().equals("Armour")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Armour")));
            } else if(item2Name.getText().equals("Shield")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Shield")));
            } else if(item2Name.getText().equals("Helmet")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Helmet")));
            }
        } else {
            howManyHold.setText("Hold: " + String.valueOf(shop.getHealthPotionNum()));
        }
    }

    @FXML
    void selectItem1(ActionEvent event) {
        this.shop.select1(this.item1Name.getText());
        itemName.setText(item1Name.getText());
        howManyHold.setVisible(true);
        priceInformation.setVisible(true);
        if(item1Name.getText().equals("Sword") || item1Name.getText().equals("Stake")) {
            priceInformation.setText("Price: 1500");
            if(item1Name.getText().equals("Sword")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Sword")));
            } else {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Stake")));
            }
        } else if(item1Name.getText().equals("Staff")) {
            priceInformation.setText("Price: 2000");
            howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Staff")));
        } else {
            priceInformation.setText("Price: 3000");
            if(item1Name.getText().equals("Armour")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Armour")));
            } else if(item1Name.getText().equals("Shield")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Shield")));
            } else {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Helmet")));
            }
        }
    }

    @FXML
    void selectItem2(ActionEvent event) {
        this.shop.select2(this.item2Name.getText());
        itemName.setText(item2Name.getText());
        howManyHold.setVisible(true);
        priceInformation.setVisible(true);
        if(item2Name.getText().equals("Sword") || item2Name.getText().equals("Stake")) {
            priceInformation.setText("Price: 1500");
            if(item2Name.getText().equals("Sword")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Sword")));
            } else {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Stake")));
            }
        } else if(item2Name.getText().equals("Staff")) {
            priceInformation.setText("Price: 2000");
            howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Staff")));
        } else {
            priceInformation.setText("Price: 3000");
            if(item2Name.getText().equals("Armour")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Armour")));
            } else if(item2Name.getText().equals("Shield")) {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Shield")));
            } else {
                howManyHold.setText("Hold: " + String.valueOf(shop.countHold("Helmet")));
            }
        }
    }

    @FXML
    void selectItem3(ActionEvent event) {
        this.shop.select3();
        itemName.setText("Health Portion");
        priceInformation.setVisible(true);
        priceInformation.setText("Price: 1000");
        howManyHold.setVisible(true);
        howManyHold.setText("Hold: " + String.valueOf(shop.getHealthPotionNum()));
    }

    @FXML
    void sellCoin(ActionEvent event) {
        Sound.playSound("mouse_click.mp3");
        shop.sellCoin();
        this.coinAmount.setText("Holding: " + String.valueOf(this.shop.getCoinAmount()));
        this.coinPrice.setText("Price: $" + String.valueOf(this.shop.getCoinPrice()));
        this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));
    }

    @FXML
    public void initialize() {
        String[] sellItems = {"Sword", "Stake", "Staff", "Armour", "Helmet", "Sheild"};
        Random random = new Random();
        int first = random.nextInt(5);
        int second = -1;
        while(second < 0 || first == second){
            second = random.nextInt(5);
        }

        this.item1Name.setText(sellItems[first]);
        this.item2Name.setText(sellItems[second]);
        this.goldInformation.setText("Gold: " + String.valueOf(this.shop.getGold()));

        File file1 = null;

        if(first == 0) {
            file1 = new File("src/images/basic_sword.png");
        } else if(first == 1) {
            file1 = new File("src/images/stake.png");
        } else if(first == 2) {
            file1 = new File("src/images/staff.png");
        } else if(first == 3) {
            file1 = new File("src/images/armour.png");
        } else if(first == 4) {
            file1 = new File("src/images/helmet.png");
        } else if(first == 5) {
            file1 = new File("src/images/shield.png");
        }
        Image image1 = new Image(file1.toURI().toString());
        item1Image.setImage(image1);

        File file2 = null;

        if(second == 0) {
            file2 = new File("src/images/basic_sword.png");
        } else if(second == 1) {
            file2 = new File("src/images/stake.png");
        } else if(second == 2) {
            file2 = new File("src/images/staff.png");
        } else if(second == 3) {
            file2 = new File("src/images/armour.png");
        } else if(second == 4) {
            file2 = new File("src/images/helmet.png");
        } else if(second == 5) {
            file2 = new File("src/images/shield.png");
        }
        Image image2 = new Image(file2.toURI().toString());
        item2Image.setImage(image2);

        this.shop.item1Property().bindBidirectional(item1.selectedProperty());
        this.shop.item2Property().bindBidirectional(item2.selectedProperty());
        this.shop.item3Property().bindBidirectional(item3.selectedProperty());

        this.shop.set1False();
        this.shop.set2False();
        this.shop.set3False();

        this.howManyHold.setVisible(false);
        this.priceInformation.setVisible(false);

        this.coinAmount.setText("Holding: " + String.valueOf(this.shop.getCoinAmount()));
        this.coinPrice.setText("Price: $" + String.valueOf(this.shop.getCoinPrice()));
    }
}
