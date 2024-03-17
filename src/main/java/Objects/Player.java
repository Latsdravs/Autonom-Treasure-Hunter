package Objects;

import java.util.ArrayList;
import java.util.Random;

public class Player extends GameObject {
    private int ID;
    private int[][] vision;

    ArrayList<Location> pastLocations = new ArrayList<>();

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Player(int x, int y){
        super(1,x,y);
        pastLocations.add(loc.duplicate());
        this.ID = new Random().nextInt();

    }
    public void setVision(int[][] vision){
        this.vision=vision;
    }
    public int move(){return 0;}
    public int move(int dir){
        this.loc = new Location(loc.x+dir/2,loc.y+dir%2);
        pastLocations.add(loc.duplicate());
        return dir;
    }



}
