package Objects;

import javafx.scene.image.Image;

public class SummerTree2 extends Tree2 {
    public SummerTree2( int x, int y) {
        super( x,y);
        for (int i = 0; i < this.size; i++) {
            this.images[i] = new Image(getClass().getResourceAsStream("/Objects/assets/big-summer-tree/row-"+((i/4)+1)+"-column-"+((i%4)+1)+".png"));
        }
    }
}
