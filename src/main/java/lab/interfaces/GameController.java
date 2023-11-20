package lab.interfaces;

import javafx.scene.canvas.Canvas;

public interface GameController {
    boolean isEnd();
    void draw(Canvas canvas);
    void newGame();
    int getScore();
    int getCoins();
    void eventsCheck();
}
