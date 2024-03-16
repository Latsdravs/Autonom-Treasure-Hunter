package Objects;

import javafx.scene.image.Image;

public class WinterTree extends Tree {
    public WinterTree(int x, int y) {
        super(x,y);
        for (int i = 0; i < this.size; i++) {
            this.images[i] = new Image(getClass().getResourceAsStream("/Objects/assets/winter-tree/winter-tree_"+(i+1)+".png"));
        }
    }
}
