package Objects;

import javafx.scene.image.Image;

public class WinterWall extends Wall{
        public WinterWall(int x, int y) {
        super(x,y);
            for (int i = 0; i < this.size; i++) {
                this.images[i] = new Image(getClass().getResourceAsStream("/Objects/assets/winter-wall/winter-wall.png"));
            }
    }
}
