package sample;
/**
 * VERSION 1.0.1
 * Battlehip
 * Kiselev Ivan
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Main extends Application {
    private boolean flag = false;
    private Battle enemyBattle, myBattle;
    private int countShip = 10;
    private boolean eTurn;
    private Cell c;
    private boolean t = false;
    private int[] deck = {1, 1, 1, 1, 2, 2, 2, 3, 3, 4};


    private void computer() {
        while (eTurn) {
            Random rand = new Random();
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            Cell cell = myBattle.getCell(x, y);
            if (cell.isShoot)
                continue;
            eTurn = cell.damage();
            if (myBattle.countShip == 0) {
                System.out.println("Проиграл...");
                System.exit(0);
            }
        }
    }

    private void computerPlace() {
        int type = 9;
        Random rand = new Random();
        while (type >= 0) {
            if (enemyBattle.placeShip(new Ship(deck[type], Math.random() < 0.5), rand.nextInt(10), rand.nextInt(10))) {
                type--;
            }
        }

        flag = true;
    }

    private Parent effect() throws IOException {
        BorderPane pane = new BorderPane();
        pane.setPrefSize(800, 800);
        enemyBattle = new Battle(true, e -> {
            Cell cell = (Cell) e.getSource();
            if (!flag)
                return;
            if (cell.isShoot)
                return;
            eTurn = !cell.damage();
            if (eTurn)
                computer();
            if (enemyBattle.countShip == 0) {
                System.out.println("ЭТО ПОБЕДА");

                System.exit(0);
            }

        });
        myBattle = new Battle(false, e -> {
            if (flag)
                return;
            Cell cell = (Cell) e.getSource();
            if (myBattle.placeShip(new Ship(deck[countShip - 1], e.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                if (--countShip == 0) {
                    computerPlace();
                    pane.setRight(new Text("THE GAME IS START"));
                }
            }

        });

        VBox vbox = new VBox(75, myBattle, enemyBattle);
        vbox.setAlignment(Pos.CENTER);
        pane.setCenter(vbox);

        return pane;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane pane = new StackPane();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        pane.getChildren().add(root);
        pane.getChildren().add(effect());
        Scene sc = new Scene(pane);
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(sc);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
