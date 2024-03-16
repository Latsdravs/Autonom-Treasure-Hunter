package Objects;

import javafx.scene.image.Image;

public class WinterRock extends Rock {
    public WinterRock(int x, int y) {
        super(x,y);
        for (int i = 0; i < this.size; i++) {
            this.images[i] = new Image(getClass().getResourceAsStream("/Objects/assets/winter-rock/winter-rock_"+(i+1)+".png"));
        }
    }
}
