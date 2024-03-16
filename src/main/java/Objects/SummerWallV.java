package Objects;

import javafx.scene.image.Image;

public class SummerWallV extends WallV{
    public SummerWallV(int x, int y) {
        super(x, y);
        for (int i = 0; i < this.size; i++) {
            this.images[i] = new Image(getClass().getResourceAsStream("/Objects/assets/summer-wall/summer-wall.png"));
        }
    }
}
