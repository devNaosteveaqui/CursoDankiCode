package pacman.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import pacman.Game;

/**
 *
 * @author samuel
 */
public class UI {
    
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("arial",Font.BOLD,18));
        g.drawString("Cherrys : " + (Game.frutas_atual) + "/" + (Game.frutas_contagem), 30, 30);
    }
}
