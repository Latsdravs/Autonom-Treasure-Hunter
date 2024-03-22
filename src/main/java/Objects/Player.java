package Objects;

import com.example.demo.GameGraph;

import javafx.scene.image.Image;

import java.util.*;


public class Player extends GameObject {
    private ArrayList<GameObject> explored=new ArrayList<>();
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





    }

    public int move(){
        int dir;
        if(gameplan.length()>0) {
            switch (gameplan.charAt(0)) {
                case 'U':
                    dir = -1;
                    break;
                case 'D':
                    dir = 1;
                    break;
                case 'R':
                    dir = 2;
                    break;
                case 'L':
                    dir = -2;
                    break;
                default:
                    dir = 0;
            }
        }else dir=0;
        this.loc = new Location(loc.x+dir/2,loc.y+dir%2);
        pastLocations.add(loc.duplicate());
        return dir;
    }
    public int move(int dir){
        this.loc = new Location(loc.x+dir/2,loc.y+dir%2);
        pastLocations.add(loc.duplicate());
        return dir;
    }

    //etrafta degisen noktalara bakacak
    public ArrayList<Integer> look(int dir){


        ArrayList<Integer> messages=new ArrayList<>();
        int x=this.getX();
        int y = this.getY();
        for (int i = -3; i < 4; i++) {
            GameObject temp = vision[x+dir/2*3+i*dir%2][y+dir%2*3+i*dir/2];
            if(!explored.contains(temp)){
                messages.add(i);
                explored.add(temp);


            }
        }



        return messages;
    }




}
