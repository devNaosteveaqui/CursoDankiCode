package graphics;

import entities.Player;
import main.Game;

import java.awt.*;

/**
 *
 * @author samuel
 */
public class UI {
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(10,10, 250,30);
        g.setColor(Color.green);
        g.fillRect(10,10, (int) ((Game.player.vida/100.0)*250),30);
        g.setColor(Color.white);
        g.drawRect(10,10,250,30);

        g.setFont(new Font("Arial",Font.BOLD,22));
        g.drawString("Moedas:" + Player.currentCoins+"/"+Player.maxCoins,Game.WIDTH*Game.SCALE-150,30);
    }
}
