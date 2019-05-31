package sample;
import javafx.scene.Parent;

public class Ship extends Parent {
    public int deck;
    //Type of the ship

    public boolean side=true;
    // Side ship on the board

    private int healthPoint;
    //Health of the ship


    public Ship(int d,boolean s){
        healthPoint=d;
        this.deck=d;
        this.side=s;
    }

    public void damage(){healthPoint--;}

    public boolean isAlive(){return healthPoint>0;}
}
