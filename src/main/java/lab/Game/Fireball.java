package lab.Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lab.interfaces.MovingObjects;

public class Fireball implements MovingObjects {
    private final Image fireballImage;
    private int x, y;
    public Fireball(int x, int y, Image fireballImage){
        this.x = x;
        this.y = y;
        this.fireballImage = fireballImage;
    }
    @Override
    public int getX(){
        return this.x;
    }
    @Override
    public int getY(){
        return this.y;
    }
    @Override
    public void draw(GraphicsContext gc){
        gc.save();
        gc.drawImage(fireballImage, x, y);
        gc.restore();
    }
    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean isOutOfScene(){
        return y >= 500;
    }
    public boolean isHit(Fireball fireball, Ufon ufo){
        return fireball.x + 50 > ufo.getX() && ufo.getX() + 50 > fireball.x && fireball.y + 50 > ufo.getY()
                && fireball.y < ufo.getY() + 50;
    }
}