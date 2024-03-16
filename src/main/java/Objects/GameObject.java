package Objects;

import javafx.scene.image.Image;

public abstract class GameObject {
    int size;


    Location loc;
    Image[] images;
    public int getX(){
        return loc.x;
    }
    public int getY(){
        return loc.getY();
    }
    public GameObject(int size, int x, int y) {
        this.loc = new Location(x,y);
        this.size=size;
        this.images = new Image[size];
    }
    Image[] getImages(){
        return images;
    }
}
