package lab.interfaces;

import javafx.scene.canvas.Canvas;

import java.sql.SQLException;

public interface GameController {
    boolean isEnd();
    void draw(Canvas canvas) throws SQLException;
    void newGame() throws SQLException;
    int getScore();
    int getCoins();
    void eventsCheck();
}
