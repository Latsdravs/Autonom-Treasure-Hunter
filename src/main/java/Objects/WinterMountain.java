package Objects;

import javafx.scene.image.Image;

public class WinterMountain extends Mountain{
    public WinterMountain(int x, int y) {
        super( x,y);
        for (int i = 0; i < this.size; i++) {

            this.images[i] = new Image(getClass().getResourceAsStream("/Objects/assets/winter-mountain/row-"+((i/15)+1)+"-column-"+((i%15)+1)+".png"));
        }
    }
}
