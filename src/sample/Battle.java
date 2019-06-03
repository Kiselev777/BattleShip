package sample;


import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;

import java.util.Arrays;
import java.util.stream.IntStream;


public class Battle extends Parent {
    private boolean turn;
    //My pane or enemy pane
    int countShip = 10;

    private VBox lines = new VBox();


    public Battle(boolean enemy, EventHandler<? super MouseEvent> handler) {
        this.turn = enemy;
        for (int y = 0; y < 10; y++) {
            HBox line = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell c = new Cell(x, y, this);
                c.setOnMouseClicked(handler);
                line.getChildren().add(c);
            }

            lines.getChildren().add(line);
        }

        getChildren().add(lines);
    }

    Cell getCell(int x, int y) {
        return (Cell) ((HBox) lines.getChildren().get(y)).getChildren().get(x);
    }

    private boolean isTruePoint(Point2D point2D) {
        return isTruePoint(point2D.getX(), point2D.getY());
    }


    private boolean isTruePoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    private Cell[] getCells(int x, int y) {
        Point2D[] points = new Point2D[]{new Point2D(x - 1, y), new Point2D(x + 1, y), new Point2D(x, y - 1),
                new Point2D(x, y + 1), new Point2D(x + 1, y + 1), new Point2D(x - 1, y - 1)
        };

        return Arrays.stream(points).filter(this::isTruePoint).map(p -> getCell((int) p.getX(),
                (int) p.getY())).toArray(Cell[]::new);
    }

    private boolean canPlace(Ship ship, int x, int y) {
        int length = ship.deck;

        if (ship.side) {
            for (int i = y; i < y + length; i++) {
                if (!isTruePoint(x, i)) {
                    return false;
                }
                Cell cell = getCell(x, i);
                if (cell.ship != null)
                    return false;

                Cell[] cells = getCells(x, i);
                for (Cell neighbor : cells) {
                    if (!isTruePoint(x, i))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        } else {
            int i = x;
            while (i < x + length) {
                if (!isTruePoint(i, y))
                    return false;

                Cell cell = getCell(i, y);
                if (cell.ship != null)
                    return false;

                Cell[] cells = getCells(i, y);
                for (int i1 = 0, cellsLength = cells.length; i1 < cellsLength; i1++) {
                    Cell neighbor = cells[i1];
                    if (!isTruePoint(i, y))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
                i++;
            }
        }

        return true;
    }

    boolean placeShip(Ship ship, int x, int y) {
        if (canPlace(ship, x, y)) {
            int l = ship.deck;
            if (ship.side) {
                IntStream.range(y, y + l).mapToObj(i -> getCell(x, i)).forEach(cell -> {
                    cell.ship = ship;
                    if (!turn) {
                        cell.setFill(Color.BLUE);
                        cell.setStroke(Color.WHITE);
                    }
                });
            } else IntStream.range(x, x + l).mapToObj(i -> getCell(i, y)).forEach(cell -> {
                cell.ship = ship;
                if (!turn) {
                    cell.setFill(Color.BLUE);
                    cell.setStroke(Color.WHITE);
                }
            });
            return true;
        }
        return false;
    }


}
