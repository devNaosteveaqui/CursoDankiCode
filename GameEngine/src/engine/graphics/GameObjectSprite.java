package engine.graphics;

import engine.utilities.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameObjectSprite extends GameObject {
    public BufferedImage image;
    public GameObjectSprite() {
        super();
    }
    public GameObjectSprite(String path) {
        super();
        loadResource(path);
    }
    public void loadResource(String path) {
        try {
            image = ImageIO.read(getClass().getResource("../../game"+path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render_process(Graphics g) {
        super.render_process(g);
        if(image != null) g.drawImage((Image) image, (int) this.position.x, (int) this.position.y, (int) this.size.x, (int) this.size.y,null);
    }
}
