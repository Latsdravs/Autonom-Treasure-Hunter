package Objects;

import com.example.demo.GameGraph;

import javafx.scene.image.Image;

import java.util.*;


public class Player extends GameObject {
    private String gameplan;
    private int ID;
    GameGraph graf;
    private GameObject[][] vision;
    private int grid_x;
    private int grid_y;


    ArrayList<Location> pastLocations = new ArrayList<>();

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Player(int x, int y){
        super(1,x,y);
        this.images[0] = new Image(getClass().getResourceAsStream("/Objects/assets/character.png"));
        pastLocations.add(loc.duplicate());
        this.ID = new Random().nextInt();



    }


    public void setVision(GameObject[][] vision){
        this.vision=vision;
        this.grid_x=vision.length;
        this.grid_y=vision[0].length;
        graf = new GameGraph(grid_x,grid_y);
        graf.fastestRouteFirst(this.getX(),this.getY());
        gameplan= graf.getRoute();
        System.out.println("length:"+gameplan.length());
    }

    public int move(){
        return switch (gameplan.charAt(0)) {
            case 'U' -> -1;
            case 'D' -> 1;
            case 'R' -> 2;
            case 'L' -> -2;
            default -> 0;
        };
    }
    public int move(int dir){
        this.loc = new Location(loc.x+dir/2,loc.y+dir%2);
        pastLocations.add(loc.duplicate());
        return dir;
    }

    //etrafta degisen noktalara bakacak
    public void look(){
        gameplan=gameplan.substring(1);
    }




}
