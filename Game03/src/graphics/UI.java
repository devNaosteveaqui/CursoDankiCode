package graphics;

import main.Game03_FlappyBird;

import java.awt.*;

/**
 *
 * @author samuel
 */
public class UI {
    
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("arial",Font.BOLD,18));
        g.drawString("Score: " + Game03_FlappyBird.score, 20, 20);
    }
}
