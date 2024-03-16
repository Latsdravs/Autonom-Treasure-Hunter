package Objects;

import javafx.scene.image.Image;

public class SummerRock extends Rock {
    public SummerRock( int x, int y) {
        super( x,y);
        for (int i = 0; i < this.size; i++) {
            this.images[i] = new Image(getClass().getResourceAsStream("/Objects/assets/summer-rock/summer-rock_"+(i+1)+".png"));
        }
    }
}
