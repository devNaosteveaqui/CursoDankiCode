import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Spritesheet {
    public static BufferedImage spritesheet;
    public static BufferedImage[] player_front;
    public static BufferedImage[] inimigo_front;
    public static BufferedImage tile_wall;
    public Spritesheet() {
        try {
            spritesheet = ImageIO.read(getClass().getResource("res/spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        player_front = new BufferedImage[2];
        inimigo_front = new BufferedImage[2];

        player_front[0] = Spritesheet.getSprite(0,11,16,16);
        player_front[1] = Spritesheet.getSprite(16,11,16,16);

        inimigo_front[0] = Spritesheet.getSprite(0,11,16,16);
        inimigo_front[1] = Spritesheet.getSprite(16,11,16,16);
        //player_front[2] = Spritesheet.getSprite(32,11,16,16);
        tile_wall = Spritesheet.getSprite(271,214,16,16);
    }
    public static BufferedImage getSprite(int x, int y, int w, int h) {
        return spritesheet.getSubimage(x,y,w,h);

    }
}
