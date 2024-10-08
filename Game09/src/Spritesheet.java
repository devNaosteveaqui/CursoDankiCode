import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Spritesheet {
    public BufferedImage spritesheet;
    public Spritesheet(String path) {
        try {
            spritesheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public BufferedImage getSprite(int x, int y) {
        return spritesheet.getSubimage(x,y,16,16);
    }
}
