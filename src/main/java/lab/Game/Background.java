package lab.Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lab.interfaces.GameBackground;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Background implements GameBackground {
    private final Image image1, image2;
    private final Image srdce;
    private String coinNumber;
    private int lifes, addedBackground;
    private boolean block;
    public Background(double width, double height){
        this.block = false;
        this.addedBackground = 1;
        this.getData();
        this.image2 = new Image("background2.png", width, height - 50, true, true);
        this.image1 = new Image("background.png", width, height - 50, true, true);
        this.srdce = new Image("srdce.png", 50, 50, true, true);
        this.lifes = 3;
    }
    @Override
    public void decLifes(){
        lifes--;
    }
    @Override
    public void resetLifes(){
        lifes = 3;
        block = false;
    }
    @Override
    public int getLifes() {return this.lifes;}
    @Override
    public void setScore(GraphicsContext gc, int score){
        String scoreNumber = Integer.toString(score);
        gc.setFill(Color.YELLOW);
        gc.fillText(scoreNumber, 150, 40);
    }
    @Override
    public void setCoins(GraphicsContext gc, int coins){
        coinNumber = Integer.toString(coins);
        gc.setFill(Color.GREEN);
        gc.fillText(coinNumber, 590, 40);
    }
    @Override
    public void draw(GraphicsContext gc){
        gc.setFont(Font.font(25));
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, 500, 50);
        gc.setFill(Color.YELLOW);
        gc.fillRect(500, 0, 500, 50);
        gc.fillText("Score: ", 10,40);
        gc.setFill(Color.GREEN);
        gc.fillText("Coins: ", 510, 40);
        if(!block){
            getData();
            block = true;
        }
        if(addedBackground == 2){
            gc.drawImage(image2, 0, 50, 1000, 500);
        } else {
            gc.drawImage(image1, 0, 50);
        }

        for (int i = 0; i < this.lifes; i++){
            int posX = 950 - i * 50;
            gc.drawImage(srdce, posX, 490);
        }
        gc.setFill(Color.GREEN);
        gc.fillText(coinNumber, 590, 40);
        gc.restore();
    }
    private void getData(){
        try (BufferedReader br = new BufferedReader(new FileReader("mem.txt"))) {
            String l1 = br.readLine();
            addedBackground = Integer.parseInt(String.valueOf(l1.charAt(4)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
