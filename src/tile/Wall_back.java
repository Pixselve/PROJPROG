package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Wall_back extends Wall {
  private static final BufferedImage image;

  static {
    try {
      image = ImageIO.read(Objects.requireNonNull(Wall.class.getResource("/tiles/WALL_back.png")));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Wall_back(int x, int y) {
    super(x, y);
  }

  @Override
  protected BufferedImage getImage() {
    return image;
  }
}
