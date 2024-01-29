package lab;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lab.Screens.Menu;
import lab.interfaces.StaticScreens;
import java.io.IOException;
import java.sql.SQLException;

public class DrawingThread extends AnimationTimer {
	private final GraphicsContext gc;
	private final StaticScreens menu;

	public DrawingThread(Canvas canvas) throws SQLException {
		this.gc = canvas.getGraphicsContext2D();
		this.menu = new Menu(canvas);
	}

	/**
	  * Draws objects into the canvas. Put you code here. 
	 */
	@Override
	public void handle(long now) {
		try {
			menu.draw(gc);
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e);
		}
        menu.checkMouseEvents();
		Routines.sleep(7);
	}
}