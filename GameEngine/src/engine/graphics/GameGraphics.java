package engine.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameGraphics {
    public static BufferedImage spritesheet;
    public static void loadResource(String path) {
        try {
            spritesheet = ImageIO.read(GameGraphics.class.getResource("../../game"+path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static BufferedImage getSprite(int x, int y, int w, int h) {
        return spritesheet.getSubimage(x,y,w,h);
    }
}
