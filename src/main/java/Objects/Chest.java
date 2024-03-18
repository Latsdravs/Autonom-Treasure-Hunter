package Objects;

import javafx.scene.image.Image;

public class Chest extends InanimateObstacle{
    public int type;
    public Chest(int x, int y) {
        super(4*4, x, y);
        for (int i = 0; i < 4; i++) {
            this.images[i] = new Image(getClass().getResourceAsStream("/Objects/assets/copper-chest/copper-chest_"+i+".png"));
        }
        for (int i = 0; i < 4; i++) {
            this.images[i+4] = new Image(getClass().getResourceAsStream("/Objects/assets/silver-chest/silver-chest_"+i+".png"));
        }
        for (int i = 0; i < 4; i++) {
            this.images[i+8] = new Image(getClass().getResourceAsStream("/Objects/assets/golden-chest/golden-chest_"+i+".png"));
        }
        for (int i = 0; i < 4; i++) {
            this.images[i+12] = new Image(getClass().getResourceAsStream("/Objects/assets/emerald-chest/emerald-chest_"+i+".png"));
        }
    }
}
