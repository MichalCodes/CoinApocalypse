package lab.interfaces;

import javafx.scene.canvas.GraphicsContext;

public interface GameBackground {
    void decLifes();
    void resetLifes();
    int getLifes();
    void setScore(GraphicsContext gc, int score);
    void setCoins(GraphicsContext gc, int coins);
    void draw(GraphicsContext gc);
}
