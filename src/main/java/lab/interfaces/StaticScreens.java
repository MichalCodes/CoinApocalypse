package lab.interfaces;

import javafx.scene.canvas.GraphicsContext;
import java.io.IOException;
import java.sql.SQLException;

public interface StaticScreens {
    int checkMouseEvents();
    void draw(GraphicsContext gc) throws IOException, SQLException;
    void removeButtons();

}
