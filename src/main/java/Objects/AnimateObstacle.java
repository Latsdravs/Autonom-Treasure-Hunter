package Objects;

public abstract class AnimateObstacle extends Obstacle {
    int walkAxis;//0 for Horizontal 1 for Vertical

    public AnimateObstacle(int size, Location loc, int walkAxis) {
        super(size, loc);
        this.walkAxis = walkAxis;
    }
}
