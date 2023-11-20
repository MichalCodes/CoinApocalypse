package lab.Screens;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lab.interfaces.StaticScreens;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class TopScores implements StaticScreens {
    private final Image image;
    private final Button back = new Button("Back to menu");
    private boolean goBack, block;
    private final Group root;
    private final int[] top5;
    TopScores(Group root) {
        this.root = root;
        this.image = new Image("upgrades.png", root.getScene().getWidth() , root.getScene().getHeight(), true, true);
        setButtons();
        goBack = false;
        this.top5 = new int[]{0, 0, 0, 0, 0};
    }
    @Override
    public int checkMouseEvents() {
        int i = 0;
        back.setOnAction(e -> goBack = true);
        if(goBack) i = 1;
        goBack = false;
        return i;
    }
    @Override
    public void draw(GraphicsContext gc) throws IOException {
        gc.drawImage(image, 0,0, root.getScene().getWidth(), root.getScene().getHeight());
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font("Calibri",FontWeight.BOLD, 20));
        for(int i = 0; i < top5.length; i++){
            gc.fillText(i+1 +". " + top5[i], 300, 200 + i * 30);
        }
        gc.setFont(Font.font("Calibri",FontWeight.BOLD, 50));
        gc.fillText("Top scores", 350, 100);
        if(!block){
            bestSc();
            root.getChildren().add(back);
            block = true;
        }
    }
    @Override
    public void removeButtons() {
        root.getChildren().remove(back);
        block = false;
    }
    private void setButtons() {
        back.setLayoutX(380);
        back.setLayoutY(450);
        back.setPrefWidth(250);
        back.setPrefHeight(50);
        back.setStyle("-fx-background-color: yellow;");
    }
    private void bestSc(){
        try (BufferedReader br = new BufferedReader(new FileReader("mem.txt"))) {
            br.readLine();
            for(int i = 0; i < 5; i++){
                this.top5[i] = Integer.parseInt(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
