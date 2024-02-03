package lab.Screens;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import lab.Database;
import lab.Game.Game;
import lab.interfaces.GameController;
import lab.interfaces.StaticScreens;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
//todo udelej to jako jednouser a jeb na to
public class Menu implements StaticScreens {
    private final GameController game;
    private final Connection conection;
    private final Canvas canvas;
    private final StaticScreens gameOver,  upgrades, topScore;
    private final Image menu;
    private boolean gameWasStarted, showUpgrades, showTopScores, block;
    private final Button play = new Button("Play");
    private final Button topScores = new Button("Top scores");
    private final Button upgrade = new Button("Upgrade");
    private final Group root;
    public Menu(Canvas canvas) throws SQLException {
        this.conection = DriverManager.getConnection("jdbc:h2:./scoreDB;AUTO_SERVER=TRUE");
        //Database.dropData(conection);
        Database.createDefaults(conection);
        Database.setActualUser("User");
        if(Database.find(conection) < 1) Database.insertUser(conection);
        this.canvas = canvas;
        this.root = (Group) canvas.getScene().getRoot();
        this.game = new Game(canvas, conection);
        this.gameOver = new GameOverScreen(canvas, (Game) game, conection);
        menu = new Image("menu.png", canvas.getWidth(), canvas.getHeight(), true, true);
        this.gameWasStarted = false;
        this.block = false;
        this.upgrades = new Upgrade(root, conection);
        this.topScore = new TopScores(conection, root);
        setButtons();
    }

    @Override
    public int checkMouseEvents() {
        play.setOnAction(e -> {
            gameWasStarted = true;
            try {
                game.newGame();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        upgrade.setOnAction(e -> {
            showUpgrades = true;
        });
        topScores.setOnAction(e -> {
            showTopScores = true;
        });
        return 0;
    }
    @Override
    public void draw(GraphicsContext gc) throws IOException, SQLException {
        //zmeny
        int eventNumber = gameOver.checkMouseEvents();
        if (eventNumber == 1){
            game.newGame();
            gameWasStarted = true;
            gameOver.removeButtons();
        } else if (eventNumber == 3) {
            gameWasStarted = false;
            gameOver.removeButtons();
        } else if (eventNumber == 2) {
            gameOver.removeButtons();
            gameWasStarted = false;
            showUpgrades = true;
        } else if (upgrades.checkMouseEvents() == 4){
            showUpgrades = false;
            upgrades.removeButtons();
            block = false;
        } else if (topScore.checkMouseEvents() == 1){
            showTopScores = false;
            topScore.removeButtons();
            block = false;
        }
        eventNumber = 0;

        //vykresleni
        if (gameWasStarted){
            removeButtons();
            block = false;
            if(game.isEnd()){
                gameOver.draw(gc);
                gameOver.checkMouseEvents();
            }else if (!game.isEnd()){
                game.draw(canvas);
                game.eventsCheck();
            }
        } else{
            if(showUpgrades){
                removeButtons();
                upgrades.draw(gc);
            } else if (showTopScores){
                removeButtons();
                topScore.draw(gc);
            }
            else{
                gc.drawImage(menu, 0,0);
                if(!block){
                    root.getChildren().add(play);
                    root.getChildren().add(topScores);
                    root.getChildren().add(upgrade);
                    block = true;
                }
            }
            checkMouseEvents();
        }
    }
    @Override
    public void removeButtons() {
        root.getChildren().remove(play);
        root.getChildren().remove(topScores);
        root.getChildren().remove(upgrade);
    }
    private void setButtons(){
        play.setLayoutX(20);
        play.setLayoutY(450);
        play.setPrefWidth(250);
        play.setPrefHeight(50);
        play.setStyle("-fx-background-color: yellow;");
        topScores.setLayoutX(350);
        topScores.setLayoutY(450);
        topScores.setPrefWidth(250);
        topScores.setPrefHeight(50);
        topScores.setStyle("-fx-background-color: yellow;");
        upgrade.setLayoutX(670);
        upgrade.setLayoutY(450);
        upgrade.setPrefWidth(250);
        upgrade.setPrefHeight(50);
        upgrade.setStyle("-fx-background-color: yellow;");
    }
}
