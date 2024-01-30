package lab.interfaces;

import javafx.scene.canvas.GraphicsContext;

import java.sql.SQLException;

public interface GameBackground {
    void decLifes();
    void resetLifes();
    int getLifes();
    int getAddedBackground();
    void setScore(GraphicsContext gc, int score);
    void setCoins(GraphicsContext gc, int coins);
    void draw(GraphicsContext gc) throws SQLException;
}
