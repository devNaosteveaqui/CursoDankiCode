package graficos;

import game01.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author samuel
 */
public class UI {
    
    public Font font;
    public UI() {
        font = new Font(Font.MONOSPACED,Font.TYPE1_FONT,5);
    }
    
    public void render(Graphics g) {
        int offsetx, offsety;
        offsetx = 3;
        offsety = 3;
        
        g.setColor(Color.red);
        g.fillRect(offsetx, offsety, 50,8);
        
        g.setColor(Color.GREEN);
        g.fillRect(offsetx + 1, offsety + 1, (int) ((Game.player.vida/Game.player.vidamax)*50 - 2),6);
        
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(Game.player.vida +" / " +Game.player.vidamax, offsetx + 5, offsety + 6);
    }
}
