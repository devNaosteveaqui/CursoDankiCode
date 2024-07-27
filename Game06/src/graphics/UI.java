package graphics;

import entities.Player;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author samuel
 */
public class UI {
    public static BufferedImage HEART = Game.spritesheet.getSprite(0,16,8,8);
    public void render(Graphics g) {
        for(int i =0; i<(int)Game.VIDA; i++) {
            g.drawImage(HEART,20+(i*40),10,36,36,null);
        }
        g.setFont(new Font("Arial",Font.BOLD,19));
        g.setColor(Color.white);
        g.drawString("$"+Game.DINHEIRO,Game.WIDTH*Game.SCALE-80,25);
    }
}
