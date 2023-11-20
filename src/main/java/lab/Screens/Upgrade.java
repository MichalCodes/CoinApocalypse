package lab.Screens;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lab.interfaces.StaticScreens;

import java.io.*;

public class Upgrade implements StaticScreens {
    private final Button upgradeCoins = new Button("Upgrade coins");
    private final Button restartAll = new Button("Restartovat postup");
    private final Button upgradeSpeed = new Button("Upgrade speed");
    private final Button addBackground = new Button("Add background");
    private final Button back = new Button("Back to menu");
    private final Group root;
    private boolean block, goBack, rst;
    private final Image image;
    private int coins, coinUpgradeLevel, speedUpgradeLevel, addedBackground;
    Upgrade(Group root){
        this.root = root;
        this.image = new Image("upgrades.png", root.getScene().getWidth() , root.getScene().getHeight(), true, true);
        this.setButtons();
        this.coins = 0;
        this.rst = false;
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
            rst = true;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
    @Override
    public void draw(GraphicsContext gc) throws IOException {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        root.getChildren().remove(upgradeCoins);
        root.getChildren().remove(upgradeSpeed);
        root.getChildren().remove(addBackground);
        root.getChildren().remove(restartAll);
        root.getChildren().remove(back);
        block = false;
    }
    private void getData() {
        try (BufferedReader br = new BufferedReader(new FileReader("mem.txt"))) {
            String l1 = br.readLine();
            coinUpgradeLevel = Integer.parseInt(String.valueOf(l1.charAt(0)));
            speedUpgradeLevel = Integer.parseInt(String.valueOf(l1.charAt(2)));
            addedBackground = Integer.parseInt(String.valueOf(l1.charAt(4)));
            for (int i = 0; i < 5; i++) {
                br.readLine();
            }
            coins = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setData(int n, int CoinLvl, int speedLvl, int addedlvl) throws IOException {
        String[] specificLine = new String[8];
        BufferedReader br = new BufferedReader(new FileReader("mem.txt"));
        for(int i = 0; i < 8; i++){
            specificLine[i] = br.readLine();
        }
        coins = Integer.parseInt(specificLine[6]);
        BufferedWriter bw = new BufferedWriter(new FileWriter("mem.txt"));
        coinUpgradeLevel = CoinLvl;
        speedUpgradeLevel = speedLvl;
        addedBackground = addedlvl;
        bw.write(Math.abs(coinUpgradeLevel) + "," + Math.abs(speedUpgradeLevel) + "," + Math.abs(addedBackground) + "\n");
        for(int i = 1; i < 6; i++){
            if(rst){
                bw.write(0 + "\n");
            } else{
                bw.write(specificLine[i] + "\n");
            }
        }
        rst = false;
        coins = n;
        bw.write(Integer.toString(Math.abs(coins)));
        bw.close();
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