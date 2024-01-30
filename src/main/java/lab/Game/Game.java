package lab.Game;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lab.Database;
import lab.interfaces.GameBackground;
import lab.interfaces.GameController;
import lab.interfaces.MovingObjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Game implements GameController {
    private final GameBackground background;
    private final MovingObjects ufon;
    private final Canvas canvas;
    private final Scene scene;
    private int many, newObj, score, counter, ufoSpeed, coinUpgradeLevel;
    private final ArrayList<MovingObjects> fireballs = new ArrayList<>();
    private final ArrayList<MovingObjects> coins = new ArrayList<>();
    private final Image fireballImage, coinImage, shurikenImage;
    private final Random rand = new Random();
    private final Connection connection;
    private boolean end;
    public Game(Canvas canvas, Connection connection) throws SQLException {
        this.fireballImage = new Image(getClass().getResourceAsStream("fire-fireball.gif"), 50, 50, true, true);
        this.coinImage = new Image(getClass().getResourceAsStream("coin.gif"), 50, 50, true, true);
        this.shurikenImage = new Image(getClass().getResourceAsStream("shuriken.gif"), 50, 50, true, true);
        this.canvas = canvas;
        this.background = new Background(canvas.getWidth(), canvas.getHeight(), connection);
        this.connection = connection;
        this.ufon = new Ufon();
        this.ufoSpeed = 1;
        this.scene = canvas.getScene();
        this.newObj = 0;
        addObjects();
        this.many = 0;
        this.score = 0;
        this.end = false;
        getData();
    }
    private void addObjects(){
        newObj++;
        if(this.newObj == 80){
            for (int i = 0; i <= rand.nextInt(2); i++){
                int choice = rand.nextInt(1,3);
                int Xpos = rand.nextInt(15) * 65;
                if(choice == 1) this.coins.add(new Coin(Xpos, 50, coinImage));
                else {
                    if(background.getAddedBackground() == 2) {
                        int vote = rand.nextInt(2);
                        if (vote == 1) this.fireballs.add(new Fireball(Xpos, 50, fireballImage));
                        else this.fireballs.add(new Fireball(Xpos, 50, shurikenImage));
                    } else{
                        this.fireballs.add(new Fireball(Xpos, 50, fireballImage));
                    }
                }
            }
            newObj = 0;
        }
    }
    @Override
    public boolean isEnd(){
        return this.end;
    }
    @Override
    public void draw(Canvas canvas) throws SQLException {
        if(background.getLifes() == 0){
            this.end = true;
        }
        else {
            if (many == 0) background.setCoins(canvas.getGraphicsContext2D(), 0);
            background.draw(canvas.getGraphicsContext2D());
            Ufon ufo = (Ufon) ufon;
            ufo.draw(canvas.getGraphicsContext2D());
            addObjects();
            for (int i = 0; i < coins.size(); i++) {
                Coin coin = (Coin) coins.get(i);
                coin.setPos(coin.getX(), coin.getY() + 1);
                coin.draw(canvas.getGraphicsContext2D());
                if (coin.isOutOfScene()) {
                    coins.remove(i);
                    i--;
                } else if (coin.isCollected(coin, ufo)) {
                    coins.remove(i);
                    i--;
                    this.many += coinUpgradeLevel;
                    background.setCoins(canvas.getGraphicsContext2D(), many);
                }
            }
            for (int i = 0; i < fireballs.size(); i++) {
                Fireball fireball = (Fireball) fireballs.get(i);
                fireball.setPos(fireball.getX(), fireball.getY() + 2);
                fireball.draw(canvas.getGraphicsContext2D());
                if (fireball.isOutOfScene()) {
                    fireballs.remove(i);
                    i--;
                } else if (fireball.isHit(fireball, ufo)) {
                    fireballs.remove(i);
                    i--;
                    background.decLifes();
                }
            }
        }
        counter ++;
        if(counter == 200){
            counter = 0;
            score ++;
        }
        background.setScore(canvas.getGraphicsContext2D(), score);
    }
    @Override
    public void eventsCheck(){
        Ufon ufo = (Ufon) ufon;
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.RIGHT || key == KeyCode.D ) {
                ufo.rightMove(ufoSpeed);
            }
            if (key == KeyCode.LEFT || key == KeyCode.A ) {
                ufo.leftMove(ufoSpeed);
            }
            if (key == KeyCode.SPACE || key == KeyCode.UP || key == KeyCode.W ) {
                ufo.jump();
            }
        });
        if(ufo.getJumping()){
            ufo.jump();
        }
    }
    @Override
    public void newGame() throws SQLException {
        background.resetLifes();
        many = 0;
        score = 0;
        background.setCoins(canvas.getGraphicsContext2D(), many);
        end = false;
        getData();
    }
    @Override
    public int getCoins(){
        return this.many;
    }
    @Override
    public int getScore(){return  this.score; }

    private void getData() throws SQLException {
        int[] numbers = Database.selectUserUpdates(connection);
        coinUpgradeLevel = numbers[0];
        ufoSpeed = numbers[1];
    }
}