package lab.Screens;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lab.Game.Game;
import lab.interfaces.GameController;
import lab.interfaces.StaticScreens;

import java.io.*;
import java.util.Arrays;

public class GameOverScreen implements StaticScreens {
    private final Image gameOver;
    private final Group root;
    private  final GameController game;
    private boolean playAgainClicked, upgradeWasClicked, mainMenuWasClicked;
    private final Button playAgain = new Button("Play again");
    private final Button mainMenu = new Button("Main menu");
    private final Button upgrade = new Button("Upgrade");
    private boolean block;
    GameOverScreen(Canvas canvas, Game game) {
        gameOver = new Image("gameOver.png", 1000, canvas.getHeight(), true, true);
        this.root = (Group) canvas.getScene().getRoot();
        playAgainClicked = false;
        upgradeWasClicked = false;
        mainMenuWasClicked = false;
        block = false;
        this.game = game;
        setButtons();
    }
    @Override
    public int checkMouseEvents() {
        int i = 0;
        playAgain.setOnAction(e -> playAgainClicked = true );
        upgrade.setOnAction(e -> upgradeWasClicked = true );
        mainMenu.setOnAction(e -> mainMenuWasClicked = true);
        if(playAgainClicked) i = 1;
        if(upgradeWasClicked) i = 2;
        if(mainMenuWasClicked) i = 3;
        playAgainClicked = false;
        upgradeWasClicked = false;
        mainMenuWasClicked = false;
        return  i;
    }

    @Override
    public void draw(GraphicsContext gc) throws IOException {
        gc.drawImage(gameOver, 0, 0);
        if(!block){
            writeBest(game.getScore());
            root.getChildren().add(playAgain);
            root.getChildren().add(upgrade);
            root.getChildren().add(mainMenu);
            block = true;
        }
        gc.setFill(Color.BLACK);
        gc.fillText("Your score: " + game.getScore(), 300, 400);
    }
    public void removeButtons(){
        root.getChildren().remove(playAgain);
        root.getChildren().remove(upgrade);
        root.getChildren().remove(mainMenu);
        block = false;
    }
    private void writeBest(int score) throws IOException {
        String[] specificLine = new String[8];
        int[] top5 = {0, 0, 0, 0, 0, 0};
        int cash = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("mem.txt"))) {
            specificLine[0] = br.readLine();
            for(int i = 1; i < 6; i++){
                specificLine[i] = br.readLine();
                top5[i - 1] = Integer.parseInt(specificLine[i]);
            }
            specificLine[6] = br.readLine();
            cash = Integer.parseInt(specificLine[6]);
            top5[5] = score;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Arrays.sort(top5);
        BufferedWriter bw = new BufferedWriter(new FileWriter("mem.txt"));
        bw.write(specificLine[0] + "\n");
        for(int i = 5; i > 0; i--) bw.write(top5[i] + "\n");
        cash += game.getCoins();
        bw.write(Integer.toString(cash));
        bw.close();
    }
    private void setButtons(){
        playAgain.setLayoutX(20);
        mainMenu.setLayoutX(380);
        upgrade.setLayoutX(735);
        playAgain.setLayoutY(490);
        mainMenu.setLayoutY(490);
        upgrade.setLayoutY(490);
        playAgain.setPrefWidth(250);
        mainMenu.setPrefWidth(250);
        upgrade.setPrefWidth(250);
        playAgain.setPrefHeight(50);
        mainMenu.setPrefHeight(50);
        upgrade.setPrefHeight(50);
        playAgain.setStyle("-fx-background-color: yellow;");
        upgrade.setStyle("-fx-background-color: yellow;");
        mainMenu.setStyle("-fx-background-color: yellow;");
    }
}
