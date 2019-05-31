package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
    private Battle bat;
    //Board of the game
    public Ship ship = null;
    //Ship on the cell
    public int x, y;
    //Сoordinates
    public boolean isShoot = false;


    public Cell(int x, int y, Battle b) {
        super(40, 40);
        setFill(Color.BLACK);
        setStroke(Color.DIMGREY);
        this.x = x;
        this.y = y;
        this.bat = b;
    }

    public boolean damage() {
        isShoot = true;
        setFill(Color.WHITE);
        if (ship != null) {
            ship.damage();
            setFill(Color.RED);
            if (!ship.isAlive())
                bat.countShip--;
            return true;
        }
        return false;
    }
}
