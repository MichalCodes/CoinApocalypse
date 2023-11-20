package lab.Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lab.interfaces.MovingObjects;

public class Ufon implements MovingObjects {
    private double x, y;
    private final Image zelenaPotvora;
    private boolean upDown, jumping;
    public Ufon(){
        zelenaPotvora = new Image("ufo.png", SIZE, SIZE, true, true);
        this.x = 200;
        this.y = 430;
        this.upDown = true;
        this.jumping = false;
    }
    @Override
    public int getX(){ return (int) this.x; }
    @Override
    public int getY(){ return (int) this.y; }
    @Override
    public void draw(GraphicsContext gc){
        gc.drawImage(zelenaPotvora, x, y);
    }
    public boolean getJumping() {return jumping;}
    public void rightMove(double speed){
        if(x < 950) x += speed * 12;
    }
    public void leftMove(double speed){
        if(x > 0) x -= speed * 12;
    }
    public boolean jump(){
        jumping = true;
        if(y > 330 && upDown){
            y -= 2;
            if(y == 330) upDown = false;
        }else if (y < 430 && !upDown) {
            y += 2;
            if(y == 430){
                jumping = false;
                upDown = true;
                return true;
            }
        }
        return false;
    }
}
