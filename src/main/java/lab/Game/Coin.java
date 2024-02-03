package lab.Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lab.interfaces.MovingObjects;

public class Coin implements MovingObjects {
    private final Image coinImage;
    private int x, y;
    private boolean special;
    public Coin(int x, int y, Image coinImage){
        this.x = x;
        this.y = y;
        this.coinImage = coinImage;
        this.special = false;
    }
    public Coin(int x, int y, Image coinImage, boolean sueprcoin){
        this.x = x;
        this.y = y;
        this.coinImage = coinImage;
        this.special = sueprcoin;
    }
    @Override
    public int getX(){ return this.x; }
    @Override
    public int getY(){ return this.y; }
    @Override
    public void draw(GraphicsContext gc){
        gc.save();
        gc.drawImage(coinImage, x, y);
        gc.restore();
    }
    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean isOutOfScene(){
        return y >= 500;
    }
    public boolean isCollected(Coin coin, Ufon ufo){
        return coin.x + 50 > ufo.getX() && ufo.getX() + 50 > coin.x && coin.y + 50 > ufo.getY()
                && coin.y < ufo.getY() + 50;
    }
    public  boolean getSupercoin(){
        return this.special;
    }
    public void resetSupercoin(){
        this.special = false;
    }
}
