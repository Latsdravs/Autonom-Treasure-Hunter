package Objects;

import javafx.scene.image.Image;

public class SummerTree extends Tree {
    public SummerTree( int x, int y) {
        super( x,y);
        for (int i = 0; i < this.size; i++) {
            this.images[i] = new Image(getClass().getResourceAsStream("/Objects/assets/summer-tree/summer-tree_"+(i+1)+".png"));
        }
    }
}
