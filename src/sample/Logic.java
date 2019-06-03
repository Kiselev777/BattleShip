package sample;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Random;

public class Logic {
    private boolean flag = false;
    private static Battle enemyBattle, myBattle;
    private int countShip = 10;
    private boolean eTurn;
    private int[] deck = {1, 1, 1, 1, 2, 2, 2, 3, 3, 4};


    private void computer() throws InterruptedException {
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
        System.out.println("The game is start");
    }

    void effect() throws IOException {
        enemyBattle = new Battle(true, e -> {
            Cell cell = (Cell) e.getSource();
            if (!flag)
                return;
            if (cell.isShoot)
                return;
            eTurn = !cell.damage();
            if (eTurn) {
                try {
                    computer();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
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
                    }
                }

        });

    }
    Parent setPane() throws IOException {
        VBox vBox=new VBox(75, myBattle, enemyBattle);
        vBox.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.setPrefSize(800, 800);
        pane.setCenter(vBox);
        return pane;
    }

}
