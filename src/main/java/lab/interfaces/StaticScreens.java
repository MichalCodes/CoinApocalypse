package lab.interfaces;

import javafx.scene.canvas.GraphicsContext;
import java.io.IOException;

public interface StaticScreens {
    int checkMouseEvents();
    void draw(GraphicsContext gc) throws IOException;
    void removeButtons();

}
