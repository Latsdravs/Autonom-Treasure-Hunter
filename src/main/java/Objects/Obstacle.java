package Objects;

public abstract class Obstacle {
    Location loc;
    int size;
    Obstacle(int size, Location loc){
        this.size = size;
        this.loc = loc;
    }
}
