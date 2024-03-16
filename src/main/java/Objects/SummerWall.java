package Objects;

import javafx.scene.image.Image;

public class SummerWall extends Wall{
    public SummerWall(int x, int y) {
        super(x,y);
        for (int i = 0; i < this.size; i++) {
            this.images[i] = new Image(getClass().getResourceAsStream("/Objects/assets/summer-wall/summer-wall.png"));
        }
    }
}
