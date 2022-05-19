package tile;

import utils.Drawable;
import utils.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Portal extends Tile {
    private int tp;

    public Portal(int map, int x, int y){
        super(false, x,y);
        tp = map;
    }

}
