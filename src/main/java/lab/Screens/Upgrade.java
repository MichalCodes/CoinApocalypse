package lab.Screens;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lab.Database;
import lab.interfaces.StaticScreens;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import static lab.Database.selectUserCoins;

public class Upgrade implements StaticScreens {
    private final Button upgradeCoins = new Button("Upgrade coins");
    private final Button restartAll = new Button("Restartovat postup");
    private final Button upgradeSpeed = new Button("Upgrade speed");
    private final Button addBackground = new Button("Add background");
    private final Button back = new Button("Back to menu");
    private final Group root;
    private boolean block;
    private boolean goBack;
    private final Image image;
    private int coins, coinUpgradeLevel, speedUpgradeLevel, addedBackground;
    private final Connection connection;
    Upgrade(Group root, Connection connection) throws SQLException {
        this.root = root;
        this.image = new Image("upgrades.png", root.getScene().getWidth() , root.getScene().getHeight(), true, true);
        this.connection = connection;
        this.setButtons();
        this.coins = 0;
        getData();
    }
    @Override
    public int checkMouseEvents() {
        upgradeCoins.setOnAction(e-> {
            if(coinUpgradeLevel < 9 && coins > 100 * coinUpgradeLevel){
                coins -= 100 * coinUpgradeLevel;
                coinUpgradeLevel++;
            }
        });
        upgradeSpeed.setOnAction(e-> {
            if(speedUpgradeLevel < 4 && coins > 500 * speedUpgradeLevel){
                coins -= 500 * speedUpgradeLevel;
                speedUpgradeLevel++;
            }
        });
        restartAll.setOnAction(e-> {
            coins = 0;
            coinUpgradeLevel = 1;
            speedUpgradeLevel = 1;
            addedBackground = 1;
        });
        addBackground.setOnAction(e-> {
            if(addedBackground < 2 && coins > 10000){
                coins -= 10000;
                addedBackground++;
            }
        });
        back.setOnAction(e-> goBack = true);
        int i = 0;
        if(goBack) i = 4;
        goBack = false;
        try {
            setData(coins, coinUpgradeLevel, speedUpgradeLevel, addedBackground);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
    @Override
    public void draw(GraphicsContext gc) throws IOException, SQLException {
        gc.clearRect(0,0,1000, 550);
        gc.drawImage(image, 0,0, root.getScene().getWidth(), root.getScene().getHeight());
        gc.setFill(Color.GREEN);
        gc.fillRect(0,0,1000, 50);
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(25));
        gc.fillText("Coins: " + coins, 400, 40);
        gc.setFont(Font.font(20));
        gc.fillText("Coins x" + coinUpgradeLevel, 130, 280);
        gc.fillText("Speed lvl" + speedUpgradeLevel, 320, 280);
        gc.fillText("Background no." + addedBackground, 490, 280);
        gc.fillText("coins, scores, \n   upgrades", 710, 260);
        if(coinUpgradeLevel < 9){
            gc.fillText("cost: " + (100 * coinUpgradeLevel), 130, 400);
        }
        if(speedUpgradeLevel < 4){
            gc.fillText("cost: " + (500 * speedUpgradeLevel), 320, 400);
        }
        if(addedBackground < 2){
            gc.fillText("cost: " + 10000, 500, 400);
        }
        if(!block){
            getData();
            root.getChildren().add(upgradeSpeed);
            root.getChildren().add(restartAll);
            root.getChildren().add(addBackground);
            root.getChildren().add(upgradeCoins);
            root.getChildren().add(back);
            block = true;
        }
    }
    @Override
    public void removeButtons() {
        try {
            setData(coins, coinUpgradeLevel, speedUpgradeLevel, addedBackground);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        root.getChildren().remove(upgradeCoins);
        root.getChildren().remove(upgradeSpeed);
        root.getChildren().remove(addBackground);
        root.getChildren().remove(restartAll);
        root.getChildren().remove(back);
        block = false;
    }
    private void getData() throws SQLException {
        int[] numbers;
        numbers = Database.selectUserUpdates(connection);
        coins = selectUserCoins(connection);
        coinUpgradeLevel = numbers[0];
        speedUpgradeLevel = numbers[1];
        addedBackground = numbers[2];
    }
    private void setData(int n, int CoinLvl, int speedLvl, int addedlvl) throws SQLException {
        Database.updateUserCoins(connection, n);
        Database.updateUserlvls(connection, CoinLvl, speedLvl, addedlvl);
    }
    private void setButtons(){
        addBackground.setLayoutX(500);
        addBackground.setLayoutY(300);
        addBackground.setPrefWidth(150);
        addBackground.setPrefHeight(50);
        addBackground.setStyle("-fx-background-color: yellow;");
        upgradeCoins.setLayoutX(100);
        upgradeCoins.setLayoutY(300);
        upgradeCoins.setPrefWidth(150);
        upgradeCoins.setPrefHeight(50);
        upgradeCoins.setStyle("-fx-background-color: yellow;");
        restartAll.setLayoutX(700);
        restartAll.setLayoutY(300);
        restartAll.setPrefWidth(150);
        restartAll.setPrefHeight(50);
        restartAll.setStyle("-fx-background-color: yellow;");
        upgradeSpeed.setLayoutX(300);
        upgradeSpeed.setLayoutY(300);
        upgradeSpeed.setPrefWidth(150);
        upgradeSpeed.setPrefHeight(50);
        upgradeSpeed.setStyle("-fx-background-color: yellow;");
        back.setLayoutX(350);
        back.setLayoutY(450);
        back.setPrefWidth(250);
        back.setPrefHeight(50);
        back.setStyle("-fx-background-color: yellow;");
    }
}