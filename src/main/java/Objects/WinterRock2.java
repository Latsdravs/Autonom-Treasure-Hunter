package Objects;

import javafx.scene.image.Image;

public class WinterRock2 extends Rock2 {
    public WinterRock2(int x, int y) {
        super( x,y);
        for (int i = 0; i < this.size; i++) {
            this.images[i] = new Image(getClass().getResourceAsStream("/Objects/assets/big-winter-rock/row-"+((i/4)+1)+"-column-"+((i%4)+1)+".png"));
        }
    }
}
